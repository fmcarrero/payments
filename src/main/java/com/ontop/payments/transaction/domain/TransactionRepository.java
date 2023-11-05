package com.ontop.payments.transaction.domain;

import com.ontop.payments.shared.pagination.PageResponse;
import com.ontop.payments.transaction.application.command.TransactionSearchCriteriaCommand;

import java.util.List;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    PageResponse<List<Transaction>> findAll(TransactionSearchCriteriaCommand criteria) ;
}
