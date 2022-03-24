import React, {useEffect, useState} from 'react';
import {Layout, List} from '@douyinfe/semi-ui';
import Comment from "./components/comment-panel";
import MovieImgCard from "./components/movie-img";
import MovieDetail from "./components/movie-detail";
import {Link, useParams} from "react-router-dom";
import NotFound from "../404";
import TagDetailPlane from "./components/tags-detail";
import movieDetail from "./components/movie-detail";
import {getMovieDetailByMovieId} from "../../services/movieDetailService";
import {getTagDetailByMovieId} from "../../services/tagDetailService";
import {getEmptyTagDetail} from "../../model/tag-detail";


function DetailPage() {
    let params = useParams();
    const {Content, Sider} = Layout;
    const [movieDetailData, setMovieDetailData] = useState({
        movieId: undefined,
        movieName: undefined,
        imgUrl: undefined,
        movieQuote: undefined
    })
    const [tagDetailData,setTagDetailData] = useState({
        tagDetailList: [getEmptyTagDetail()]
    })

    const getMovieDetail = () => {
        getMovieDetailByMovieId(params.id)
            .then((response:any) =>{
                setMovieDetailData(response.data)
                console.log(response.data);
            }).catch((e:Error) =>{
                console.log(e);
        });
    }
    const getTagDetail =()=>{
        getTagDetailByMovieId(params.id)
            .then((response:any)=>{
                setTagDetailData(response.data);
            }).catch((e:Error)=>{
                console.log(e);
        })
    }
    useEffect(() => {
        getMovieDetail()
    }, []);
    useEffect(()=>{
        getTagDetail()
    },[])
    if (typeof params.id === "string") {
        // @ts-ignore
        return (
            <>
                <h2>调试用的-movieId:{params.id}</h2>
                <Layout className={'movie-detail-mainly-layout'} style={{height: '575px'}}>
                    <Sider style={{marginLeft: '42px'}}>
                        <MovieImgCard imgUrl={movieDetailData.imgUrl} cardWidth={333} cardHeight={500} movieName={''}/>
                    </Sider>
                    <Content className={"comment-panel-context-info"}
                             style={{marginRight: '92px', marginLeft: '42px'}}>
                        <MovieDetail movieName={movieDetailData.movieName}
                                     movieQuote={movieDetailData.movieQuote}
                        />
                        <TagDetailPlane tagList={tagDetailData} tagBackgroundSize={'18px'}
                                        tagFrontSize={'14px'}/>
                        <Comment/>
                    </Content>
                </Layout>
            </>
        );
    }
    return (
        <>
            <Layout className={'movie-detail-mainly-layout'} style={{height: '575px'}}>
                <NotFound/>
            </Layout>
        </>
    );
}

export default DetailPage