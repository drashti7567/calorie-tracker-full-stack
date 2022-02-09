import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BaseRequestOptions } from "@angular/http";
import { UrlConstants } from "app/shared/constants/url-constants";
import { BaseResponse } from "app/shared/models/base-response";
import { FoodEntryRequest } from "app/shared/models/request/foodEntry/food-entry-request.model";
import { GetFoodEntriesResponse } from "app/shared/models/response/foodEntry/get-food-entries-response.model";
import { Observable } from "rxjs";

@Injectable()
export class AllEntriesService {

    constructor(private http: HttpClient) { }

    private foodEntryUrl = UrlConstants.API_URL + UrlConstants.FOOD_ENTRY_URL;
    private allFoodEntryUrl = this.foodEntryUrl + UrlConstants.ALL_ENTRIES_URL;

    private reportsURL = UrlConstants.API_URL + UrlConstants.REPORTS_URL;

    public getFoodEntries(): Observable<GetFoodEntriesResponse> {
        /**
         * Function to get all the users food entries
         */

        return this.http.get<GetFoodEntriesResponse>(`${this.allFoodEntryUrl}`);
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
