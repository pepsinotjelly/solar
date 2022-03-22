import { IconArrowRight } from "@douyinfe/semi-icons";
import { Avatar, Card, Dropdown } from "@douyinfe/semi-ui";
const UserInfo = () => {
    return (
        <Dropdown
            trigger={"click"}
            position={"bottomLeft"}
            render={
                <Dropdown.Menu>
                    <Dropdown.Item
                        icon={<IconArrowRight/>}
                        onClick={() => {
                            window.location.href = process.env.REACT_APP_LOGIN_API!;
                        }}
                    >
                        立即登录
                    </Dropdown.Item>
                </Dropdown.Menu>
            }
        >
            <Avatar size="small">未登录</Avatar>
        </Dropdown>
    );
};
export default UserInfo