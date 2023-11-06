package com.ontop.payments.transfer.application.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferSourceCommand {
    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("source_id")
    private long sourceId;

}
