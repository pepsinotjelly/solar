import React, {useEffect, useState} from 'react';
import {Link, useParams} from "react-router-dom";
import {Typography, Layout} from '@douyinfe/semi-ui';
import MovieImgCard from "../movie-detail-page/components/movie-img";
import {getTagDetailByTagId} from "../../services/tagDetailService";
import {getMovieDetailByTagId} from "../../services/movieDetailService";
import TagDetail from "../../model/tag-detail";
import {MovieDetail} from "../../model/movie-detail";
import MovieDetailPanel from '../movie-detail-page/components/movie-detail';

function TagMoviePage() {
    const {Title, Text} = Typography;
    const {Header, Footer, Content, Sider} = Layout;
    const mockDataTag = {tagId: 1, tagColor: 'green', tagName: 'Thrill'};
    const backGroundImg = '/resources/icecold.jpg';
    const [movieDetailData, setMovieDetailData] = useState<MovieDetail[]>([])
    const [tagDetailData, setTagDetailData] = useState<TagDetail>()
    const getMovieDetail = () => {
        getMovieDetailByTagId(params.id)
            .then((response: any) => {
                setMovieDetailData(response.data);
                console.log(response.data)
            }).catch((e: Error) => {
            console.log(e);
        })
    }
    const getTagDetail = () => {
        getTagDetailByTagId(params.id)
            .then((response: any) => {
                setTagDetailData(response.data);
                console.log(response.data)
            }).catch((e: Error) => {
            console.log(e);
        })
    }
    useEffect(() => {
        getMovieDetail()
    }, [])
    useEffect(()=>{
        getTagDetail()
    },[])

    let params = useParams()
    return (
        <div>
            <Layout style={{
                // border: '1px solid var(--semi-color-border)'
            }}>
                <Content
                    style={{backgroundImage: `url(${tagDetailData?.tagImgUrl})`, height: '500px', width: '90%', marginLeft: '5%'}}>
                    <Title style={{
                        marginTop: '200px',
                        textAlign: 'center',
                        color: 'whitesmoke'
                    }}>
                        {tagDetailData?.tagName}
                        {/*===-TagId：{params.id}*/}
                    </Title>
                </Content>
                <Content style={{
                    width: '70%',
                    justifyContent: 'center',
                    // border: '1px solid var(--semi-color-border)',
                    marginLeft: '15%',
                    marginTop: '62px'
                }}>
                    {
                        movieDetailData.map((movie, idx) => (
                            <Link to={'/comment/' + movie.movieId} style={{textDecoration: 'none'}}>
                                <Layout>
                                    <Sider style={{height: '300px',
                                        // border: '1px solid var(--semi-color-border)',
                                    }}>
                                        <MovieImgCard imgUrl={movie.imgUrl} cardWidth={180} cardHeight={250}
                                                      movieName={''}/>
                                    </Sider>
                                    <Content style={{
                                        marginLeft: '3%',
                                        width: '80%',
                                        // border: '1px solid var(--semi-color-border)',
                                    }}>
                                        <MovieDetailPanel movieName={movie.movieName}
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