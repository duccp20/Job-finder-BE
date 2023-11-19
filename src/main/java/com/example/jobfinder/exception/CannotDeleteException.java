package com.example.jobfinder.exception;

public class CannotDeleteException extends ExceptionCustom {

    public CannotDeleteException(Object errors) {
        super("CANNOT DELETE", errors);
    }
}
