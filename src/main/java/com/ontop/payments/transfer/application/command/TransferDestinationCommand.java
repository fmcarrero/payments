package com.ontop.payments.transfer.application.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TransferDestinationCommand {
   @JsonProperty("recipient_id")
    private long recipientId;
}
