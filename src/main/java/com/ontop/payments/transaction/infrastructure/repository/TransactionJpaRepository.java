package com.ontop.payments.transaction.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionJpaEntity, Long>, PagingAndSortingRepository<TransactionJpaEntity, Long>, JpaSpecificationExecutor<TransactionJpaEntity> {
    // No necesitas definir ningún método aquí, Spring Data implementará los necesarios por ti
}