package com.meatsfresh.BillingService.exception;

public class NoOrdersFoundException extends RuntimeException {
    public NoOrdersFoundException(String message) {
        super(message);
    }
}