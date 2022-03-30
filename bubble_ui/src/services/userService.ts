import http from "../http-common";
import {UserBase, UserInfo, UserInfoBaseResp, UserRegister} from "../model/user-info";
import BaseResp from "../model/base-resp"

export function userLogin(userBase: UserBase) {
    return http.post<BaseResp>(`/user/login`,userBase);
}

export function getUserInfo(jwt: string){
    return http.get<UserInfo>(`/user/info`,{headers: {Authorization: `${jwt}`}})
}

export function userBaseRegister(userRegister:UserRegister) {
    return http.post<BaseResp>(`/user/register`,userRegister)
}