import {IconArrowRight, IconDoubleChevronRight} from "@douyinfe/semi-icons";
import {Avatar, Card, Dropdown, Input, Toast, TextArea} from "@douyinfe/semi-ui";
import {Modal, Button} from '@douyinfe/semi-ui';
import React, {useState} from "react";
import {getEmptyUser, useGlobalContext, UserContext} from "../../context";
import {Link} from "react-router-dom";
import RatingRecord from "../../model/rating-record";
import {submitRatingRecord} from "../../services/ratingRecordService";
import {userLogin} from "../../services/userService";
import {UserBase, UserInfo, UserInfoBaseResp} from "../../model/user-info";
import {JWT} from "../../constants";
import {userInfo} from "os";

function UserInfoPanel() {
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
    }

    //  控制登陆弹窗是否可见
    const [windowVisible, setWindowVisible] = useState(false);
    const onClose = () => {
        setWindowVisible(false);
        console.log("close the window!")
    };

    //  发送登陆请求
    const handleLogin = async (
        e: React.MouseEvent<Element, MouseEvent>
    ) => {
        e.preventDefault();
        setWindowVisible(false);
        //  设置超时时间
        await new Promise((r) => setTimeout(r, 5000));
        //  数据处理
        try {
            const userBase: UserBase = {
                userId: Number.parseInt(inputUserId),
                userPwd: inputUserPwd
            }
            console.log(userBase);
            //  发送请求
            const resp = await userLogin(userBase);
            if (resp.status === 200) {
                console.log(resp)
                if (resp.data.baseResp.baseCode === 0) {
                    localStorage.setItem(JWT,resp.data.baseResp.token)
                    //  toast提示
                    Toast.success(`Hi,${resp.data.userInfo.userName}`)
                     //  更新用户id状态
                    setInputUserId("")
                    //  更新密码状态
                    setInputUserPwd("")
                    //  刷新登陆数据
                    setUserContext({
                        userId: resp.data.userInfo.userId,
                        userName: resp.data.userInfo.userName,
                        userAvatar: resp.data.userInfo.userAvatar
                    })
                } else {
                    Toast.error(resp.data.baseResp.baseMsg)
                    //  更新用户id状态
                    setInputUserId("")
                    //  更新密码状态
                    setInputUserPwd("")
                }
            }
        } catch (err) {
            //  更新用户id状态
            setInputUserId("")
            //  更新密码状态
            setInputUserPwd("")
            console.log(err);
            Toast.error("哎呀!登陆失败!")
        }
    };

    //  已登陆
    if (userContext.userId > 0) {
        return (
            <div>
                <Dropdown
                    trigger={"click"}
                    position={"bottomLeft"}
                    render={
                        <Dropdown.Menu>
                            <Dropdown.Item
                                icon={<IconDoubleChevronRight/>}
                                onClick={Logout}
                            >
                                退出登陆
                            </Dropdown.Item>
                        </Dropdown.Menu>
                    }
                >
                    <Avatar size="small">未登录</Avatar>
                </Dropdown>
            </div>
        );
    } else {
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
                <Modal
                    title="用户登陆"
                    visible={windowVisible}
                    maskClosable={false}
                    onCancel={onClose}
                    onOk={handleLogin}
                >
                    <Input className={"id-input-box"}
                           prefix={"enter your email:"}
                           showClear
                           value={inputUserId}
                           onChange={handelUserIdChange}
                    />
                    <br/><br/>
                    <Input className={"pwd-input-box"}
                           mode="password"
                           prefix={"enter password:"}
                           showClear
                           value={inputUserPwd}
                           onChange={handelUserPwdChange}/>
                </Modal>
            </>

        );
    }
};
export default UserInfoPanel