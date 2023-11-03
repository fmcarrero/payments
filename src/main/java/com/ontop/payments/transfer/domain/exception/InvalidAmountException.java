package com.ontop.payments.transfer.domain.exception;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(long amount) {
        super(String.format("Invalid amount detected: %d. Reason: Amount is less than or equal to 0. Expected: Amount greater than 0.", amount));
    }

    public InvalidAmountException(String message) {
        super(message);
    }
}
