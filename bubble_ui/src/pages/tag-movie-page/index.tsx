import React, {useState} from 'react';
import {Link, useParams} from "react-router-dom";
import {Typography, Layout} from '@douyinfe/semi-ui';
import MovieImgCard from "../movie-detail-page/components/movie-img";
import MovieDetail from "../movie-detail-page/components/movie-detail";

function TagMoviePage() {
    const {Title, Text} = Typography;
    const {Header, Footer, Content, Sider} = Layout;
    const mockDataTag = {tagId: 1, color: 'green', children: '欧美犯罪'};
    const backGroundImg = '../resources/img_0_0.jpg'
    const mockDataMovie = [{
        movieId: 11362,
        movieName: '新基督山伯爵',
        imgUrl: 'https://image.tmdb.org/t/p/w300_and_h450_bestv2/aoKzR82lgcle1NmlayZ9wURhwFa.jpg',
        movieQuote: '我想评论些什么'
    },
        {
            movieId: 26957,
            movieName: '魔偶奇谭4：邪神军团',
            imgUrl: 'https://image.tmdb.org/t/p/w300_and_h450_bestv2/fRIpNpZVL73MGJ7E2ZYt03tggeX.jpg',
            movieQuote: '我想评论些什么'

        },
        {
            movieId: 281338,
            movieName: '猩球崛起3：终极之战',
            imgUrl: 'https://image.tmdb.org/t/p/w300_and_h450_bestv2/cu0bxkxDeqwjCowG5jsH4lpopSM.jpg',
            movieQuote: '我想评论些什么'

        },
        {
            movieId: 38582,
            movieName: '哥斯拉对机械哥斯拉',
            imgUrl: 'https://image.tmdb.org/t/p/w300_and_h450_bestv2/mdeEGozorRqHSLs0kTjVCyNH3Va.jpg',
            movieQuote: '我想评论些什么'

        }]
    let params = useParams()
    return (
        <div>
            <Layout style={{border: '1px solid var(--semi-color-border)'}}>
                <Content style={{ border: '1px solid var(--semi-color-border)',backgroundImage:`url(${backGroundImg})no-repeat`,height:'400px'}}>
                    <img src={`url(${backGroundImg})no-repeat`} alt={'back'}/>
                    <Title style={{
                            marginTop: '42px',
                            textAlign: 'center',
                        }}>{mockDataTag.children}===调试用的-TagId：{params.id}</Title>
                </Content>
                <Content style={{
                    width: '70%',
                    justifyContent: 'center',
                    border: '1px solid var(--semi-color-border)',
                    marginLeft: '15%',
                    marginTop: '62px'
                }}>
                    {
                        mockDataMovie.map((movie, idx) => (
                            <Layout>
                                <Sider>
                                    <MovieImgCard imgUrl={movie.imgUrl} cardWidth={150} cardHeight={250}></MovieImgCard>
                                </Sider>
                                <Content>
                                    <MovieDetail movieName={movie.movieName}
                                                 movieQuote={movie.movieQuote}></MovieDetail>
                                </Content>
                            </Layout>
                        ))
                    }
                </Content>
            </Layout>

        </div>
    );
};
export default TagMoviePage