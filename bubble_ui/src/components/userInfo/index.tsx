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
import LoginWindow from "../loginWindow";

function UserInfoPanel() {
    const {Meta} = Card;
    // const navigate = useNavigate();
    const {userContext, setUserContext} = useGlobalContext()

    // const [inputUserId, setInputUserId] = useState<string>("")
    // const handelUserIdChange = (val: React.SetStateAction<string>) => setInputUserId(val);
    // const [inputUserPwd, setInputUserPwd] = useState<string>("")
    // const handelUserPwdChange = (val: React.SetStateAction<string>) => setInputUserPwd(val);

    // 退出登陆
    const Logout = () => {
        setUserContext(getEmptyUser())
        //  删除用户token
        localStorage.removeItem(JWT)
        Toast.success("Logout Success！")
        console.log("USER LOGOUT")
    }

     //控制登陆弹窗是否可见
    const [windowVisible, setWindowVisible] = useState(false);
    const onClose = () => {
        setWindowVisible(false);
        console.log("onClose :: close the window!")
    };

    //  发送登陆请求
    // const handleLogin = async (
    //     e: React.MouseEvent<Element, MouseEvent>
    // ) => {
    //     e.preventDefault();
    //     //  设置超时时间
    //     await new Promise((r) => setTimeout(r, 5000));
    //     //  数据处理
    //     try {
    //         const userBase: UserBase = {
    //             userId: Number.parseInt(inputUserId),
    //             userPwd: inputUserPwd
    //         }
    //         console.log("/user/login/ :: userBase :: ", userBase);
    //         //  发送请求
    //         const resp = await userLogin(userBase);
    //         setWindowVisible(false);
    //         if (resp.status === 200) {
    //             console.log("/user/login :: response :: ", resp)
    //             if (resp.data.baseCode === 0) {
    //                 console.log("/user/login :: response.token :: ", resp.data.token)
    //                 localStorage.setItem(JWT, resp.data.token)
    //                 //  toast提示
    //                 Toast.success(`Hi Welcome Back`)
    //             } else {
    //                 Toast.error(resp.data.baseMsg)
    //             }
    //         }
    //     } catch (err) {
    //         console.log("/user/login :: response.error :: ", err);
    //         Toast.error("登陆失败!")
    //     }
    //     //  更新输入框中的用户id状态
    //     setInputUserId("")
    //     //  更新输入框中的密码状态
    //     setInputUserPwd("")
    //     await new Promise((r) => setTimeout(r, 100));
    //     navigate("/", {replace: true});
    // };

    //  已登陆
    if (userContext.userId > 0) {
        return (
            <div>
                <Dropdown
                    trigger={"click"}
                    position={"bottomLeft"}
                    render={
                        <Dropdown.Menu>
                            <Card style={{maxWidth: 340, borderRadius: "0px"}}>
                                <Meta
                                    title={userContext.userName}
                                    description={"userContext-email"}
                                    avatar={<Avatar size="small" src={userContext.userAvatar}/>}
                                />
                            </Card>
                            <Dropdown.Item icon={<IconArrowRight/>} onClick={Logout}>
                                退出
                            </Dropdown.Item>
                        </Dropdown.Menu>
                    }
                >
                    <Avatar size="small" src={userContext.userAvatar} />
                </Dropdown>
            </div>
        );
    }
    return (
        <>
            <Dropdown
                trigger={'hover'}
                position={"bottomLeft"}
                render={<Dropdown.Menu>
                    <Dropdown.Item
                        icon={<IconDoubleChevronRight/>}
                        onClick={() => setWindowVisible(true)}
                    >
                        立即登录
                    </Dropdown.Item>
                </Dropdown.Menu>}
            >
                <Avatar size="small">未登录</Avatar>
            </Dropdown>
            <LoginWindow windowVisible={windowVisible}/>
        </>

    );

};
export default UserInfoPanel