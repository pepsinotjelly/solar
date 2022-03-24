import React from 'react';
import {List, Space, Tag, TagGroup} from '@douyinfe/semi-ui';
import {Link} from "react-router-dom";
import TagDetail from "../../../../model/tag-detail";

function TagDetailPlane(props: { tagList: TagDetail[] |any[], tagFrontSize: string | undefined, tagBackgroundSize: string | undefined }) {
    const src = 'https://sf6-cdn-tos.douyinstatic.com/obj/eden-cn/ptlz_zlp/ljhwZthlaukjlkulzlp/root-web-sites/avatarDemo.jpeg';
    const divStyle = {
        height: 22,
        width: 300,
        display: 'flex',
        alignItems: 'center',
        padding: '0 10px',
        marginBottom: 30
    };
    return (
        <>
            <div style={divStyle}>
                <Space>{
                    props.tagList.map((v, idx) => (
                        <Link to={"/tag-movie/" + props.tagList.at(idx).tagId}>
                            <Tag color={props.tagList.at(idx).color} style={{
                                fontSize: props.tagFrontSize,
                                backgroundSize: props.tagBackgroundSize,
                            }}
                                 type='light'> {props.tagList.at(idx).children} </Tag>
                        </Link>
                    ))
                }
                </Space>

            </div>
        </>
    );

};


export default TagDetailPlane
