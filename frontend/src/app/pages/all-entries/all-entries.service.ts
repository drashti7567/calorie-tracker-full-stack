import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UrlConstants } from "app/shared/constants/url-constants";
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
}
