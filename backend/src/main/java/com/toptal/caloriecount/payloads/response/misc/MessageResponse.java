package com.toptal.caloriecount.payloads.response.misc;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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
