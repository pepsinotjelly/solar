import React from 'react';
import {List, Space, Tag, TagGroup} from '@douyinfe/semi-ui';
import {Link} from "react-router-dom";
import TagDetail from "../../../../model/tag-detail";

function TagDetailPlane(props: { tagList: TagDetail[], tagFrontSize: string | undefined, tagBackgroundSize: string | undefined }) {
    //  配置tag基本属性
    const divStyle = {
        height: 22,
        width: 300,
        display: 'flex',
        alignItems: 'center',
        padding: '0 10px',
        marginBottom: 30
    };
    //  记录参数历史
    console.log(props.tagList)
    return (
        <>
            <div style={divStyle}>
                <Space>{
                    props.tagList?.map((item, idx) => (
                        <Link to={"/tag-movie/" + item.tagId ?? 1}>
                            <Tag color={item.tagColor ?? 'blue'}
                                 style={{
                                     fontSize: props.tagFrontSize,
                                     backgroundSize: props.tagBackgroundSize
                                 }}
                                 type='light'> {item.tagName ?? 'name空了'} </Tag>
                        </Link>
                    ))
                }
                </Space>
            </div>
        </>
    );
};


export default TagDetailPlane
