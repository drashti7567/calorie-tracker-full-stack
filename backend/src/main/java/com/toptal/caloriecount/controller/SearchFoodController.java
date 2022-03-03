package com.toptal.caloriecount.controller;

import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import com.toptal.caloriecount.payloads.response.searchFood.ProductWithCalorie;
import com.toptal.caloriecount.payloads.response.searchFood.SearchProductsNutritionixResponse;
import com.toptal.caloriecount.payloads.response.searchFood.SearchProductsResponse;
import com.toptal.caloriecount.services.interfaces.SearchFoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("${calorieCount.app.searchUrl}")
public class SearchFoodController {

    @Autowired
    SearchFoodService searchFoodService;

    @GetMapping("${calorieCount.app.searchFoodUrl}")
    public ResponseEntity<?> getFoodProducts(@RequestParam String query) {
        try {
            log.info("Get Food Products for autocomplete Service started");
            SearchProductsResponse response = searchFoodService.getProducts(query);
            log.info("Get Food Products for autocomplete Service started");
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), false, "CC-411" ));
        }
    }

    @GetMapping("${calorieCount.app.getCalorieUrl}")
    public ResponseEntity<?> getCalorie(@RequestParam String query) {
        try {
            log.info("Get Calorie for selected Product: " + query + " Service started");
            ProductWithCalorie response = searchFoodService.getCalorie(query);
            log.info("Get Calorie for selected Product: " + query + " Service completed successfully");
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), false, "CC-411" ));
        }
    }
}
