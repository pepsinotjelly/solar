import BaseResp from "./base-resp"
export  interface UserInfo {
    userId:number;
    userName:string;
    userAvatar:string;
}
export interface UserBase {
    userId:number;
    userPwd:string;
}
export interface UserInfoBaseResp {
    userInfo:UserInfo,
    baseResp:BaseResp,
}