import React from 'react';
import {Typography} from '@douyinfe/semi-ui';
import {Layout} from '@douyinfe/semi-ui';
import MovieDetail from "./index";

function movieDetail(props:{movieName:string,movieQuote:string}){
    const {Title, Text} = Typography;
    const { Content} = Layout;

    return (
        <div>
            <Content className={"movie-info"} style={{
                padding: '24px',
                border: '1px solid var(--semi-color-border)',
                height: '250px',
                marginBottom: '24px'
            }}>
                <Title heading={2}>{props.movieName}</Title>
                <br/><br/>
                <Text size={'normal'}>
                    {props.movieQuote}
                </Text>
            </Content>
        </div>
    );
}
export default movieDetail