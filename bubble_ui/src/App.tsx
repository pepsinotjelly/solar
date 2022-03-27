import React from 'react';
import {useEffect} from "react";
import './App.css';
import {Outlet} from "react-router-dom";
import {useNavigate} from "react-router-dom";
import {Layout, Nav, Button, Avatar, Pagination} from '@douyinfe/semi-ui';
import {
    IconSemiLogo,
    IconBell,
    IconHelpCircle,
    IconBytedanceLogo,
    IconHome,
    IconLive,
    IconSetting,
    IconCloud
} from '@douyinfe/semi-icons';
import {Typography} from '@douyinfe/semi-ui';
import Home from "./pages/home-page";
import UserInfoPanel from "./components/userInfo";


function App() {
    const {Header, Footer, Content} = Layout;
    const {Title, Text} = Typography;
    const getUser = () => {

    }
    const navigate = useNavigate();

    useEffect(() => {
        getUser();
    }, []);
    return (
        <Layout style={{
            border: '1px solid var(--semi-color-border)',
            height: '100%'
        }}>
            <Header style={{backgroundColor: 'var(--semi-color-bg-1)'}}>
                <div>
                    <Nav
                        mode="horizontal"
                        defaultSelectedKeys={['Home']}
                        onSelect={data => console.log('trigger onSelect: ', data)}
                        onClick={data => console.log('trigger onClick: ', data)}
                    >
                        <Nav.Header>
                            <IconSemiLogo style={{fontSize: 36}}/>
                        </Nav.Header>
                        <Nav.Item
                            itemKey="Home"
                            text="首页"
                            icon={<IconHome size="large"/>}
                            link={"/"}
                        />
                        <Nav.Item
                            itemKey="Movie"
                            text="推荐"
                            icon={<IconLive size="large"/>}
                            link={"/recommend"}
                        />
                        <Nav.Item
                            itemKey="Share"
                            text="更多"
                            icon={<IconCloud size="large"/>}
                            link={"/tagall"}
                        />
                        <Nav.Footer>
                            <Button
                                theme="borderless"
                                icon={<IconBell size="large"/>}
                                style={{
                                    color: 'var(--semi-color-text-2)',
                                    marginRight: '12px',
                                }}
                            />
                            <Button
                                theme="borderless"
                                icon={<IconHelpCircle size="large"/>}
                                style={{
                                    color: 'var(--semi-color-text-2)',
                                    marginRight: '12px',
                                }}
                            />
                            <UserInfoPanel/>
                        </Nav.Footer>
                    </Nav>
                </div>
            </Header>
            <Content
                style={{
                    backgroundColor: 'var(--semi-color-bg-0)',
                    height: '90%',
                    marginBottom: '0.7%'
                }}
            >
                <div
                    style={{
                        borderRadius: '10px',
                        border: '1px solid var(--semi-color-border)',
                        height: '100%',
                        padding: '24px',
                    }}
                >
                    <Outlet/>
                </div>

            </Content>
            <Footer
                style={{
                    display: 'flex',
                    justifyContent: 'space-between',
                    padding: '20px',
                    color: 'var(--semi-color-text-2)',
                    backgroundColor: 'rgba(var(--semi-grey-0), 1)',
                    marginBottom: '0'
                }}
            >
                <span
                    style={{
                        display: 'flex',
                        alignItems: 'center',
                    }}
                >
                    <IconBytedanceLogo size="large" style={{marginRight: '8px'}}/>
                    <span>Bubble was designed by SoniaSuen 2022. Let's blow a Bubble! All Rights Reserved. </span>
                </span>
                <span>
                    <span style={{marginRight: '24px'}}>平台客服</span>
                    <span>反馈建议</span>
                </span>
            </Footer>
        </Layout>
    );
}

export default App;
