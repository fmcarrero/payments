package com.ontop.payments.recipient.application.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRecipientCommand {
    @JsonProperty("first_name")
    private  String firstName;

    @JsonProperty("last_name")
    private  String lastName;

    @JsonProperty("routing_number")
    private String routingNumber;

    @JsonProperty("national_identification_number")
    private String nationalIdentificationNumber;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("bank_name")
    private String bankName;

    @JsonProperty("currency")
    private String currency;
}
