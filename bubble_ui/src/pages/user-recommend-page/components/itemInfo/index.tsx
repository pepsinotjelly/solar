import React, {useState} from 'react';
import {Card, CardGroup, Typography, Slider, Pagination, Progress, Layout, List} from '@douyinfe/semi-ui';
import {IconLink} from "@douyinfe/semi-icons";
import {Link, useParams} from "react-router-dom";
import axios from "axios";
import {MovieDetailData} from "../../../../model/movie-detail";
import movieImg from "../../../movie-detail-page/components/movie-img";


function ItemInfo(props:{movieList: any[]}) {
    // const abcd= useParams()
    const {Content} = Layout;
    //  Pagination 页面的设置
    const mockPage = 50;
    const [page, setPage] = useState(1)
    function onPageChange(currentPage: React.SetStateAction<number>) {
        setPage(currentPage);
    }
    return (
        <>
            <Layout style={{
                marginLeft: '4.5%',
                marginBottom: '1px',
                width: '90%',
                height: '100%'
            }}>
                <Content style={{
                }}>
                    <CardGroup spacing={33}
                               style={{justifyContent: 'center'}}>
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
                                        <img
                                            alt={'ImgUrl'}
                                            src={item.imgUrl}
                                        />
                                    }
                                    headerExtraContent={
                                        <Link to={"/comment/"+item.movieId}>
                                            <IconLink></IconLink>
                                        </Link>
                                    }
                                />
                            ))
                        }
                    </CardGroup>
                    <Content>
                        <br/><br/>
                        <Pagination
                            style={{
                                marginBottom: 4,
                                justifyContent: 'center'
                            }}
                            total={mockPage}
                            currentPage={page}
                            onPageChange={onPageChange}
                        />
                    </Content>
                </Content>
            </Layout>
        </>
    );
}

export default ItemInfo;