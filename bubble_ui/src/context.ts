import {createContext, useContext} from "react";
import {AvatarColor} from "@douyinfe/semi-ui/lib/es/avatar";

export interface UserContext {
    userId: number;
    userName: string;
    userAvatar: AvatarColor | undefined;
}

export const getEmptyUser = (): UserContext => {
    return {userId: -1, userName: '', userAvatar: 'grey'}
}

export interface GlobalContext {
    userContext: UserContext;
    setUserContext: (value: UserContext) => void;
}

export const GlobalContext =createContext<GlobalContext>({
    userContext:getEmptyUser(),
    setUserContext:()=>{}
})

export const useGlobalContext = () => useContext(GlobalContext);
