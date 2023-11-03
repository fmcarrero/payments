package com.ontop.payments.transaction.infrastructure.controller;

import com.ontop.payments.shared.pagination.CursorBasedPageable;
import com.ontop.payments.shared.pagination.PageResponse;
import com.ontop.payments.transaction.application.find_transaction.FindTransactions;
import com.ontop.payments.transaction.domain.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {
    private final FindTransactions findTransaction;

    public TransactionController(FindTransactions findTransaction) {
        this.findTransaction = findTransaction;
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> findTransactions(){
        List<Transaction> response =  this.findTransaction.execute(26873423L, 10);
        return ResponseEntity.ok(response);
    }
}
