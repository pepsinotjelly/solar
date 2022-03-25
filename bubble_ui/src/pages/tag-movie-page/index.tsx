import React, {useEffect, useState} from 'react';
import {Link, useParams} from "react-router-dom";
import {Typography, Layout, Progress} from '@douyinfe/semi-ui';
import MovieImgCard from "../movie-detail-page/components/movie-img";
import {getTagDetailByMovieId} from "../../services/tagDetailService";
import MovieDetail from "../movie-detail-page/components/movie-detail";
import {getMovieDetailByTagId} from "../../services/movieDetailService";
import {types} from "util";

function TagMoviePage() {
    const {Title, Text} = Typography;
    const {Header, Footer, Content, Sider} = Layout;
    const mockDataTag = {tagId: 1, color: 'green', children: 'Thrill'};
    const backGroundImg = '/resources/icecold.jpg';
    const [movieDetailData, setMovieDetailData] = useState<any[]>([])
    const getMovieDetail = () => {
        getMovieDetailByTagId(params.id)
            .then((response: any) => {
                console.log(response.data)
                setMovieDetailData(response.data);
                console.log(response.data)
            }).catch((e: Error) => {
            console.log(e);
        })
    }
    useEffect(() => {
        getMovieDetail()
    }, [])

    let params = useParams()
    return (
        <div>
            <Layout style={{border: '1px solid var(--semi-color-border)'}}>
                <Content
                    style={{backgroundImage: `url(${backGroundImg})`, height: '300px', width: '90%', marginLeft: '5%'}}>
                    <Title style={{
                        marginTop: '70px',
                        textAlign: 'center',
                        color: 'whitesmoke'
                    }}>{mockDataTag.children}===-TagId：{params.id}</Title>
                </Content>
                <Content style={{
                    width: '70%',
                    justifyContent: 'center',
                    border: '1px solid var(--semi-color-border)',
                    marginLeft: '15%',
                    marginTop: '62px'
                }}>
                    {
                        movieDetailData.map((movie, idx) => (
                            <Link to={'/comment/' + movie.movieId} style={{textDecoration: 'none'}}>
                                <Layout>
                                    <Sider style={{height: '300px', border: '1px solid var(--semi-color-border)',}}>
                                        <MovieImgCard imgUrl={movie.imgUrl} cardWidth={180} cardHeight={250}
                                                      movieName={''}/>
                                    </Sider>
                                    <Content style={{
                                        marginLeft: '3%',
                                        width: '80%',
                                        border: '1px solid var(--semi-color-border)',
                                    }}>
                                        <MovieDetail movieName={movie.movieName}
                                                     movieQuote={movie.movieQuote}/>
                                    </Content>
                                </Layout>
                            </Link>
                        ))
                    }
                </Content>
            </Layout>

        </div>
    );
};
export default TagMoviePage