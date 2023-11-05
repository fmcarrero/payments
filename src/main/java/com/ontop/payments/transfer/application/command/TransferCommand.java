package com.ontop.payments.transfer.application.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TransferCommand {

    private long amount;
    private TransferSourceCommand source;
    private TransferDestinationCommand destination;
}

