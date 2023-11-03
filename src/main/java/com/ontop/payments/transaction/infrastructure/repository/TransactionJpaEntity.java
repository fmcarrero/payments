package com.ontop.payments.transaction.infrastructure.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionJpaEntity {
    @Id
    private Long id;

    @Column(nullable = false)
    private long amount;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "wallet_id")
    private long walletId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
