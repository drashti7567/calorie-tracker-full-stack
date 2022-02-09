import { HttpClient } from "@angular/common/http";
import { APP_INITIALIZER, Injectable } from "@angular/core";
import { UrlConstants } from "app/shared/constants/url-constants";
import { BaseResponse } from "app/shared/models/base-response";
import { FoodEntryRequest } from "app/shared/models/request/foodEntry/food-entry-request.model";
import { GetFoodEntriesResponse } from "app/shared/models/response/foodEntry/get-food-entries-response.model";
import { ProductWithCalorieResponse } from "app/shared/models/response/searchFood/product-with-calorie-response.model";
import { SearchProductsResponse } from "app/shared/models/response/searchFood/search-products-response.model";
import { Observable } from "rxjs";

@Injectable()
export class HomeService {

    constructor(private http: HttpClient) { }

    private foodEntryUrl = UrlConstants.API_URL + UrlConstants.FOOD_ENTRY_URL;

    private searchUrl = UrlConstants.API_URL + UrlConstants.SEARCH_URL;
    private searchFoodUrl = this.searchUrl + UrlConstants.SEARCH_FOOD_URL;
    private searchCaloriesUrl = this.searchUrl + UrlConstants.SEARCH_CALORIES_URL;


    public getFoodEntries(userId: string): Observable<GetFoodEntriesResponse> {
        /**
         * Function to get the users food entries
         * @param userId: Id of the user
         */

        return this.http.get<GetFoodEntriesResponse>(`${this.foodEntryUrl}/${userId}`);
    }

    public addFoodEntry(request: FoodEntryRequest): Observable<BaseResponse> {
        /**
         * Function to add new food entry
         * @param request: New Entry request object
         */

        return this.http.post<BaseResponse>(`${this.foodEntryUrl}`, request);
    }

    public updateFoodEntry(entryId: string, request: FoodEntryRequest): Observable<BaseResponse> {
        /**
         * Function to update new food entry
         * @param entryId: Id of the food entry
         * @param request: Update Entry request object
         */

        return this.http.put<BaseResponse>(`${this.foodEntryUrl}/${entryId}`, request);
    }

    public deleteFoodEntry(entryId: string, userId: string): Observable<BaseResponse> {
        /**
         * Function to delete new food entry
         * @param entryId: Id of the food entry
         */

        return this.http.delete<BaseResponse>(`${this.foodEntryUrl}/${userId}/${entryId}`);
    }

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
}
