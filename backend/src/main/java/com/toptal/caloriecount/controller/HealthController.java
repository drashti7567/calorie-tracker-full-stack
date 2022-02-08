package com.toptal.caloriecount.controller;

import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import com.toptal.caloriecount.shared.constants.MessageConstants;
import com.toptal.caloriecount.shared.constants.ReturnCodeConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("${calorieCount.app.healthUrl}")
public class HealthController {

    @GetMapping("/")
    @ApiOperation(value = "Health Endpoint")
    public ResponseEntity<?> checkHealth() {
        return ResponseEntity.ok(new MessageResponse(MessageConstants.HEALTH_CHECK_SUCCESSFUL, true, ReturnCodeConstants.SUCESS));
    }
}