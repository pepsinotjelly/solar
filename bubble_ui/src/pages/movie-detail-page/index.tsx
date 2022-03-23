import React from 'react';
import {Layout} from '@douyinfe/semi-ui';
import Comment from "./components/comment-panel";
import MovieImgCard from "./components/movie-img";
import MovieDetail from "./components/movie-detail";
import {MovieDetailData} from "../../model/movie-detail";
import {Link, useParams} from "react-router-dom";
import NotFound from "../404";
import TagDetailPlane from "./components/tags-detail";


function DetailPage() {
    let params = useParams();
    const {Content, Sider} = Layout;
    if (typeof params.id === "string") {
        const movieDetailData = new MovieDetailData(parseInt(params.id), '这是一个电影名',
            'https://image.tmdb.org/t/p/w300_and_h450_bestv2/wXb9KGqxyrAHNygRRI1HW1BhcU.jpg', '这个电影的简介就是这样一段话。他主要讲了一个什么东西的故事呢？\n' +
            '                        我也没看。但是我得编出来显得很有趣那样子。如果喜欢的话老铁，一键三连吧。\n' +
            '                        但是即使是这样字数还是太少了，一般电影简介它都说点什么呢？那么我该说些什么呢？\n' +
            '                        这个字数应该可以了吧？老铁，多吃点麦当劳吧，人间不值得。与此同时这个是我的mockData，怕你看混了。'
            , [{tagId:1,color:'green',children: '一饿'},
                {tagId:2,color:'red',children: 'hi'},
                {tagId:3,color:'amber',children: '猫咪'},
                {tagId:4,color:'blue',children: '说点什么'},
                {tagId:5,color:'pink',children: '很好的电影'},
                {tagId:6,color:'lime',children: '没了就这样'}]);
        return (
            <>
                <h2>调试用的-movieId:{params.id}</h2>
                <Layout className={'movie-detail-mainly-layout'} style={{height: '575px'}}>
                    <Sider style={{marginLeft: '42px'}}>
                        <MovieImgCard imgUrl={movieDetailData.imgUrl} cardWidth={333} cardHeight={500}/>
                    </Sider>
                    <Content className={"comment-panel-context-info"}
                             style={{marginRight: '92px', marginLeft: '42px'}}>
                        <MovieDetail movieName={movieDetailData.movieName}
                                     movieQuote={movieDetailData.movieQuote}
                        />
                        <TagDetailPlane tagList={movieDetailData.movieTag}/>
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