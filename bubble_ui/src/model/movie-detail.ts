import {List} from "@douyinfe/semi-ui";
import BaseResp from "./base-resp"

export interface MovieDetail {
    movieId: number;
    movieName: string;
    imgUrl: string;
    movieQuote: string;
}

export interface MovieDetailBaseResp{
    movieDetailList :List<MovieDetail>;
    baseResp:BaseResp;
}
















// export class MovieDetailData implements MovieDetail{
//     movieId:number;
//     movieName: string;
//     imgUrl: string;
//     movieQuote: string;
//     movieTag:any[];
//
//     // 构造函数
//     constructor(movieId: number, movieName: string, imgUrl: string, movieQuote: string,movieTag:any[]) {
//         this.movieId=movieId;
//         this.movieName = movieName;
//         this.imgUrl = imgUrl;
//         this.movieQuote = movieQuote;
//         this.movieTag=movieTag;
//     }
//     // 方法
//     disp():void {
//         console.log("电影名称为 :   "+this.movieName)
//     }
// }

