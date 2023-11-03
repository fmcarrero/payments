package com.ontop.payments.recipient.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder(toBuilder = true, builderClassName = "Builder", buildMethodName = "buildInternal")
@AllArgsConstructor
public class Recipient {
    private long id;
    private long userId;
    private String firstName;
    private String lastName;
    private String routingNumber;
    private String nationalIdentificationNumber;
    private String accountNumber;
    private String bankName;
    private String currency;
    private LocalDateTime createdAt;

    public Recipient(long id ){
        this.id = id;
    }
    public static Recipient.Builder builder() {
        return new Recipient.Builder();
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public static class Builder {
        public Recipient build() {
            if (firstName == null || firstName.isEmpty()) {
                throw new IllegalArgumentException("First name cannot be null or empty");
            }
            return buildInternal();
        }
    }
}