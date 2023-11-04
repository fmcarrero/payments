package com.ontop.payments.transaction.application.find_transaction;

import com.ontop.payments.shared.pagination.PageResponse;
import com.ontop.payments.transaction.application.command.TransactionSearchCriteriaCommand;
import com.ontop.payments.transaction.domain.Transaction;
import com.ontop.payments.transaction.domain.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Service
public class FindTransaction {
    private final TransactionRepository transactionRepository;

    public FindTransaction(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public  PageResponse<List<Transaction>> execute(@ModelAttribute TransactionSearchCriteriaCommand criteria) {
        return this.transactionRepository.FindAll(criteria);
    }
}

