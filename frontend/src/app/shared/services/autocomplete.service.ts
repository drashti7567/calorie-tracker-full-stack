import { HttpClient } from "@angular/common/http";
import { APP_INITIALIZER, Injectable } from "@angular/core";
import { UrlConstants } from "app/shared/constants/url-constants";
import { BaseResponse } from "app/shared/models/base-response";
import { FoodEntryRequest } from "app/shared/models/request/foodEntry/food-entry-request.model";
import { GetFoodEntriesResponse } from "app/shared/models/response/foodEntry/get-food-entries-response.model";
import { ProductWithCalorieResponse } from "app/shared/models/response/searchFood/product-with-calorie-response.model";
import { SearchProductsResponse } from "app/shared/models/response/searchFood/search-products-response.model";
import { Observable } from "rxjs";
import { GetUsersResponse } from "../models/response/auth/get-users-response.model";
import { GetCalorieLimitResponse } from "../models/response/calorieLimit/get-calorie-limit-response.model";

@Injectable()
export class AutoCompleteService {

    constructor(private http: HttpClient) { }

    private searchUrl = UrlConstants.API_URL + UrlConstants.SEARCH_URL;
    private searchFoodUrl = this.searchUrl + UrlConstants.SEARCH_FOOD_URL;
    private searchCaloriesUrl = this.searchUrl + UrlConstants.SEARCH_CALORIES_URL;

    private authUrl = UrlConstants.API_URL + UrlConstants.AUTH_URL;
    private getUsersUrl = this.authUrl + UrlConstants.USERS_URL;


    private calorieLimitUrl = UrlConstants.API_URL + UrlConstants.CALORIE_LIMIT_URL;

    public searchFood(query: string): Observable<SearchProductsResponse> {
        /**
         * Function for autocomplete of food names
         * @param query: search inputted by user
         */

        return this.http.get<SearchProductsResponse>(`${this.searchFoodUrl}?query=${query}`);
    }

    public getCalories(query: string): Observable<ProductWithCalorieResponse> {
        /**
         * Function to calories of the product selected in autocomplete
         * @param query: food name
         */

        return this.http.get<ProductWithCalorieResponse>(`${this.searchCaloriesUrl}?query=${query}`);
    }

    public getUsers(): Observable<GetUsersResponse> {
        /**
         * Function to calories of the product selected in autocomplete
         * @param query: food name
         */

        return this.http.get<GetUsersResponse>(`${this.getUsersUrl}`);
    }
}
