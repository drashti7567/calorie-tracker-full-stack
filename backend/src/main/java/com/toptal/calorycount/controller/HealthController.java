package com.toptal.calorycount.controller;

import com.toptal.calorycount.payloads.response.misc.MessageResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping("/")
    @ApiOperation(value = "Health Endpoint")
    public ResponseEntity<?> checkHealth() {
        return ResponseEntity.ok(new MessageResponse("Application is up", true, "CC-211"));
    }
}