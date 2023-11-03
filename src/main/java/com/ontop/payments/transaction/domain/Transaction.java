package com.ontop.payments.transaction.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Transaction {
    private long id;
    private long amount;
    private long userId;
    private String paymentId;
    private long walletId;
    private LocalDateTime createdAt;
}
