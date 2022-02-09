import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UrlConstants } from "app/shared/constants/url-constants";
import { BaseResponse } from "app/shared/models/base-response";
import { FoodEntryRequest } from "app/shared/models/request/foodEntry/food-entry-request.model";
import { GetFoodEntriesResponse } from "app/shared/models/response/foodEntry/get-food-entries-response.model";
import { GetReportsResponse } from "app/shared/models/response/reports/get-reports-response.model";
import { Observable } from "rxjs";

@Injectable()
export class ReportsService {

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

    public getReports(): Observable<GetReportsResponse> {
        /**
         * Function to get all the users food entries
         */

        return this.http.get<GetReportsResponse>(`${this.getReports}`);
    }
}
