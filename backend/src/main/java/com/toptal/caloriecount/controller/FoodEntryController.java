package com.toptal.caloriecount.controller;

import com.toptal.caloriecount.exceptions.FoodEntryNotFoundException;
import com.toptal.caloriecount.exceptions.UserIdNotFoundException;
import com.toptal.caloriecount.exceptions.UserNotAuthorizedException;
import com.toptal.caloriecount.payloads.request.foodEntry.FoodEntryRequest;
import com.toptal.caloriecount.payloads.response.foodEntry.GetFoodEntriesResponse;
import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import com.toptal.caloriecount.services.interfaces.FoodEntryService;
import com.toptal.caloriecount.shared.constants.ReturnCodeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("${calorieCount.app.entryUrl}")
public class FoodEntryController {

    @Autowired
    FoodEntryService foodEntryService;

    @PostMapping("/")
    public ResponseEntity<?> addEntry(@Valid @RequestBody FoodEntryRequest request) {
        try {
            log.info("Add Food Entry Service started");
            MessageResponse response = foodEntryService.addEntry(request);
            log.info("Add Food Entry Service completed successfully");
            return ResponseEntity.ok(response);
        }
        catch(UserIdNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(new MessageResponse(e.getMessage(), false, ReturnCodeConstants.USER_NOT_FOUND_ERROR));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), false, "CC-411" ));
        }
    }

    @PutMapping("/{entryId}")
    public ResponseEntity<?> updateEntry(@PathVariable String entryId, @Valid @RequestBody FoodEntryRequest request) {
        try {
            log.info("Update Food Entry: " + entryId + " Service started");
            MessageResponse response = foodEntryService.updateEntry(entryId, request);
            log.info("Update Food Entry: " + entryId + " Service completed successfully");
            return ResponseEntity.ok(response);
        }
        catch(FoodEntryNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(new MessageResponse(e.getMessage(),
                    false, ReturnCodeConstants.FOOD_ENTRY_NOT_FOUND_ERROR));
        }
        catch(UserIdNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(new MessageResponse(e.getMessage(),
                    false, ReturnCodeConstants.USER_NOT_FOUND_ERROR));
        }
        catch(UserNotAuthorizedException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(new MessageResponse(e.getMessage(),
                    false, ReturnCodeConstants.USER_NOT_AUTHORIZED_ERROR));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), false, "CC-411" ));
        }
    }

    @DeleteMapping("/{userId}/{entryId}")
    public ResponseEntity<?> deleteEntry(@PathVariable String userId ,@PathVariable String entryId) {
        try {
            log.info("Delete Food Entry: " + entryId + " Service started");
            MessageResponse response = foodEntryService.deleteEntry(userId, entryId);
            log.info("Delete Food Entry: " + entryId + " Service completed successfully");
            return ResponseEntity.ok(response);
        }
        catch(FoodEntryNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(new MessageResponse(e.getMessage(),
                    false, ReturnCodeConstants.FOOD_ENTRY_NOT_FOUND_ERROR));
        }
        catch(UserIdNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(new MessageResponse(e.getMessage(),
                    false, ReturnCodeConstants.USER_NOT_FOUND_ERROR));
        }
        catch(UserNotAuthorizedException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(new MessageResponse(e.getMessage(),
                    false, ReturnCodeConstants.USER_NOT_AUTHORIZED_ERROR));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), false, "CC-411" ));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getEntries(@PathVariable String userId, @RequestParam Map<String, String> params) {
        try {
            log.info("Get All Food Entries for" + userId + " Service started");
            GetFoodEntriesResponse response = foodEntryService.getEntries(userId, params);
            log.info("Get All Food Entries for" + userId + " Service completed successfully");
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

    @GetMapping("${calorieCount.app.getAllEntries}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllEntries() {
        try {
            log.info("Get All Food Entries Service started");
            GetFoodEntriesResponse response = foodEntryService.getAllEntries();
            log.info("Get All Food Entries Service completed successfully");
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), false, "CC-411" ));
        }
    }
}
