import React from 'react';
import {Typography} from '@douyinfe/semi-ui';
import {Layout} from '@douyinfe/semi-ui';
import MovieDetail from "./index";
import TagDetailPlane from "../tags-detail";
import {TagDetail} from "../../../../model/tag-detail";

function movieDetail(props: { movieName: string, movieQuote: string,movieTag:any[] }) {
    const {Title, Text} = Typography;
    const {Content} = Layout;
    const ContentStyle = {
        padding: '24px',
        border: '1px solid var(--semi-color-border)',
        height: '250px',
        marginBottom: '24px'
    };

    return (
        <div>
            <Content className={"movie-info"} style={ContentStyle}>
                <Title heading={2}>{props.movieName}</Title>
                <br/><br/>
                <Text size={'normal'}>{props.movieQuote}</Text>
                <br/><br/>
                <TagDetailPlane tagList={props.movieTag}/>
            </Content>
        </div>
    );
}

export default movieDetail