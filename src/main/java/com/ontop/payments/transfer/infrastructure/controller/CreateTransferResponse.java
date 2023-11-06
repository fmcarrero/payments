package com.ontop.payments.transfer.infrastructure.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreateTransferResponse {

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
    private LocalDateTime createdAt;
}
