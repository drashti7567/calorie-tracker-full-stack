package com.toptal.caloriecount.controller;

import com.toptal.caloriecount.payloads.request.auth.LoginRequest;
import com.toptal.caloriecount.payloads.response.auth.JwtResponse;
import com.toptal.caloriecount.payloads.response.auth.ResetTokenResponse;
import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import com.toptal.caloriecount.services.interfaces.AuthService;
import com.toptal.caloriecount.shared.constants.ReturnCodeConstants;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("${calorieCount.app.authUrl}")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("${calorieCount.app.signInUrl}")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            log.info("Authenticate service started successfully");
            JwtResponse response = authService.authenticateUser(loginRequest);
            log.info("Authenticate service ended successfully");
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), false, ReturnCodeConstants.UNKNOWN_ERROR ));
        }
    }

    @GetMapping("${calorieCount.app.refreshTokenUrl}")
    @ApiOperation(value="Refresh Token controller")
    public ResponseEntity<?> refreshToken(@RequestHeader(value= HttpHeaders.AUTHORIZATION) String bearerToken) {
        try {
            ResetTokenResponse response = authService.refreshToken(bearerToken);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(e.getMessage(), false, ReturnCodeConstants.UNKNOWN_ERROR));
        }
    }
}
