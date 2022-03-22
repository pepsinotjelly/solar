import React from "react";
import {Button, Layout} from "@douyinfe/semi-ui";
import {Typography} from '@douyinfe/semi-ui';
import {Link} from "react-router-dom";
//
// const homebackground='https://image.tmdb.org/t/p/w1920_and_h600_multi_faces_filter(duotone,032541,01b4e4)/5GISMqlRXMEyBrgEqyvhaMMuQmJ.jpg'
// const homeImg = {
//     backgroundSize: '100% 100%', //记得这里100%
//     //或者下面这种也行
//     backgroundImage: 'url(' + homebackground + ')'
// }
const Welcome = () => {
    const {Content, Sider} = Layout;
    const {Title, Text} = Typography;

    return (
        <div>
            <Layout style={{
                padding:'24px',
                border: '4px solid var(--semi-color-border)',
                height: '590px',
                width: '100%',
                backgroundSize: '100% 100%',
                backgroundImage: 'url(https://image.tmdb.org/t/p/w1920_and_h600_multi_faces_filter(duotone,032541,01b4e4)/5GISMqlRXMEyBrgEqyvhaMMuQmJ.jpg)'
            }}>
                <Content style={{justifyContent:"center",textAlign:'center'}}>
                    <br/><br/><br/>
                    <Title style={{
                        textAlign:'center'
                    }}> 欢迎 </Title>
                    <br/><br/>
                    <Title style={{
                        textAlign:'center'
                    }}>这里有海量电影等你选择</Title>
                    <br/><br/><br/><br/>
                    <Link to={"/recommend"}>
                        <Button size={'large'}

                        >去看看</Button>

                    </Link>

                </Content>
            </Layout>

        </div>
    );
};

export default Welcome