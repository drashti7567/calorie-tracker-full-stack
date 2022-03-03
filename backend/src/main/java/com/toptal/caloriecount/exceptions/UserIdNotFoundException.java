package com.toptal.caloriecount.exceptions;

public class UserIdNotFoundException extends Exception {
    public UserIdNotFoundException(String message, Throwable error) {
        super(message, error);
    }

    public UserIdNotFoundException(String message) {
        super(message);
    }
}
