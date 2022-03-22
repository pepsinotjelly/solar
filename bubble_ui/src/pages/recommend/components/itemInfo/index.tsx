import React, {useState} from 'react';
import {Card, CardGroup, Typography, Slider, Pagination, Progress, Layout} from '@douyinfe/semi-ui';
import {IconLink} from "@douyinfe/semi-icons";
import {Link} from "react-router-dom";
import axios from "axios";


function ItemInfo() {
    const {Content} = Layout;
    const mockPage = 5;
    const [page, setPage] = useState(1)
    function onPageChange(currentPage: React.SetStateAction<number>) {
        setPage(currentPage);
    }
    const mockData = [
        {
            movieName: '新基督山伯爵',
            imgUrl: 'https://image.tmdb.org/t/p/w300_and_h450_bestv2/aoKzR82lgcle1NmlayZ9wURhwFa.jpg'
        },
        {
            movieName: '魔偶奇谭4：邪神军团',
            imgUrl: 'https://image.tmdb.org/t/p/w300_and_h450_bestv2/fRIpNpZVL73MGJ7E2ZYt03tggeX.jpg'
        },
        {
            movieName: '猩球崛起3：终极之战',
            imgUrl: 'https://image.tmdb.org/t/p/w300_and_h450_bestv2/cu0bxkxDeqwjCowG5jsH4lpopSM.jpg'
        },
        {
            movieName: '哥斯拉对机械哥斯拉',
            imgUrl: 'https://image.tmdb.org/t/p/w300_and_h450_bestv2/mdeEGozorRqHSLs0kTjVCyNH3Va.jpg'
        }
    ]

    return (
        <>
            <Layout style={{
                marginLeft: '4.5%',
                marginBottom: '1px',
                width: '90%',
                height: '100%'
            }}>
                <Content style={{
                    // border: '1px solid var(--semi-color-border)',
                }}>
                    <CardGroup spacing={33}
                               style={{justifyContent: 'center'}}>
                        {
                            mockData.map((item, idx) => (
                                <Card
                                    key={idx}
                                    shadows='hover'
                                    title={item.movieName}
                                    headerLine={false}
                                    style={{
                                        width: "20%",
                                        height: 440
                                    }}
                                    cover={
                                        <img
                                            alt={'ImgUrl'}
                                            src={item.imgUrl}
                                        />
                                    }
                                    headerExtraContent={
                                        <Link to={"/comment-panel"}>
                                            <IconLink></IconLink>
                                        </Link>
                                    }
                                />
                            ))
                        }
                    </CardGroup>
                    <Content>
                        <br/><br/>
                        <Pagination
                            style={{
                                marginBottom: 4,
                                justifyContent: 'center'
                            }}
                            total={50}
                            currentPage={page}
                            onPageChange={onPageChange}
                        />
                    </Content>
                </Content>
            </Layout>
        </>
    );
}

export default ItemInfo;