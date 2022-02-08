package com.toptal.caloriecount.controller;

import com.toptal.caloriecount.dao.impl.CalorieLimitRepository;
import com.toptal.caloriecount.exceptions.FoodEntryNotFoundException;
import com.toptal.caloriecount.exceptions.UserIdNotFoundException;
import com.toptal.caloriecount.exceptions.UserNotAuthorizedException;
import com.toptal.caloriecount.payloads.request.calorieLimit.UpdateCalorieLimitRequest;
import com.toptal.caloriecount.payloads.request.foodEntry.FoodEntryRequest;
import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import com.toptal.caloriecount.services.interfaces.CalorieLimitService;
import com.toptal.caloriecount.shared.constants.ReturnCodeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("${calorieCount.app.calorieLimitUrl}")
public class CalorieLimitController {

    @Autowired
    CalorieLimitService calorieLimitService;

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateEntry(@PathVariable String userId, @Valid @RequestBody UpdateCalorieLimitRequest request) {
        try {
            log.info("Update Calorie Limit for: " + userId + " Service started");
            MessageResponse response = calorieLimitService.updateCalorieLimit(userId, request);
            log.info("Update Calorie Limit for: " + userId + " Service completed successfully");
            return ResponseEntity.ok(response);
        }
        catch(UserIdNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(new MessageResponse(e.getMessage(),
                    false, ReturnCodeConstants.USER_NOT_FOUND_ERROR));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), false, "CC-411" ));
        }
    }
}
