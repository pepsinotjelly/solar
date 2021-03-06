import {Card, Layout} from "@douyinfe/semi-ui";
import React from "react";
import Meta from "@douyinfe/semi-ui/lib/es/card/meta";


function MovieImgCard(props: { imgUrl: string | undefined,cardWidth:number,cardHeight:number,movieName:string}) {
    //  使用时配置图片链接及展示尺寸
    return (
        <div>
            <Card style={{width: props.cardWidth, height: props.cardHeight,}}
                  cover={
                      <img alt={'movie_cover'} src={props.imgUrl}/>}
            >
                <Meta title={props.movieName}></Meta>
            </Card>
        </div>
    );
};

export default MovieImgCard