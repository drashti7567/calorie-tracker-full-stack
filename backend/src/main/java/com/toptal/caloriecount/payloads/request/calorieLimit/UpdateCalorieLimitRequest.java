package com.toptal.caloriecount.payloads.request.calorieLimit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCalorieLimitRequest {
    private Float carbs;
    private Float protein;
    private Float fat;
}
