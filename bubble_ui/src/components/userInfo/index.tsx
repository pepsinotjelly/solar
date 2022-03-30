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

function UserInfoPanel() {
    const {Meta} = Card;
    const {userContext, setUserContext} = useGlobalContext()


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
                    <Avatar size="small" src={userContext.userAvatar}/>
                </Dropdown>
            </div>
        );
    }
    return (
        <>
            <Dropdown
                trigger={'hover'}
                position={"bottomLeft"}
                render={
                    <Dropdown.Menu>
                        <Link to={"/login"}>
                        <Dropdown.Item
                            icon={<IconDoubleChevronRight/>}
                        >
                            立即登录
                        </Dropdown.Item>
                        </Link>
                    </Dropdown.Menu>
                }
            >
                <Avatar size="small">未登录</Avatar>
            </Dropdown>
        </>
    );
};
export default UserInfoPanel