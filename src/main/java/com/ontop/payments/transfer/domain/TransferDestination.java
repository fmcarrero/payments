package com.ontop.payments.transfer.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferDestination {
    private long recipientId;
}
