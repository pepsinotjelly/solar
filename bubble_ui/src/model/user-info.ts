import BaseResp from "./base-resp"
export  interface UserInfo {
    userId:number;
    userName:string;
    userAvatar:string;
}
export interface UserBase {
    userEmail:string;
    userPwd:string;
}
export interface UserRegister{
    userName:string;
    userAvatar:string;
    userPwd:string;
    userEmail:string;
}
export interface UserInfoBaseResp {
    userInfo:UserInfo,
    baseResp:BaseResp,
}