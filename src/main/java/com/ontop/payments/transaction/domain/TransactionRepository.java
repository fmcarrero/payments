package com.ontop.payments.transaction.domain;

import com.ontop.payments.shared.pagination.CursorBasedPageable;
import com.ontop.payments.shared.pagination.PageResponse;

import java.util.List;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    List<Transaction> FindAll(Long cursorId, int size) ;
}
