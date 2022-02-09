import { BaseResponse } from "../../base-response";

export interface GetFoodEntriesResponse extends BaseResponse{
    calorieLimit?: number,
    foodEntryList?: FoodEntryFields[]
}

export interface FoodEntryFields {
    entryId?: string,
    eatingTime?: string,
    foodName?: string,
    calories?: number,
    userId?: string,
    userName?: string,
    creationTimestamp?: string,
    lastUpdatedTimestamp?: string,
}