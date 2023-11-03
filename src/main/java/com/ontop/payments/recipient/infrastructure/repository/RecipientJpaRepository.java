package com.ontop.payments.recipient.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipientJpaRepository extends JpaRepository<RecipientJpaEntity, Long> {
    Optional<RecipientJpaEntity> findByIdAndUserId(Long id, Long userId);
}
