import React, {useEffect, useState} from "react";
import ItemInfo from "./components/itemInfo";
import {Layout, Toast, Typography} from "@douyinfe/semi-ui";
import {getMovieDetailByTagId, getRecommendMovieDetailByUserId} from "../../services/movieDetailService";
import {MovieDetail} from "../../model/movie-detail";
import {JWT} from "../../constants";
import {useGlobalContext} from "../../context";


const Recommend = () => {
    const {Title} = Typography;
    const {userContext, setUserContext} = useGlobalContext()
    const userId = "100";
    const [movieDetailData, setMovieDetailData] = useState<MovieDetail[]>([])
    const getMovieDetail = () => {
        getRecommendMovieDetailByUserId("1")
            .then((response: any) => {
                setMovieDetailData(response.data);
                console.log("/movie/recommend/ response.data :: ",response.data)
            }).catch((e: Error) => {
            console.log("/movie/recommend/ ERROR :: ",e);
        })
    }
    useEffect(() => {
        getMovieDetail()
    }, [])

    return (
        <div style={{justifyContent: 'center', height: '100%'}}>
            <Title heading={2}
                   style={{
                       justifyContent: 'center',
                       textAlign: 'center'
                   }}
            > 猜你喜欢 </Title>
            <br/>
            <ItemInfo movieList={movieDetailData}/>
        </div>
    );
};
export default Recommend