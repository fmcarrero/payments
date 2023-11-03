package com.ontop.payments.transfer.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentProvider {
    private String id;
    public boolean isCompleted() {
        return true;
    }
}
