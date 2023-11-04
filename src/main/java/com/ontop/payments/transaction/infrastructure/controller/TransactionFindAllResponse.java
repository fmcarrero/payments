package com.ontop.payments.transaction.infrastructure.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionFindAllResponse {

    @JsonProperty("id")
    private long id;

    @JsonProperty("amount")
    private long amount;

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("payment_id")
    private String paymentId;

    @JsonProperty("wallet_id")
    private long walletId;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime createdAt;
}
