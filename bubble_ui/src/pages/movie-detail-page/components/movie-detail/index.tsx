import React from 'react';
import {Typography} from '@douyinfe/semi-ui';
import {Layout} from '@douyinfe/semi-ui';
import MovieDetail from "./index";
import TagDetailPlane from "../tags-detail";

function movieDetailPanel(props: { movieName: string|undefined, movieQuote: string|undefined}) {
    const {Title, Text} = Typography;
    const {Content} = Layout;
    const ContentStyle = {
        padding: '24px',
        border: '1px solid var(--semi-color-border)',
        height: '100%',
        marginBottom: '24px'
    };

    return (
        <div>
            <Content className={"movie-info"} style={ContentStyle}>
                <Title heading={2}>{props.movieName}</Title>
                <br/>
                <Text size={'normal'}>{props.movieQuote}</Text>
            </Content>
        </div>
    );
}

export default movieDetailPanel