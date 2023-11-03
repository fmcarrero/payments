package com.ontop.payments.transfer.infrastructure.exception;

public class WalletHTTPRepositoryException extends RuntimeException{

    public WalletHTTPRepositoryException(String message) {
        super(message);
    }
}
