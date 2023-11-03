package com.ontop.payments.transaction.infrastructure.controller;

import com.ontop.payments.shared.pagination.PageResponse;
import com.ontop.payments.transaction.application.find_transaction.FindTransactions;
import com.ontop.payments.transaction.domain.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {
    private final FindTransactions findTransaction;

    public TransactionController(FindTransactions findTransaction) {
        this.findTransaction = findTransaction;
    }

    @GetMapping("/transactions")
    public ResponseEntity<PageResponse<List<Transaction>>> findTransactions(@RequestParam(name = "cursor", defaultValue = "0") Long cursorId,
                                                              @RequestParam(name = "limit", defaultValue = "10") int limit){
        PageResponse<List<Transaction>> response =  this.findTransaction.execute(cursorId, limit);
        return ResponseEntity.ok(response);
    }
}
