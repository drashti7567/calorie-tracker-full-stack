package com.toptal.caloriecount.payloads.request.foodEntry;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodEntryRequest {
    private String userId;
    private String foodName;
    private String eatingTime;
    private Float calories;
}
