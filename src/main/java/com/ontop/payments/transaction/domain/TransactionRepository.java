package com.ontop.payments.transaction.domain;

import com.ontop.payments.shared.pagination.PageResponse;

import java.util.List;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    PageResponse<List<Transaction>> FindAll(Long cursorId, int size) ;
}
