import { BaseResponse } from "../../base-response";
import { ProductWithCalorieResponse } from "./product-with-calorie-response.model";

export interface SearchProductsResponse extends BaseResponse {
    productList?: ProductWithCalorieResponse[]
}