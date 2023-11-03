package com.ontop.payments.transaction.application.find_transaction;

import com.ontop.payments.shared.pagination.PageResponse;
import com.ontop.payments.transaction.domain.Transaction;
import com.ontop.payments.transaction.domain.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindTransactions {
    private final TransactionRepository transactionRepository;

    public FindTransactions(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public PageResponse<List<Transaction>> execute(Long cursorId, int size) {
        return this.transactionRepository.FindAll(cursorId, size);
    }
}

