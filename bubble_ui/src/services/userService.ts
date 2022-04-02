import http from "../http-common";
import {UserBase, UserInfo, UserInfoBaseResp, UserRegister} from "../model/user-info";
import BaseResp from "../model/base-resp"

export function userLogin(username:string,password:string) {
    return http.post(`/login`,{},{params:{username:`${username}`,password:`${password}`}});
}

export function getUserInfo(jwt: string){
    return http.get<UserInfo>(`/user/info`,{headers: {Authorization: `${jwt}`}})
}

export function userBaseRegister(userRegister:UserRegister) {
    return http.post<BaseResp>(`/user/register`,userRegister)
}