package com.toptal.caloriecount.exceptions;

public class UserNotAuthorizedException extends Exception {
    public UserNotAuthorizedException(String message, Throwable error) {
        super(message, error);
    }

    public UserNotAuthorizedException(String message) {
        super(message);
    }
}
