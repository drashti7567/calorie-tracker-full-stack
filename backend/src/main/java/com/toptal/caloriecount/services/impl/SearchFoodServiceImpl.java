package com.toptal.caloriecount.services.impl;

import com.toptal.caloriecount.mapper.SearchFoodMapper;
import com.toptal.caloriecount.payloads.request.foodEntry.NutrientNutritionixRequest;
import com.toptal.caloriecount.payloads.response.misc.MessageResponse;
import com.toptal.caloriecount.payloads.response.searchFood.*;
import com.toptal.caloriecount.services.interfaces.SearchFoodService;
import com.toptal.caloriecount.shared.constants.MessageConstants;
import com.toptal.caloriecount.shared.constants.ReturnCodeConstants;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchFoodServiceImpl implements SearchFoodService {

    @Value("${nutritionix.search.url}")
    private String searchUrl;

    @Value("${nutritionix.nutrient.url}")
    private String nutrientUrl;

    @Value("${calorie.app.nutritionix.id}")
    private String nutritionixId;

    @Value("${calorie.app.nutritionix.key}")
    private String nutritionixKey;

    private SearchProductsResponse generateSearchFoodProductsResponse(
            ResponseEntity<SearchProductsNutritionixResponse> nutritionixResponse, String query) {
        /**
         * Function that converts the Nutritionix APi POJO into simplified POJo.
         */
        List<ProductWithCalorie> list = nutritionixResponse.getBody().getCommon().stream()
                .map(SearchFoodMapper::convertNutritionixSearchResponse).collect(Collectors.toList());
        list.addAll(nutritionixResponse.getBody().getBranded().stream()
                .map(SearchFoodMapper::convertNutritionixSearchResponse).collect(Collectors.toList()));

        SearchProductsResponse response = new SearchProductsResponse(list);
        response.setMessageResponseVariables(MessageConstants.AUTOCOMPLETE_FOOD_NAME_SUCCESSFUL + query,
                true, ReturnCodeConstants.SUCESS);

        return response;
    }

    private HttpEntity<?> getHeaders(String query) {
        /**
         * Function used to set the headers for API call
         */
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-app-id", this.nutritionixId);
        headers.add("x-app-key", this.nutritionixKey);
        headers.add("content-type", "application/json");
        if(query == null) return  new HttpEntity<>(headers);
        else {
            JSONObject object = new JSONObject();
            object.put("query", query);
            return new HttpEntity<>(object.toString(), headers);
        }
    }

    @Override
    public SearchProductsResponse getProducts(String query) {
        /**
         * Main Service function that calls the nutritionix api with query typed by the user.
         */

        RestTemplate template = new RestTemplate();

        HttpEntity<?> entity = this.getHeaders(null);

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("query", query);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(this.searchUrl)
                .queryParam("query", query).encode().toUriString();

        ResponseEntity<SearchProductsNutritionixResponse> nutritionixResponse = template.exchange(urlTemplate,
                HttpMethod.GET, entity, SearchProductsNutritionixResponse.class, urlParams);

        SearchProductsResponse response = this.generateSearchFoodProductsResponse(nutritionixResponse, query);

        return response;
    }

    @Override
    public ProductWithCalorie getCalorie(String query) {
        /**
         * Main Service function to get calories of common food item retrieved via instant search from the above api
         */
        RestTemplate template = new RestTemplate();

        HttpEntity<?> entity = this.getHeaders(query);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(this.nutrientUrl).encode().toUriString();

        ResponseEntity<NutrientNutritionixResponse> nutritionixResponse = template.exchange(urlTemplate,
                HttpMethod.POST, entity, NutrientNutritionixResponse.class);

        ProductWithCalorie productWithCalorie = new ProductWithCalorie(nutritionixResponse.getBody().getFoods().get(0).getFood_name(),
                nutritionixResponse.getBody().getFoods().get(0).getNf_calories());
        productWithCalorie.setMessageResponseVariables(MessageConstants.RETRIEVED_CALORIE_SUCCESSFUL + query,
                true, ReturnCodeConstants.SUCESS);

        return productWithCalorie;
    }
}
