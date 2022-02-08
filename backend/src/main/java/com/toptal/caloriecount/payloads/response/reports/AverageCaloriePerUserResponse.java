package com.toptal.caloriecount.payloads.response.reports;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AverageCaloriePerUserResponse {
    private String userId;
    private String userName;
    private Float calories;
}
