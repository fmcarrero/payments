package com.ontop.payments.transaction.infrastructure.repository;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TransactionSpecifications {
    public static Specification<TransactionJpaEntity> hasAmountGreaterThan(Long amount) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("amount"), amount);
    }

    public static Specification<TransactionJpaEntity> isCreatedAfter(LocalDateTime date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("createdAt"), date);
    }
}
