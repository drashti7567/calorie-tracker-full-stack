package com.toptal.caloriecount.payloads.response.searchFood;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonProductsResponse {
    private String food_name;
    private String serving_unit;
    private String tag_name;
    private String serving_qty;
    private String common_type;
    private String tag_id;
}
