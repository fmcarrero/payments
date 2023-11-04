package com.ontop.payments.transaction.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontop.payments.shared.pagination.PageResponse;
import com.ontop.payments.transaction.application.command.TransactionSearchCriteriaCommand;
import com.ontop.payments.transaction.application.find_transaction.FindTransaction;
import com.ontop.payments.transaction.domain.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TransactionController {
    private final FindTransaction findTransaction;
    private final ObjectMapper objectMapper;

    public TransactionController(FindTransaction findTransaction, ObjectMapper objectMapper) {
        this.findTransaction = findTransaction;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/transactions")
    public ResponseEntity<PageResponse<List<TransactionFindAllResponse>>> findTransactions(@RequestParam Map<String,String> params){
        var criteria = objectMapper.convertValue(params, TransactionSearchCriteriaCommand.class);
        PageResponse<List<Transaction>> response = this.findTransaction.execute(criteria);
        return ResponseEntity.ok(new PageResponse<>(this.toResponse(response.content()), response.total()));
    }
    public List<TransactionFindAllResponse>toResponse(List<Transaction> transactions){
        return transactions.stream()
                .map(this::toResponse)
                .toList();
    }
    public TransactionFindAllResponse toResponse(Transaction transaction){
        return TransactionFindAllResponse.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .userId(transaction.getUserId())
                .paymentId(transaction.getPaymentId())
                .walletId(transaction.getWalletId())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
