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


    public getFoodEntries(userId: string, dateFrom?, dateTo?): Observable<GetFoodEntriesResponse> {
        /**
         * Function to get the users food entries
         * @param userId: Id of the user
         */

        const params = `${!!dateFrom ? "dateFrom=" + dateFrom: ""}${!!dateTo ? "&dateTo=" + dateTo: ""};`

        return this.http.get<GetFoodEntriesResponse>(`${this.foodEntryUrl}/${userId}?${params}`);
    }

    public addFoodEntry(request: FoodEntryRequest): Observable<BaseResponse> {
        /**
         * Function to add new food entry
         * @param request: New Entry request object
         */

        return this.http.post<BaseResponse>(`${this.foodEntryUrl}/`, request);
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
}
