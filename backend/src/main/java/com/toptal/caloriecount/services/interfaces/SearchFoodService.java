package com.toptal.caloriecount.services.interfaces;

import com.toptal.caloriecount.payloads.response.searchFood.ProductWithCalorie;
import com.toptal.caloriecount.payloads.response.searchFood.SearchProductsResponse;

public interface SearchFoodService {
    SearchProductsResponse getProducts(String query);

    ProductWithCalorie getCalorie(String query);
}
