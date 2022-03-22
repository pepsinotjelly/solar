import React from "react";
import ItemInfo from "./components/itemInfo";
import {Layout, Typography} from "@douyinfe/semi-ui";


const Recommend = () => {
    const {Title} = Typography;
     const mockData = [
        {
            movieId:11362,
            movieName: '新基督山伯爵',
            imgUrl: 'https://image.tmdb.org/t/p/w300_and_h450_bestv2/aoKzR82lgcle1NmlayZ9wURhwFa.jpg'
        },
        {
            movieId:26957,
            movieName: '魔偶奇谭4：邪神军团',
            imgUrl: 'https://image.tmdb.org/t/p/w300_and_h450_bestv2/fRIpNpZVL73MGJ7E2ZYt03tggeX.jpg'
        },
        {
            movieId:281338,
            movieName: '猩球崛起3：终极之战',
            imgUrl: 'https://image.tmdb.org/t/p/w300_and_h450_bestv2/cu0bxkxDeqwjCowG5jsH4lpopSM.jpg'
        },
        {
            movieId:38582,
            movieName: '哥斯拉对机械哥斯拉',
            imgUrl: 'https://image.tmdb.org/t/p/w300_and_h450_bestv2/mdeEGozorRqHSLs0kTjVCyNH3Va.jpg'
        }
    ]
    return (
        <div style={{justifyContent: 'center',
            height:'100%'
        }}>
            <Title heading={2}
                   style={{
                       justifyContent: 'center',
                       textAlign: 'center'
                   }}
            > 猜你喜欢 </Title>
            <br/>
            <ItemInfo movieList={mockData}/>
        </div>
    );
};
export default Recommend