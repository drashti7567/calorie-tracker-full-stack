export interface GetReportsResponse {
    currentWeekEntriesCount?: number,
    pastWeekEntriesCount?: number,
    averageCaloriesList?: AverageCaloriePerUserResponse[]
}

export interface AverageCaloriePerUserResponse {
    userId?: string,
    userName?: string,
    calories?: number
}