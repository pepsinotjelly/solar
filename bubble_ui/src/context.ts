import {createContext, useContext} from "react";

export interface UserContext {
    userId: number;
    userName: string;
    userAvatar: string;
}

export const getEmptyUser = (): UserContext => {
    return {userId: -1, userName: '', userAvatar: ''}
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
