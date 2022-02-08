package com.toptal.caloriecount.mapper;

import com.toptal.caloriecount.dao.models.FoodEntry;
import com.toptal.caloriecount.dao.models.Users;
import com.toptal.caloriecount.payloads.request.foodEntry.FoodEntryRequest;
import com.toptal.caloriecount.payloads.response.searchFood.*;
import com.toptal.caloriecount.shared.utils.CommonUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.sql.Timestamp;
import java.util.Date;

public class SearchFoodMapper {
    private static ModelMapper mapper = new ModelMapper();

    public static ProductWithCalorie convertNutritionixSearchResponse(CommonProductsResponse commonProductsResponse) {
        /**
         * Mapper function to convert AddEntryRequest object to Food Entry Entity
         */
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ProductWithCalorie productWithCalorie = mapper.map(commonProductsResponse, ProductWithCalorie.class);
        productWithCalorie.setFoodName(commonProductsResponse.getFood_name());
        return productWithCalorie;
    }

    public static ProductWithCalorie convertNutritionixSearchResponse(BrandedProductsResponse brandedProductsResponse) {
        /**
         * Mapper function to convert AddEntryRequest object to Food Entry Entity
         */
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ProductWithCalorie productWithCalorie = mapper.map(brandedProductsResponse, ProductWithCalorie.class);
        productWithCalorie.setFoodName(brandedProductsResponse.getFood_name());
        productWithCalorie.setCalories(brandedProductsResponse.getNf_calories());
        return productWithCalorie;
    }
}
