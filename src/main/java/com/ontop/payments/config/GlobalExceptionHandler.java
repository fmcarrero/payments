package com.ontop.payments.config;


import com.ontop.payments.transfer.domain.exception.InsufficientFundsException;
import com.ontop.payments.transfer.domain.exception.InvalidAmountException;
import com.ontop.payments.transfer.domain.exception.InvalidUserException;
import com.ontop.payments.transfer.domain.exception.RecipientNotFoundException;
import com.ontop.payments.transfer.infrastructure.exception.WalletHTTPRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<?> handleInvalidAmountException(InvalidAmountException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getDescription(false));
        return ResponseEntity.badRequest().body(errorResponse);
    }
    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<?> handleInvalidAmountException(InvalidUserException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getDescription(false));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(RecipientNotFoundException.class)
    public ResponseEntity<?> handleInvalidAmountException(RecipientNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(WalletHTTPRepositoryException.class)
    public ResponseEntity<?> handleInvalidAmountException(WalletHTTPRepositoryException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getDescription(false));
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<?> handleInvalidAmountException(InsufficientFundsException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getDescription(false));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex, WebRequest request) {
        logger.error("An unexpected error occurred: ", ex);
        String errorMessage = "An unexpected error occurred: " + ex.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(errorMessage, request.getDescription(false));
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
