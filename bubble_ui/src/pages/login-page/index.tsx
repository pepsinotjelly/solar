import {Input, Toast} from "@douyinfe/semi-ui";
import {Modal, Button} from '@douyinfe/semi-ui';
import React, {useState} from "react";
import {getEmptyUser, useGlobalContext, UserContext} from "../../context";
import {Link, useNavigate} from "react-router-dom";
import {userLogin} from "../../services/userService";
import {UserBase} from "../../model/user-info";
import {JWT} from "../../constants";
import {IconHelpCircle} from "@douyinfe/semi-icons";
import {Popover} from '@douyinfe/semi-ui';

function LoginPage() {
    const navigate = useNavigate();
    const {userContext, setUserContext} = useGlobalContext()

    const [inputUserEmail, setInputUserEmail] = useState<string>("")
    const handelUserEmailChange = (val: React.SetStateAction<string>) => setInputUserEmail(val);
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
                userEmail: inputUserEmail,
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
        setInputUserEmail("")
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
                cancelText={"home"}
                onOk={handleLogin}
                okText={"login"}
                icon={<Popover
                    content={
                        <article style={{padding: 12}}>
                            New to here ?
                            <br/> Click to register!
                            <br/>
                            <br/>
                            <Link to={"/register"}>
                                <Button>
                                    register
                                </Button>
                            </Link>
                        </article>
                    }
                >
                    <IconHelpCircle></IconHelpCircle>
                </Popover>}
            >
                <Input className={"email-input-box"}
                       prefix={"enter your email:"}
                       showClear
                       value={inputUserEmail}
                       onChange={handelUserEmailChange}
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