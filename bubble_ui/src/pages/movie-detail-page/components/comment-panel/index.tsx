import {Button, Layout, Rating, TagInput, Typography} from "@douyinfe/semi-ui";
import React, {useState} from "react";
import API from "../../../../api";

function Comment() {
    const {Text} = Typography;
    const {Sider, Content} = Layout;

    //  Rating 设置
    const [value, setValue] = useState(0);
    const desc = ['terrible', 'bad', 'normal', 'good', 'wonderful'];
    const change = (val: React.SetStateAction<number>) => setValue(val);
    const ratingStarInfoSiderStyle = {
        border: '1px solid var(--semi-color-border)',
        width: '60%',
        padding: '24px'
    }
    const submitButtonStyle = {
        justifyItems: 'center',
        marginTop: '45%',
        marginLeft: '20%'
    }
    const state = {
        name: ''
    }


    // Submit 回调
    const handleSubmit = async (
        e: React.MouseEvent<HTMLButtonElement, MouseEvent>
    ) => {
        e.preventDefault();
        await new Promise((r) => setTimeout(r, 5000));
        try {
            const resp = await API.post("/api/rate-movie", {});
        } catch (err) {
            console.log(err);
        }
    };


    return (
        <div>
            <Layout className={"rating-layout"}>
                <Sider className={'rating-star-info'} style={ratingStarInfoSiderStyle}>
                    <div>
                        <Text>
                            看过这部电影？分享一下你觉得这部电影怎么样吧
                        </Text>
                        <br/>
                        <Rating allowHalf tooltips={desc} onChange={change} value={value}/>
                        <br/><br/>
                        <Text>发表我的看法：</Text>
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
                    <Button className={'submit-button'}
                            style={submitButtonStyle}
                            onClick={handleSubmit}
                    >写好了</Button>
                </Content>
            </Layout>
        </div>
    );

};
export default Comment