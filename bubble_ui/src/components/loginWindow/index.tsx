import {IconArrowRight, IconDoubleChevronRight} from "@douyinfe/semi-icons";
import {Avatar, Card, Dropdown, Input, Toast, TextArea} from "@douyinfe/semi-ui";
import {Modal, Button} from '@douyinfe/semi-ui';
import React, {useState} from "react";
import {getEmptyUser, useGlobalContext, UserContext} from "../../context";
import {Link, useNavigate} from "react-router-dom";
import RatingRecord from "../../model/rating-record";
import {submitRatingRecord} from "../../services/ratingRecordService";
import {userLogin} from "../../services/userService";
import {UserBase, UserInfo, UserInfoBaseResp} from "../../model/user-info";
import {JWT} from "../../constants";
import {userInfo} from "os";

function LoginWindow(props:{windowVisible:boolean}){
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

    //  控制登陆弹窗是否可见
    const [windowVisible, setWindowVisible] = useState(props.windowVisible);

    const onClose = () => {
        setWindowVisible(false);
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
            console.log("/user/login/ :: userBase :: ", userBase);
            //  发送请求
            const resp = await userLogin(userBase);
            setWindowVisible(false);
            if (resp.status === 200) {
                console.log("/user/login :: response :: ", resp)
                if (resp.data.baseCode === 0) {
                    console.log("/user/login :: response.token :: ", resp.data.token)
                    localStorage.setItem(JWT, resp.data.token)
                    //  toast提示
                    Toast.success(`Hi Welcome Back`)
                } else {
                    Toast.error(resp.data.baseMsg)
                }
            }
        } catch (err) {
            console.log("/user/login :: response.error :: ", err);
            Toast.error("登陆失败!")
        }
        //  更新输入框中的用户id状态
        setInputUserId("")
        //  更新输入框中的密码状态
        setInputUserPwd("")
        await new Promise((r) => setTimeout(r, 100));
        navigate("/", {replace: true});
    };
    return(
        <>
            <Modal
                title="用户登陆"
                visible={windowVisible}
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
        </>
    );
}
export default LoginWindow