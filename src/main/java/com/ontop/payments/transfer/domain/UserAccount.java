package com.ontop.payments.transfer.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAccount {
    private long userId;
    private long accountId;
    private String currency;
    private String type;
    private String accountNumber;
    private String routingNumber;
}
