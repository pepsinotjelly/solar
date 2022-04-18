import {Avatar, Space} from "@douyinfe/semi-ui";
import React from "react";
import {Link} from "react-router-dom";

import TagDetail from "../../../model/tag-detail";

function AvatarListPanel(props: { AvatarDataList: TagDetail[] }) {
    return (
        <div>
            <Space>
                {
                    props.AvatarDataList.map((item, idx) => (
                        <Link to={"/tag-movie/" + item.tagId ?? 1}
                              style={{
                                  textDecoration: "none",
                                  // border: '1px solid var(--semi-color-border)',
                                   marginLeft:'20px',
                              }}
                        >
                            <Avatar color={item.tagColor}
                                    style={{
                                        width: (item.tagName.length * 13).toString() + 'px',
                                        height: (item.tagName.length * 13).toString() + 'px',
                                        // marginTop: (item.tagName.length * 0.01).toString() + 'px',
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