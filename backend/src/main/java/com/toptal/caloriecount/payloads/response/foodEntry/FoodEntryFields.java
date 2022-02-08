package com.toptal.caloriecount.payloads.response.foodEntry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodEntryFields {
    private String entryId;
    private String eatingTime;
    private String foodName;
    private Float calories;
    private String userId;
    private String userName;
    private Timestamp creationTimestamp;
    private Timestamp lastUpdatedTimestamp;
}
