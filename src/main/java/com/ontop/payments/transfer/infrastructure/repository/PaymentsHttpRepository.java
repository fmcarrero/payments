package com.ontop.payments.transfer.infrastructure.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontop.payments.config.PaymentsConfig;
import com.ontop.payments.transfer.domain.PaymentProvider;
import com.ontop.payments.transfer.domain.Transfer;
import com.ontop.payments.transfer.domain.TransferStatus;
import com.ontop.payments.transfer.domain.repository.PaymentsRepository;
import com.ontop.payments.transfer.infrastructure.exception.WalletHTTPRepositoryException;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Repository
public class PaymentsHttpRepository implements PaymentsRepository {

    private final HttpClient httpClient;
    private final PaymentsConfig paymentsConfig;
    private final ObjectMapper objectMapper;

    public PaymentsHttpRepository(HttpClient httpClient, PaymentsConfig paymentsConfig, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.paymentsConfig = paymentsConfig;
        this.objectMapper = objectMapper;
    }

    @Override
    public PaymentProvider PaymentProvider(Transfer transfer) {
        String endpoint = paymentsConfig.getEndpoint();
        try {
            var body = objectMapper.writeValueAsString(createPaymentsRequest(transfer));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(endpoint))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                transfer.setStatus(TransferStatus.COMPLETED);
                PaymentProviderResponse paymentProviderResponse = objectMapper.readValue(response.body(), PaymentProviderResponse.class);

                return PaymentProvider.builder()
                        .id(paymentProviderResponse.getPaymentInfo().getId())
                        .build();
            } else {
                throw new WalletHTTPRepositoryException("Failed to doing payment . Status code: " + response.statusCode());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error payment service" , e);
        }
    }
    private CreatePaymentsRequest createPaymentsRequest(Transfer transfer){
        return CreatePaymentsRequest.builder()
                .amount(transfer.getAmount())
                .source(CreatePaymentsRequest.Source.builder()
                        .type(transfer.getSource().getType())
                        .sourceInformation(CreatePaymentsRequest.Source.SourceInformation.builder()
                                //.name(transfer.getSourceInformation().getName())
                                .name("ONTOP INC")
                                .build())
                        .account(CreatePaymentsRequest.Source.Account.builder()
                                .accountNumber(transfer.getSource().getAccountNumber())
                                .currency(transfer.getSource().getCurrency())
                                .routingNumber(transfer.getSource().getRoutingNumber())
                                .build())
                        .build())
                .destination(CreatePaymentsRequest.Destination.builder()
                        .name(transfer.getDestination().getName())
                        .account(CreatePaymentsRequest.Destination.Account.builder()
                                .accountNumber(transfer.getDestination().getAccountNumber())
                                .currency(transfer.getDestination().getCurrency())
                                .routingNumber(transfer.getDestination().getRoutingNumber())
                                .build())
                        .build()
                )
                .build();
    }
}
