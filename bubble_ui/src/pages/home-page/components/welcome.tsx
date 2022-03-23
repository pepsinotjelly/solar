import React from "react";
import {Button, Layout} from "@douyinfe/semi-ui";
import {Typography} from '@douyinfe/semi-ui';
import {Link} from "react-router-dom";

function Welcome (){
    const {Content, Sider} = Layout;
    const {Title, Text} = Typography;
    const backGroundImg = '/resources/icecold.jpg';

    return (
        <div>
            <Layout style={{
                padding:'24px',
                border: '4px solid var(--semi-color-border)',
                height: '590px',
                width: '100%',
                backgroundSize: '100% 100%',

            }}>
                <Content style={{justifyContent:"center",
                    textAlign:'center',
                    backgroundColor:'darkblue',
                    backgroundImage: `url('${backGroundImg}')`}}>
                    <br/><br/><br/><br/><br/><br/>
                    <Title style={{
                        textAlign:'center',
                        color:'whitesmoke',
                        fontSize:'50px'
                    }} > 欢迎</Title>
                    <br/>
                    <Title style={{
                        textAlign:'center',
                        color:'whitesmoke'
                    }}>这里有海量的电影、剧集和人物等你来发现。快来探索吧！</Title>
                    <br/><br/><br/><br/><br/>
                    <Link to={"/recommend"}>
                        <Button size={'large'} style={{height:'10%' ,width:'20%',
                            color:'whitesmoke',backgroundColor:'darkslateblue' ,opacity:'55%',
                            fontSize:'15px'
                        }}>去看看</Button>
                    </Link>

                </Content>
            </Layout>

        </div>
    );
};

export default Welcome