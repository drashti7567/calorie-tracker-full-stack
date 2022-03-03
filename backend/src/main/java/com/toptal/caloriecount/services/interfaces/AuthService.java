package com.toptal.caloriecount.services.interfaces;

import com.toptal.caloriecount.payloads.request.auth.LoginRequest;
import com.toptal.caloriecount.payloads.response.auth.GetUsersResponse;
import com.toptal.caloriecount.payloads.response.auth.JwtResponse;
import com.toptal.caloriecount.payloads.response.auth.ResetTokenResponse;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);

    ResetTokenResponse refreshToken(String bearerToken) throws Exception;

    GetUsersResponse getUsers();
}
