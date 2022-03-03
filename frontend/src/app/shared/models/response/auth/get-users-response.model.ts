import { BaseResponse } from "../../base-response";

export interface GetUsersResponse extends BaseResponse {
    usersList: UserFields[]
}

export interface UserFields {
    userId: string,
    name: string,
    type: string
}