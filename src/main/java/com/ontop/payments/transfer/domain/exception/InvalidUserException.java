package com.ontop.payments.transfer.domain.exception;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException() {
        super("Invalid user");
    }

    public InvalidUserException(String message) {
        super(message);
    }
}
