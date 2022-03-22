import React from 'react';
import {Tag, TagGroup} from '@douyinfe/semi-ui';
import {Link} from "react-router-dom";
import {TagDetail} from "../../../../model/tag-detail";

function TagDetailPlane(props: { tagList: any[]}) {
    const src = 'https://sf6-cdn-tos.douyinstatic.com/obj/eden-cn/ptlz_zlp/ljhwZthlaukjlkulzlp/root-web-sites/avatarDemo.jpeg';
    const divStyle = {
        // backgroundColor: 'var(--semi-color-fill-0)',//  一个颜色才好看！
        height: 35,
        width: 300,
        display: 'flex',
        alignItems: 'center',
        padding: '0 10px',
        marginBottom: 30,
    };
    const tagGroupStyle = {
        display: 'flex',
        alignItems: 'center',
        width: 350
    };

    return (
        <>
            <div style={divStyle}>
                  <TagGroup
                    maxTagCount={13}
                    style={tagGroupStyle}
                    tagList={props.tagList}
                    size='large'
                />
            </div>
        </>
    );

};


export default TagDetailPlane
