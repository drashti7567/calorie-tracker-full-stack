package com.toptal.caloriecount.payloads.response.searchFood;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandedProductsResponse {
    private String food_name;
    private String serving_unit;
    private String nix_brand_id;
    private String brand_name_item_name;
    private String serving_qty;
    private Float nf_calories;
    private String brand_name;
}
