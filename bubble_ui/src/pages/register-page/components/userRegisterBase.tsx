import React, {useState} from "react";
import {Input, Modal, Toast} from "@douyinfe/semi-ui";
import {useNavigate} from "react-router-dom";
import {UserRegister} from "../../../model/user-info";
import {userBaseRegister} from "../../../services/userService";
import {JWT} from "../../../constants";

function UserRegisterBase() {
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

    const handleRegister = async (
        e: React.MouseEvent<Element, MouseEvent>
    ) => {
        e.preventDefault();
        await new Promise((r) => setTimeout(r, 5000));
        try {
            const userRegister: UserRegister = {
                userAvatar: "",
                userName: inputUserName,
                userEmail: inputUserEmail,
                userPwd: inputUserPwd
            }
            const resp = await userBaseRegister(userRegister);
            if (resp.status === 200) {
                if (resp.data.baseCode === 0) {
                    console.log("/user/register :: response.token :: ", resp.data.token)
                    localStorage.setItem(JWT, resp.data.token)
                    Toast.success(`Hi Welcome!`)
                } else {
                    Toast.error(resp.data.baseMsg)
                }
                // await new Promise((r) => setTimeout(r, 100));
                // navigate("/", {replace: true});
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

export default UserRegisterBase;