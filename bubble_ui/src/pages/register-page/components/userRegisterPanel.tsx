import React, {useState} from "react";
import {Input, Modal, Toast} from "@douyinfe/semi-ui";
import {useNavigate} from "react-router-dom";
import {UserRegister} from "../../../model/user-info";
import {userBaseRegister, userLogin} from "../../../services/userService";
import {JWT} from "../../../constants";

function UserRegisterPanel() {
    const navigate = useNavigate()

    const [inputUserName, setInputUserName] = useState<string>("")
    const handelUserNameChange = (val: React.SetStateAction<string>) => setInputUserName(val);
    const [inputUserPwd, setInputUserPwd] = useState<string>("")
    const handelUserPwdChange = (val: React.SetStateAction<string>) => setInputUserPwd(val);
    const [inputUserEmail, setInputUserEmail] = useState<string>("")
    const handelUserEmailChange = (val: React.SetStateAction<string>) => setInputUserEmail(val);

    const onClose = () => {
        navigate("/login", {replace: true});
        console.log("onClose :: close the registerWindow!")
    };
    const handleLogin = async (
        e: React.MouseEvent<Element, MouseEvent>
    ) => {
        e.preventDefault();
        //  设置超时时间
        await new Promise((r) => setTimeout(r, 1000));
        //  数据处理
        try {
            //  发送请求
            const resp = await userLogin(inputUserName, inputUserPwd);
            if (resp.status === 200) {
                console.log("/user/login :: response.token :: ", resp.data.data)
                localStorage.setItem(JWT, resp.data.data)
                Toast.success(`已自动帮你登陆！`)
                await new Promise((r) => setTimeout(r, 100));
                navigate("/avatar-update", {replace: true});
            }
        } catch (err) {
            Toast.error("登陆失败!")
        }
    };
    const handleRegister = async (
        e: React.MouseEvent<Element, MouseEvent>
    ) => {
        e.preventDefault();
        await new Promise((r) => setTimeout(r, 1000));
        try {
            const userRegister: UserRegister = {
                userAvatar: "grey",
                userName: inputUserName,
                userEmail: inputUserEmail,
                userPwd: inputUserPwd
            }
            const resp = await userBaseRegister(userRegister);
            console.log(resp)
            if (resp.data.status === 200) {
                Toast.success(`正在帮你登陆!`)
                await handleLogin(e);
            }else{
                Toast.info(resp.data.msg)
            }
        } catch (err) {
            Toast.error("注册失败!")
        }
        setInputUserName("")
        setInputUserPwd("")
    };

    return (
        <div>
            <Modal
                title="用户注册"
                visible={true}
                maskClosable={false}
                onCancel={onClose}
                cancelText={"back"}
                onOk={handleRegister}
                okText={"register"}
                style={{height: "300px"}}
            >
                <Input className={"email-input-box"}
                       prefix={"please enter your email:"}
                       showClear
                       value={inputUserEmail}
                       onChange={handelUserEmailChange}
                />
                <br/><br/>
                <Input className={"nick-name-input-box"}
                       prefix={"enter your nick name:"}
                       showClear
                       value={inputUserName}
                       onChange={handelUserNameChange}
                />
                <br/><br/>
                <Input className={"pwd-input-box"}
                       mode="password"
                       prefix={"and your password:"}
                       showClear
                       value={inputUserPwd}
                       onChange={handelUserPwdChange}/>
            </Modal>
        </div>
    );

};

export default UserRegisterPanel;