import {Card, Layout} from "@douyinfe/semi-ui";
import React from "react";


function MovieImgCard(props: { imgUrl: string | undefined; }) {
    return (
        <div>
            <Card style={{width: 333, height: 500,}}
                  cover={
                      <img alt={'movie_cover'} src={props.imgUrl}/>}
            />
        </div>
    );
};

export default MovieImgCard