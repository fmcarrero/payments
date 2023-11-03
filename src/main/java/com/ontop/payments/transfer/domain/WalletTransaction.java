package com.ontop.payments.transfer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WalletTransaction {
    private long walletTransactionId;
    private long amount;
    private long userId;
}
