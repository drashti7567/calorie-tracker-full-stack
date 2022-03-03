import { BaseResponse } from "../../base-response";

export interface ProductWithCalorieResponse extends BaseResponse {
    foodName?: string,
    calories?: number
}