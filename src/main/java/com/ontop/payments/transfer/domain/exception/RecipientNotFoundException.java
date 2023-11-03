package com.ontop.payments.transfer.domain.exception;

public class RecipientNotFoundException extends RuntimeException{
    public RecipientNotFoundException() {
        super("Recipient not found");
    }

    public RecipientNotFoundException(String message) {
        super(message);
    }
}
