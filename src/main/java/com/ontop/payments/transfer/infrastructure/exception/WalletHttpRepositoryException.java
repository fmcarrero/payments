package com.ontop.payments.transfer.infrastructure.exception;

public class WalletHttpRepositoryException extends RuntimeException{

    public WalletHttpRepositoryException(String message) {
        super(message);
    }
}
