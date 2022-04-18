import BaseResp from "./base-resp"
import {AvatarColor} from "@douyinfe/semi-ui/lib/es/avatar";

export interface UserInfo {
    userId: number;
    userName: string;
    userAvatar: AvatarColor | undefined;
    userEmail: string;
}

export interface UserBase {
    userEmail: string;
    userPwd: string;
}

export interface UserRegister {
    userName: string;
    userAvatar: AvatarColor | undefined;
    userPwd: string;
    userEmail: string;
}

export interface UserInfoBaseResp {
    userInfo: UserInfo,
    baseResp: BaseResp,
}