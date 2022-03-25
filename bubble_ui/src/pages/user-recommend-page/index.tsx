import React, {useEffect, useState} from "react";
import ItemInfo from "./components/itemInfo";
import {Layout, Typography} from "@douyinfe/semi-ui";
import {getMovieDetailByTagId, getRecommendMovieDetailByUserId} from "../../services/movieDetailService";
import MovieDetail from "../../model/movie-detail";


const Recommend = () => {
    const {Title} = Typography;
    const userId = "100";
    const [movieDetailData, setMovieDetailData] = useState<MovieDetail[]>([])
    const getMovieDetail = () => {
        getRecommendMovieDetailByUserId(userId,"1")
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

    return (
        <div style={{justifyContent: 'center',height:'100%'}}>
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