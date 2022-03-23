import React, {useState} from 'react';
import {Link, useParams} from "react-router-dom";
import {Typography, Layout} from '@douyinfe/semi-ui';
import MovieImgCard from "../movie-detail-page/components/movie-img";
import MovieDetail from "../movie-detail-page/components/movie-detail";
import TagDetailPlane from "../movie-detail-page/components/tags-detail";

function AllTagPage() {
    const {Content} =Layout
    const mockData = [
        {tagId: 1, children: 'Adventure',color:  'amber'},
        {tagId: 2, children: 'Animation',color:  'green'},
        {tagId: 3, children: 'Children',color:  'pink'},
        {tagId: 4, children: 'Comedy',color:  'orange'},
        {tagId: 5,  children: 'Fantasy', color:'violet'},
        {tagId: 6, children: 'Romance', color: 'purple'},
        {tagId: 7, children:'Drama', color:  'lime'},
        {tagId: 8, children:  'Action',color: 'red'},
        {tagId: 9, children: 'Crime', color: 'blue'},
        {tagId: 10,  children: 'Thriller',color: 'red'},
        {tagId: 11, children: 'Horror',color:  'blue'},
        {tagId: 12, children:   'Mystery',color:'amber'},
        {tagId: 13, children: 'Sci-Fi',color:  'lime'},
        {tagId: 14, children: 'War',color:  'red'},
        {tagId: 15,  children:'Musical', color: 'light-blue'},
        {tagId: 16,  children:'Documentary', color: 'indigo'},
        {tagId: 17, children: 'IMAX', color: 'teal'},
        {tagId: 18, children: 'Western',color: 'yellow'},
        {tagId: 19, children: 'Film-Noir',color:  'cyan'},
        {tagId: 20, children: '(no genres listed)', color: 'white'}
    ]
    return (
        <div>
            <Layout >
                <Content style={{
                    width:'500',
                    border: '1px solid var(--semi-color-border)',
                }}>

                    <TagDetailPlane tagList={mockData}/>

                </Content>
            </Layout>

        </div>
    );
}

export default AllTagPage