import http from "../http-common";
import {UserBase, UserInfo, UserInfoBaseResp} from "../model/user-info";

export function userRegister(userInfo: UserInfo) {
    return http.post<UserInfo>(`/user/register`,userInfo);
}

export function userLogin(userBase: UserBase) {
    return http.post<UserInfoBaseResp>(`/user/login`,userBase);
}