import React, {useState} from "react";
import {Avatar, Modal, Upload} from "@douyinfe/semi-ui";
import {Input, Toast} from "@douyinfe/semi-ui";
import {IconCamera} from "@douyinfe/semi-icons";
import Text from "@douyinfe/semi-ui/lib/es/typography/text";
import {useNavigate} from "react-router-dom";
import {UserRegister} from "../../../model/user-info";
import {userBaseRegister, userLogin, userUpdateAvatar} from "../../../services/userService";
import {JWT} from "../../../constants";
import {AvatarColor} from "@douyinfe/semi-ui/avatar/interface";


function AvatarUpdate() {
    const navigate = useNavigate()
    const [avatarColor, setAvatarColor] = useState<AvatarColor>('grey')
    const [inputUserAvatar, setInputUserAvatar] = useState('https://sf6-cdn-tos.douyinstatic.com/obj/eden-cn/ptlz_zlp/ljhwZthlaukjlkulzlp/root-web-sites/avatarDemo.jpeg');
    const handelUserAvatarChange = (val: React.SetStateAction<string>) => setInputUserAvatar(val)

    const handleUpdateAvatar = async (
        e: React.MouseEvent<Element, MouseEvent>
    ) => {
        e.preventDefault();
        //  设置超时时间
        await new Promise((r) => setTimeout(r, 5000));
        //  数据处理
        try {
            //  发送请求
            const resp = await userUpdateAvatar(avatarColor, localStorage.getItem(JWT) ?? "");
            if (resp.status === 200) {
                Toast.success(`头像设置成功`)
                await new Promise((r) => setTimeout(r, 100));
                navigate("/", {replace: true});
            }
        } catch (err) {
            Toast.error("头像设置失败!")
        }
    };

    const onClose = () => {
        navigate("/login", {replace: true});
        console.log("onClose :: close the registerWindow!")
    };

    return (
        <div>
            <Modal
                title="用户注册"
                visible={true}
                maskClosable={false}
                onCancel={onClose}
                cancelText={"back"}
                onOk={handleUpdateAvatar}
                okText={"register"}
                style={{height: "300px"}}
            >
                <text>click and choose one of your avatar color :</text>
                <br/><br/>
                <Avatar color={"grey"} onClick={() => setAvatarColor("grey")}/>
                <Avatar color={"blue"} onClick={() => setAvatarColor("blue")}/>
                <Avatar color={"amber"} onClick={() => setAvatarColor("amber")}/>
                <Avatar color={"cyan"} onClick={() => setAvatarColor("cyan")}/>
                <Avatar color={"green"} onClick={() => setAvatarColor("green")}/>
                <Avatar color={"indigo"} onClick={() => setAvatarColor("indigo")}/>
                <Avatar color={"light-blue"} onClick={() => setAvatarColor("light-blue")}/>
                <Avatar color={"light-green"} onClick={() => setAvatarColor("light-green")}/>
                <Avatar color={"lime"} onClick={() => setAvatarColor("lime")}/>
                <Avatar color={"orange"} onClick={() => setAvatarColor("orange")}/>
                <Avatar color={"pink"} onClick={() => setAvatarColor("pink")}/>
                <Avatar color={"purple"} onClick={() => setAvatarColor("purple")}/>
                <Avatar color={"red"} onClick={() => setAvatarColor("red")}/>
                <Avatar color={"teal"} onClick={() => setAvatarColor("teal")}/>
                <Avatar color={"violet"} onClick={() => setAvatarColor("violet")}/>
                <Avatar color={"yellow"} onClick={() => setAvatarColor("yellow")}/>
                <br/>
                <text>choose {avatarColor} maybe good :)</text>
            </Modal>
        </div>
    );

};
export default AvatarUpdate;