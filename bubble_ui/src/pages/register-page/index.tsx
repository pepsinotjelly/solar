import {Avatar, Input, Popover, Toast, Upload} from "@douyinfe/semi-ui";
import {Modal, Button} from '@douyinfe/semi-ui';
import React, {useState} from "react";
import {getEmptyUser, useGlobalContext, UserContext} from "../../context";
import {Link, useNavigate} from "react-router-dom";
import {userLogin} from "../../services/userService";
import {UserBase, UserRegister} from "../../model/user-info";
import {JWT} from "../../constants";
import {IconCamera} from "@douyinfe/semi-icons";
import UserRegisterPanel from "./components/userRegisterPanel";
import AvatarUpload from "./components/avatarUpload";

function RegisterPage() {
    const {userContext, setUserContext} = useGlobalContext();
    const jwt = localStorage.getItem(JWT)
    if(!jwt){
        return (
            <div>
                <AvatarUpload/>
            </div>
        );
    }
    return (
        <div>
            <UserRegisterPanel/>
        </div>
    );
}

export default RegisterPage