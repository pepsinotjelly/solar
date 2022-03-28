import {Input, Toast} from "@douyinfe/semi-ui";
import {Modal, Button} from '@douyinfe/semi-ui';
import React, {useState} from "react";
import {getEmptyUser, useGlobalContext, UserContext} from "../../context";
import {useNavigate} from "react-router-dom";
import {userLogin} from "../../services/userService";
import {UserBase} from "../../model/user-info";
import {JWT} from "../../constants";

function LoginPage() {
    const navigate = useNavigate();
    const {userContext, setUserContext} = useGlobalContext()

    const [inputUserId, setInputUserId] = useState<string>("")
    const handelUserIdChange = (val: React.SetStateAction<string>) => setInputUserId(val);
    const [inputUserPwd, setInputUserPwd] = useState<string>("")
    const handelUserPwdChange = (val: React.SetStateAction<string>) => setInputUserPwd(val);

    // 退出登陆
    const Logout = () => {
        setUserContext(getEmptyUser())
        //  删除用户token
        localStorage.removeItem(JWT)
        Toast.success("Logout Success！")
        console.log("USER LOGOUT")
    }

    const onClose = () => {
        navigate("/", {replace: true});
        console.log("onClose :: close the window!")
    };

    //  发送登陆请求
    const handleLogin = async (
        e: React.MouseEvent<Element, MouseEvent>
    ) => {
        e.preventDefault();
        //  设置超时时间
        await new Promise((r) => setTimeout(r, 5000));
        //  数据处理
        try {
            const userBase: UserBase = {
                userId: Number.parseInt(inputUserId),
                userPwd: inputUserPwd
            }
            //  发送请求
            const resp = await userLogin(userBase);
            if (resp.status === 200) {
                if (resp.data.baseCode === 0) {
                    console.log("/user/login :: response.token :: ", resp.data.token)
                    localStorage.setItem(JWT, resp.data.token)
                    //  toast提示
                    Toast.success(`Hi Welcome Back`)
                } else {
                    Toast.error(resp.data.baseMsg)
                }
                await new Promise((r) => setTimeout(r, 100));
                navigate("/", {replace: true});
            }
        } catch (err) {
            Toast.error("登陆失败!")
        }
        //  更新输入框中的用户id状态
        setInputUserId("")
        //  更新输入框中的密码状态
        setInputUserPwd("")
    };
    return (
        <div style={{height: "590px"}}>
            <Modal
                title="用户登陆"
                visible={true}
                maskClosable={false}
                onCancel={onClose}
                onOk={handleLogin}
            >
                <Input className={"id-input-box"}
                       prefix={"enter  your  id :"}
                       showClear
                       value={inputUserId}
                       onChange={handelUserIdChange}
                />
                <br/><br/>
                <Input className={"pwd-input-box"}
                       mode="password"
                       prefix={"and password:"}
                       showClear
                       value={inputUserPwd}
                       onChange={handelUserPwdChange}/>
            </Modal>
        </div>
    );
}

export default LoginPage