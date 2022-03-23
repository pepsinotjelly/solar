export interface UserContext{
    user_id:number;
    user_name:string;
    user_email:string;
    avatar_url:string;
}
export class UserContextImpl implements UserContext{
    user_id:number;
    user_name:string;
    user_email:string;
    avatar_url:string;

    constructor(user_id:number,user_name:string,user_email:string,avatar_url:string) {
        this.user_id=user_id;
        this.user_name = user_name;
        this.user_email=user_email;
        this.avatar_url =avatar_url;
    }
}