package com.ontop.payments.transfer.domain;

import com.ontop.payments.recipient.domain.Recipient;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAccount {
    /*
    "type": "COMPANY",
        "sourceInformation": {
            "name": "ONTOP INC"
        },
        "account": {
            "accountNumber": "0245253419",
            "currency": "USD",
            "routingNumber": "028444018"
        }
     */
    private long userId;
    private long accountId;
    private String currency;
    private String type;
    private String accountNumber;
    private String routingNumber;
}
