import { HttpClient } from "@angular/common/http";
import { APP_INITIALIZER, Injectable } from "@angular/core";
import { UrlConstants } from "app/shared/constants/url-constants";
import { BaseResponse } from "app/shared/models/base-response";
import { FoodEntryRequest } from "app/shared/models/request/foodEntry/food-entry-request.model";
import { GetFoodEntriesResponse } from "app/shared/models/response/foodEntry/get-food-entries-response.model";
import { ProductWithCalorieResponse } from "app/shared/models/response/searchFood/product-with-calorie-response.model";
import { SearchProductsResponse } from "app/shared/models/response/searchFood/search-products-response.model";
import { Observable } from "rxjs";
import { GetCalorieLimitResponse } from "../models/response/calorieLimit/get-calorie-limit-response.model";

@Injectable()
export class CalorieLimitService {

    constructor(private http: HttpClient) { }

    private calorieLimitUrl = UrlConstants.API_URL + UrlConstants.CALORIE_LIMIT_URL;

    public getCalorieLimit(userId: string): Observable<GetCalorieLimitResponse> {
        /**
         * Function to get the users calorie limit
         * @param userId: Id of the user
         */


        return this.http.get<GetFoodEntriesResponse>(`${this.calorieLimitUrl}/${userId}`);
    }
}
