package com.toptal.caloriecount.services.interfaces;

import com.toptal.caloriecount.payloads.request.auth.LoginRequest;
import com.toptal.caloriecount.payloads.response.auth.JwtResponse;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);
}
