package com.ontop.payments.transaction.application.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSearchCriteriaCommand {
    private int page = 0;
    private int size = 10;
    private Long amount;

    @JsonProperty("created_at_start")
    private LocalDateTime createdAtStart;

    @JsonProperty("created_at_end")
    private LocalDateTime createdAtEnd;
}