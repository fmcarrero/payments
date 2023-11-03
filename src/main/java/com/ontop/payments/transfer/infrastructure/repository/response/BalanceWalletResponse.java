package com.ontop.payments.transfer.infrastructure.repository.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BalanceWalletResponse {

    private long balance;

    @JsonProperty("user_id")
    private long userId;
}
