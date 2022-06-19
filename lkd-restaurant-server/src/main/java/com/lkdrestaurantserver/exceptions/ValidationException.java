package com.lkdrestaurantserver.exceptions;

import org.hibernate.service.spi.ServiceException;

public class ValidationException extends ServiceException {

    public static enum ErrorType {
        INPUT_INVALID,
        INPUT_MISSING,
        NOT_FOUND,
        DUPLICATE,
        TOKEN_EXPIRED,
        PASSWORD_MISMATCH,
        WRONG_OLD_PASSWORD;
    }

    private ErrorType errorType;
    private String field;

    public ValidationException(String message, ErrorType errorType, String field) {
        super(message);
        this.errorType = errorType;
        this.field = field;
    }

    public ValidationException(String message, Throwable cause, ErrorType errorType, String field) {
        super(message, cause);
        this.errorType = errorType;
        this.field = field;
    }


    public ErrorType getErrorType(){ return this.errorType; }

    public void setErrorType(ErrorType errorType){ this.errorType = errorType; }

    public String getField() { return this.field; }

    public void setField(String field) { this.field = field; }

    @Override
    public String getMessage(){
        String message = super.getMessage();
        if(message == null || message.isEmpty()) {
            return String.format("Field is: %s,Error Type:%s", field, errorType.toString());
        } else {
            return String.format("Error Message: %s,Field: %s,Error Type:%s", message, field, errorType.toString());
        }
    }
}
