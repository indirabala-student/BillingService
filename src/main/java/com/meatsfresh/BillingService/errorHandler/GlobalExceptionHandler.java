package com.meatsfresh.BillingService.errorHandler;

import com.meatsfresh.BillingService.exception.DateRangeConflictException;
import com.meatsfresh.BillingService.exception.NoOrdersFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DateRangeConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(DateRangeConflictException ex) {    ErrorResponse error = new ErrorResponse(
            "Date Range Conflict",
            ex.getMessage(),
            HttpStatus.CONFLICT.value()
    );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoOrdersFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoOrdersFound(NoOrdersFoundException ex) {    ErrorResponse error = new ErrorResponse(
            "No Orders Found",
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value()
    );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}