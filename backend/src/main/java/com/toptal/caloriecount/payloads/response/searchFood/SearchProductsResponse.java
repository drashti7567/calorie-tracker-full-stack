package com.toptal.caloriecount.payloads.response.searchFood;

import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchProductsResponse extends MessageResponse {
    List<ProductWithCalorie> productList;
}
