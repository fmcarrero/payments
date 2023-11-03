package com.ontop.payments.transaction.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionJpaRepository extends JpaRepository<TransactionJpaEntity, Long> {

    @Query("SELECT i FROM TransactionJpaEntity i WHERE i.id > :cursorId")
    List<TransactionJpaEntity> findByCursorId(Long cursorId, Pageable pageable);

    @Query(value = "SELECT min(i.id) FROM TransactionJpaEntity i WHERE i.id > :cursorId")
    Optional<Long> findNextCursorId(Long cursorId);

    @Query(value = "SELECT max(i.id) FROM TransactionJpaEntity i WHERE i.id < :cursorId")
    Optional<Long> findPreviousCursorId(Long cursorId);
}