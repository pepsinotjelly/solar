import React, {useState} from "react";
import {Avatar, Modal, Upload} from "@douyinfe/semi-ui";
import {Input, Toast} from "@douyinfe/semi-ui";
import {IconCamera} from "@douyinfe/semi-icons";
import Text from "@douyinfe/semi-ui/lib/es/typography/text";
import {useNavigate} from "react-router-dom";
import {UserRegister} from "../../../model/user-info";
import {userBaseRegister} from "../../../services/userService";
import {JWT} from "../../../constants";


function AvatarUpload() {
    const navigate =useNavigate()
    const [inputUserAvatar, setInputUserAvatar] = useState('https://sf6-cdn-tos.douyinstatic.com/obj/eden-cn/ptlz_zlp/ljhwZthlaukjlkulzlp/root-web-sites/avatarDemo.jpeg');
    const handelUserAvatarChange = (val: React.SetStateAction<string>) => setInputUserAvatar(val)
    //  头像上传
    //  上传成功提示
    const onSuccess = () => {
        Toast.success('头像更新成功');
        setInputUserAvatar(inputUserAvatar);
        console.log(inputUserAvatar)
    };

    const hoverMask = (
        <div style={{
            backgroundColor: 'var(--semi-color-overlay-bg)',
            height: '100%',
            width: '100%',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            color: 'var(--semi-color-white)',
        }}>
            <IconCamera/>
        </div>);

    const upLoadApi = 'http://localhost:8080/user/update/avatar';
    //  设置上传约束
    let imageOnly = 'image/*';

    const onClose = () => {
        navigate("/login", {replace: true});
        console.log("onClose :: close the registerWindow!")
    };

    return (
        <div>
            <Text>ok</Text>
            <Modal
                title="用户注册"
                visible={true}
                maskClosable={false}
                onCancel={onClose}
                cancelText={"back"}
                onOk={onSuccess}
                okText={"register"}
                style={{height: "300px"}}
            >
                <Upload
                    className="avatar-upload"
                    action={upLoadApi}
                    onSuccess={onSuccess}
                    accept={imageOnly}
                    showUploadList={false}
                    onError={() => Toast.error('上传失败')}
                    headers={{"ContentType":"image/jpeg"}}
                >
                    <Avatar src={inputUserAvatar} style={{margin: 4}} hoverMask={hoverMask}/>
                </Upload>

                <br/><br/>
            </Modal>
        </div>
    );

};
export default AvatarUpload;