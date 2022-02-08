package com.toptal.caloriecount.controller;

import com.toptal.caloriecount.payloads.response.foodEntry.GetFoodEntriesResponse;
import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import com.toptal.caloriecount.payloads.response.reports.GetReportsResponse;
import com.toptal.caloriecount.services.interfaces.ReportsService;
import com.toptal.caloriecount.shared.constants.MessageConstants;
import com.toptal.caloriecount.shared.constants.ReturnCodeConstants;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("${calorieCount.app.reportsUrl}")
public class ReportsController {

    @Autowired
    ReportsService reportsService;

    @GetMapping("/")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getReports() {
        try {
            log.info("Get Reports Service started");
            GetReportsResponse response = reportsService.getReports();
            log.info("Get Reports Service completed successfully");
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), false, "CC-411" ));
        }
    }
}
