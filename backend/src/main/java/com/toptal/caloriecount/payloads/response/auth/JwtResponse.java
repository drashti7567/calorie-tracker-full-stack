package com.toptal.caloriecount.payloads.response.auth;

import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse extends MessageResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String username;
    private String email;
    private String name;
    private List<String> roles;

    public JwtResponse(String accessToken, String id, String username, String email, String name,
                       List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.name = name;
    }
}
