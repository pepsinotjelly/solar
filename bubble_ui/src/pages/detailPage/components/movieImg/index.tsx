import {Card, Layout} from "@douyinfe/semi-ui";
import React from "react";

const {Sider} = Layout;

const MovieImg = (movieImg: any) => {
    return (
        <div>
                <Card style={{
                    width: 333, height: 500,
                }}
                      cover={
                          <img alt={'movie_cover'}
                               src={movieImg}
                          />}
                />
        </div>
    );
};
export default MovieImg