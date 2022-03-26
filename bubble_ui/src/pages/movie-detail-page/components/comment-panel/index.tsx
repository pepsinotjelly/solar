import {Button, Layout, Rating, TagInput, TextArea, Typography} from "@douyinfe/semi-ui";
import React, {useState} from "react";
import {Link, useParams} from "react-router-dom";
import {Toast, Empty} from '@douyinfe/semi-ui';
import {Spin} from '@douyinfe/semi-ui';
import {submitRatingRecord} from "../../../../services/ratingRecordService";
import RatingRecord from "../../../../model/rating-record";
import {IllustrationSuccess, IllustrationSuccessDark} from '@douyinfe/semi-illustrations';


function Comment() {
    let params = useParams()
    const {Text} = Typography;
    const {Sider, Content} = Layout;
    // loading设置
    const [loading, setLoading] = useState<boolean>(false)
    // 提交的设置
    const [submitDone, setSubmitDone] = useState<boolean>(false)
    //  Rating 设置
    const [ratingValue, setRatingValue] = useState(0);
    const desc = ['terrible', 'bad', 'normal', 'good', 'wonderful'];
    const ratingChange = (val: React.SetStateAction<number>) => setRatingValue(val);
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
    // 评价设置
    const [commentValue, setCommentValue] = useState<string>()
    console.log(commentValue)
    const commentChange = (val: React.SetStateAction<string | undefined>) => setCommentValue(val);

    // Submit 回调
    const handleSubmit = async (
        e: React.MouseEvent<HTMLButtonElement, MouseEvent>
    ) => {
        e.preventDefault();
        await new Promise((r) => setTimeout(r, 5000));

        try {
            setLoading(true)
            const commentDetailValue: RatingRecord = {
                movieId: Number.parseInt(params.id as string),
                userId: 1,
                rating: ratingValue as unknown as string,
                comment: commentValue as unknown as string
            }

            console.log(commentDetailValue);
            const resp = await submitRatingRecord(commentDetailValue);
            setSubmitDone(true)
            setLoading(false)
            if (resp.status === 200) {
                Toast.success("提交成功啦！")
                setRatingValue(0);
                setCommentValue("");
            }
        } catch (err) {
            setLoading(false)
            console.log(err);
            Toast.error("哎呀！提交失败！")
        }
    };
    if (submitDone) {
        return (
            <div>
                <Empty
                    title={'评论成功'}
                    image={<IllustrationSuccess style={{width: 150, height: 150}}/>}
                    darkModeImage={<IllustrationSuccessDark style={{width: 150, height: 150}}/>}
                    layout="horizontal"
                    description="感谢你提供的宝贵意见！"
                    style={{width: 800, margin: '0 auto',marginTop:'10%',marginLeft:'10%'}}
                >
                    <Button type="primary" theme="solid" style={{padding: '6px 24px'}}
                            onClick={() => setSubmitDone(false)}>
                        再次评价
                    </Button>
                </Empty>
            </div>
        )
    } else {
        return (
            <div className={"sping-wrapper"}>
                <Layout className={"rating-layout"}>
                    <Sider className={'rating-star-info'} style={ratingStarInfoSiderStyle}>
                        <div>
                            <Text>
                                看过这部电影？分享一下你觉得这部电影怎么样吧
                            </Text>
                            <br/>
                            <Rating allowHalf tooltips={desc} onChange={ratingChange} value={ratingValue}/>
                            <br/><br/>
                            <Text>发表我的看法：</Text>
                            <br/><br/>
                            {/* eslint-disable-next-line react/jsx-no-undef */}
                            <TextArea
                                style={{height: 80, width: 480}}
                                maxCount={200}
                                placeholder='那么我认为的是...'
                                value={commentValue}
                                onChange={commentChange}
                            />
                        </div>
                    </Sider>
                    <Content className={'button-content'} style={{padding: '24px'}}>
                        <Button className={'submit-button'}
                                style={submitButtonStyle}
                                onClick={handleSubmit}
                        >写好了
                        <Spin wrapperClassName={"\"sping-wrapper\""}/>
                        </Button>
                    </Content>
                </Layout>
            </div>
        );
    }
};
export default Comment