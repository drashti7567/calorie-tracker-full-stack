package com.toptal.caloriecount.payloads.response.searchFood;

import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithCalorie extends MessageResponse {
    private String foodName;
    private Float calories;
}
