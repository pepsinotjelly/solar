import http from "../http-common";
import UserInfo from "../model/user-info";

export function userRegister(userInfo: UserInfo) {
    return http.post<UserInfo>(`/user/register`,userInfo);
}

export function userLogin(userInfo: UserInfo) {
    return http.post<UserInfo>(`/user/login`,userInfo);
}