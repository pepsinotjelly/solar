import {Avatar, Space} from "@douyinfe/semi-ui";
import React from "react";
import {Link} from "react-router-dom";

import TagDetail from "../../../model/tag-detail";

function AvatarListPanel(props: { AvatarDataList: TagDetail[] }) {
    const sizeList :number[]= [1263,881,904,1656,979,1496,1861,1428,1199,1494,1078,873,1080,782,734,790,658,667,587,534];
    return (
        <div>
            <Space>
                {
                    props.AvatarDataList.map((item, idx) => (
                        <Link to={"/tag-movie/" + item.tagId ?? 1}
                              style={{
                                  textDecoration: "none",
                                  // border: '1px solid var(--semi-color-border)',
                                   marginLeft:'10px',
                              }}
                        >
                            <Avatar color={item.tagColor}
                                    style={{
                                        width:((sizeList.at(item.tagId)??1)*0.1).toString()+'px',
                                        height:((sizeList.at(item.tagId)??1)*0.1).toString()+'px',
                                        fontSize:((sizeList.at(item.tagId)??1)*0.02).toString()+'px',
                                        margin: 3
                                    }}> {item.tagName}</Avatar>
                        </Link>
                    ))
                }
            </Space>
        </div>
    );
}

export default AvatarListPanel;