package com.ontop.payments.transfer.application.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransferDestinationCommand {
   @JsonProperty("recipient_id")
    private long recipientId;
}
