package com.ontop.payments.transfer.infrastructure.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontop.payments.config.WalletConfig;
import com.ontop.payments.transfer.domain.Balance;
import com.ontop.payments.transfer.domain.Transfer;
import com.ontop.payments.transfer.domain.WalletTransaction;
import com.ontop.payments.transfer.domain.repository.WalletRepository;
import com.ontop.payments.transfer.infrastructure.exception.WalletHTTPRepositoryException;
import com.ontop.payments.transfer.infrastructure.repository.response.BalanceWalletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;


@Repository
public class WalletHTTPRepository implements WalletRepository {

    private final HttpClient httpClient;
    private final WalletConfig walletConfig;
    private final ObjectMapper objectMapper;


    public WalletHTTPRepository(HttpClient httpClient, WalletConfig walletConfig, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.walletConfig = walletConfig;
        this.objectMapper = objectMapper;
    }
    @Override
    public Balance getBalanceByUser(Long userId) {
        String endpoint = "%s/balance?user_id=%d".formatted(walletConfig.getEndpoint(), userId);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(endpoint))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                BalanceWalletResponse balanceResponse = objectMapper.readValue(response.body(), BalanceWalletResponse.class);
                return new Balance(balanceResponse.getBalance(),balanceResponse.getUserId());
            } else {
                throw new WalletHTTPRepositoryException("Failed to get balance for user: " + userId + ". Status code: " + response.statusCode());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error fetching balance for user: " + userId, e);
        }
    }

    @Override
    public WalletTransaction saveTransaction(Transfer transfer) {
        String endpoint = "%s/transactions".formatted(walletConfig.getEndpoint());
        Map<String, Object> body = new HashMap<>();
        body.put("amount", transfer.getTotalWithdrawal());
        body.put("user_id", transfer.getSource().getUserId());
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(endpoint))
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != HttpStatus.OK.value()) {
                throw new WalletHTTPRepositoryException("Failed to save transaction. Status code: " + response.statusCode());
            }
            Map<String, Object> responseMap = objectMapper.readValue(response.body(), new TypeReference<>() {});
            return new WalletTransaction((Integer) responseMap.get("wallet_transaction_id"), transfer.getTotalWithdrawal(), transfer.getSource().getUserId());

        } catch (Exception e) {
            throw new RuntimeException("Error saving transaction", e);
        }
    }
}
