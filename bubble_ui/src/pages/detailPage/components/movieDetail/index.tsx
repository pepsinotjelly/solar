import React from 'react';
import {Typography} from '@douyinfe/semi-ui';
import {Layout} from '@douyinfe/semi-ui';

const movieDetail = (movieName:any,movieQuote:any) => {
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
                <Title heading={2}>{movieName}</Title>
                <br/><br/>
                <Text size={'normal'}>
                    {movieQuote}
                </Text>
            </Content>
        </div>
    );
}
export default movieDetail