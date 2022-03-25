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
    // const mockDataMovie = [{
    //     movieId: 11362,
    //     movieName: '新基督山伯爵',
    //     imgUrl: 'https://image.tmdb.org/t/p/w300_and_h450_bestv2/aoKzR82lgcle1NmlayZ9wURhwFa.jpg',
    //     movieQuote: '我想评论些什么',
    // },
    //     {
    //         movieId: 26957,
    //         movieName: '魔偶奇谭4：邪神军团',
    //         imgUrl: 'https://image.tmdb.org/t/p/w300_and_h450_bestv2/fRIpNpZVL73MGJ7E2ZYt03tggeX.jpg',
    //         movieQuote: '我想评论些什么'
    //
    //     },
    //     {
    //         movieId: 281338,
    //         movieName: '猩球崛起3：终极之战',
    //         imgUrl: 'https://image.tmdb.org/t/p/w300_and_h450_bestv2/cu0bxkxDeqwjCowG5jsH4lpopSM.jpg',
    //         movieQuote: '我想评论些什么'
    //
    //     },
    //     {
    //         movieId: 38582,
    //         movieName: '哥斯拉对机械哥斯拉',
    //         imgUrl: 'https://image.tmdb.org/t/p/w300_and_h450_bestv2/mdeEGozorRqHSLs0kTjVCyNH3Va.jpg',
    //         movieQuote: '我想评论些什么'
    //
    //     }]
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