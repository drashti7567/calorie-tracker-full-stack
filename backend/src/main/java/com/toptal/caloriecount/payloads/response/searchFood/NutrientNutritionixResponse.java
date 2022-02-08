package com.toptal.caloriecount.payloads.response.searchFood;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NutrientNutritionixResponse {
    private List<BrandedProductsResponse> foods;
}
