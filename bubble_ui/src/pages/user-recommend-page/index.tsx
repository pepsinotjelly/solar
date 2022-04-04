import React, {useEffect, useState} from "react";
import ItemInfo from "./components/itemInfo";
import {Button, Layout, Pagination, Toast, Typography} from "@douyinfe/semi-ui";
import {getMovieDetailByTagId, getRecommendMovieDetailByUserId} from "../../services/movieDetailService";
import {MovieDetail} from "../../model/movie-detail";
import {JWT} from "../../constants";
import {useGlobalContext} from "../../context";
import {Link} from "react-router-dom";


const Recommend = () => {

    const {Content} = Layout;
    //  Pagination 页面的设置
    const mockPage = 50;
    const {Title} = Typography;
    const {userContext, setUserContext} = useGlobalContext()

    const [page, setPage] = useState(1)

    function onPageChange(currentPage: React.SetStateAction<number>) {
        setPage(currentPage);
        getMovieDetail();
    }

    const [movieDetailData, setMovieDetailData] = useState<MovieDetail[]>([])
    const getMovieDetail = () => {
        getRecommendMovieDetailByUserId(page.toString(), localStorage.getItem(JWT) ?? "")
            .then((response: any) => {
                setMovieDetailData(response.data);
                console.log("/movie/recommend/ response.data :: ", response.data)
            }).catch((e: Error) => {
            console.log("/movie/recommend/ ERROR :: ", e);
        })
    }
    useEffect(() => {
        getMovieDetail()
    }, [])
    if (localStorage.getItem(JWT) === null) {
        return (
            <div>
                <Title heading={2}
                       style={{
                           justifyContent: 'center',
                           textAlign: 'center'
                       }}
                > 本周热榜
                    <Link to={"/login"}>
                        <Button style={{marginLeft: "3%"}}>
                            点击登陆解锁更多惊喜
                        </Button>
                    </Link></Title>

                <br/>
                <ItemInfo movieList={movieDetailData}/>
                <Content>
                    <br/><br/>
                    <Pagination
                        style={{marginBottom: 4, justifyContent: 'center'}}
                        total={mockPage}
                        currentPage={page}
                        onPageChange={onPageChange}
                    />
                </Content>
            </div>
        );
    }
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
            <Content>
                <br/><br/>
                <Pagination
                    style={{marginBottom: 4, justifyContent: 'center'}}
                    total={mockPage}
                    currentPage={page}
                    onPageChange={onPageChange}
                />
            </Content>
        </div>
    );
};
export default Recommend