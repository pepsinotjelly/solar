import {Card, Layout} from "@douyinfe/semi-ui";
import React from "react";


function MovieImgCard(props: { imgUrl: string | undefined,cardWidth:number,cardHeight:number}) {
    return (
        <div>
            <Card style={{width: props.cardWidth, height: props.cardHeight,}}
                  cover={
                      <img alt={'movie_cover'} src={props.imgUrl}/>}
            />
        </div>
    );
};

export default MovieImgCard