import http from "../http-common";
import {UserBase, UserInfo, UserInfoBaseResp} from "../model/user-info";
import BaseResp from "../model/base-resp"

export function userRegister(userInfo: UserInfo) {
    return http.post<UserInfo>(`/user/register`,userInfo);
}

export function userLogin(userBase: UserBase) {
    return http.post<BaseResp>(`/user/login`,userBase);
}

export function getUserInfo(jwt: string){
    return http.get<UserInfo>(`/user/info`,{headers: {Authorization: `${jwt}`}})
}