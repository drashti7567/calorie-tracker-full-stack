package com.toptal.caloriecount.exceptions;

public class FoodEntryNotFoundException extends Exception {
    public FoodEntryNotFoundException(String message, Throwable error) {
        super(message, error);
    }

    public FoodEntryNotFoundException(String message) {
        super(message);
    }
}
