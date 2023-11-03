package com.ontop.payments.transfer.application.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransferSourceCommand {
    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("source_id")
    private long sourceId;

}
