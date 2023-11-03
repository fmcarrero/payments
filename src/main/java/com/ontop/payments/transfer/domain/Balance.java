package com.ontop.payments.transfer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Balance {
    private long balance;
    private long userId;


}
