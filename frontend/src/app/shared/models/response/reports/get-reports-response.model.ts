import { BaseResponse } from "../../base-response";

export interface GetReportsResponse extends BaseResponse{
    currentWeekEntriesCount?: number,
    pastWeekEntriesCount?: number,
    averageCaloriesCurrentWeek?: number,
    averageCaloriesPastWeek?: number,
    averageCaloriesList?: AverageCaloriePerUserResponse[],
    currentWeekEntriesList?: EntriesPerDay[],
    pastWeekEntriesList?: EntriesPerDay[]
}

export interface EntriesPerDay {
    date?: string,
    day?: string,
    totalEntries?: number
}

export interface AverageCaloriePerUserResponse {
    userId?: string,
    userName?: string,
    calories?: number,
    
}