package com.toptal.caloriecount.payloads.response.misc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private String message;
    private boolean success;
    private String code;

    public void setMessageResponseVariables(String message, boolean success, String code) {
        this.message = message;
        this.code = code;
        this.success = success;
    }
}
