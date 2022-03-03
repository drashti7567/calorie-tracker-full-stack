package com.toptal.caloriecount.payloads.response.auth;

import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetTokenResponse extends MessageResponse {
    private String newToken;
}
