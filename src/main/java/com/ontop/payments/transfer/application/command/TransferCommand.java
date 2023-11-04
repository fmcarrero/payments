package com.ontop.payments.transfer.application.command;

import lombok.Data;

@Data
public class TransferCommand {

    private long amount;
    private TransferSourceCommand source;
    private TransferDestinationCommand destination;
}

