package com.ontop.payments.transfer.application.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class TransferCommand {

    private long amount;
    private TransferSourceCommand source;
    private TransferDestinationCommand destination;
}

