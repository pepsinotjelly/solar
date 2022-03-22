import {Button, Layout, Rating, TagInput, Typography} from "@douyinfe/semi-ui";
import React, {useState} from "react";
import API from "../../../../api";

const Comment = () => {
    const {Title, Text} = Typography;
    const {Header, Footer, Sider, Content} = Layout;
    //  Rating 设置
    const [value, setValue] = useState(0);
    const desc = ['terrible', 'bad', 'normal', 'good', 'wonderful'];
    const change = (val: React.SetStateAction<number>) => setValue(val);
    // Submit 回调
    const handleSubmit = async (
        e: React.MouseEvent<HTMLButtonElement, MouseEvent>
    ) => {
        e.preventDefault();
        await new Promise((r) => setTimeout(r, 1000));
        try {
            const resp = await API.post("/api/rate-movie", {

            });
        } catch (err) {
            console.log(err);
        }
    };


    return (
        <div>
            <Layout className={"rating-layout"} style={{
                border: '1px solid var(--semi-color-border)',
            }}>
                <Sider className={'rating-star-info'} style={{
                    border: '1px solid var(--semi-color-border)',
                    width: '60%',
                    padding: '24px'
                }}>
                    <div>
                        <Text>
                            看过这部电影？分享一下你觉得这部电影怎么样吧: {value ? <span>{desc[Math.floor(value)]}</span> : ''}
                        </Text>
                        <br/>
                        <Rating allowHalf tooltips={desc} onChange={change} value={value}/>
                        <br/><br/>
                        <Text>
                            发表我的看法：
                        </Text>
                        <br/><br/>
                        <TagInput
                            style={{height: 80, width: 480}}
                            size={'small'}
                            placeholder='那么我认为的是...'
                            onChange={v => console.log(v)}
                        />
                    </div>
                </Sider>
                <Content className={'button-content'} style={{padding: '24px'}}>
                    <Button style={{
                        justifyItems: 'center',
                        marginTop: '45%',
                        marginLeft: '20%'
                    }}>写好了</Button>
                </Content>
            </Layout>
        </div>
    );

};
export default Comment