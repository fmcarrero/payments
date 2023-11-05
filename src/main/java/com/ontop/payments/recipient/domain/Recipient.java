package com.ontop.payments.recipient.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Currency;


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
            if (lastName == null || lastName.isEmpty()) {
                throw new IllegalArgumentException("Last name cannot be null or empty");
            }
            if (routingNumber == null || routingNumber.isEmpty()) {
                throw new IllegalArgumentException("Routing number cannot be null or empty");
            }
            if (nationalIdentificationNumber == null || nationalIdentificationNumber.isEmpty()) {
                throw new IllegalArgumentException("National identification number cannot be null or empty");
            }
            if (accountNumber == null || accountNumber.isEmpty()) {
                throw new IllegalArgumentException("Account number cannot be null or empty");
            }
            if (bankName == null || bankName.isEmpty()) {
                throw new IllegalArgumentException("Bank name cannot be null or empty");
            }
            if (currency == null || currency.isEmpty()) {
                throw new IllegalArgumentException("Currency cannot be null or empty");
            }
            setCurrency(currency);
            return buildInternal();
        }
        public void setCurrency(String currency) {
            try {
                Currency.getInstance(currency);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid currency: " + currency);
            }
        }
    }
}