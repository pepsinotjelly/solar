import React, {useState} from 'react';
import {Card, CardGroup, Typography, Slider, Pagination, Progress, Layout, List} from '@douyinfe/semi-ui';
import {IconLink} from "@douyinfe/semi-icons";
import {Link, useParams} from "react-router-dom";


function ItemInfo(props: { movieList: any[] }) {
    const {Content} = Layout;
    return (
        <>
            <Layout style={{marginLeft: '4.5%', marginBottom: '1px', width: '90%', height: '100%'}}>
                <Content>
                    <CardGroup spacing={33} style={{justifyContent: 'center'}}>
                        {
                            props.movieList.map((item, idx) => (
                                <Card
                                    key={idx}
                                    shadows='hover'
                                    title={item.movieName}
                                    headerLine={false}
                                    style={{
                                        width: "20%",
                                        height: 440
                                    }}
                                    cover={
                                        <img alt={'ImgUrl'} src={item.imgUrl}/>
                                    }
                                    headerExtraContent={
                                        <Link to={"/comment/" + item.movieId}>
                                            <IconLink></IconLink>
                                        </Link>
                                    }
                                />
                            ))
                        }
                    </CardGroup>
                </Content>
            </Layout>
        </>
    );
}

export default ItemInfo;