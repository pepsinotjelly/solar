export interface MovieDetail{
    movieName: string;
    imgUrl: string;
    movieQuote: string;
}

export class MovieDetailData implements MovieDetail{
    movieName: string;
    imgUrl: string;
    movieQuote: string;

    // 构造函数
    constructor(movieName: string, imgUrl: string, movieQuote: string) {
        this.movieName = movieName;
        this.imgUrl = imgUrl;
        this.movieQuote = movieQuote;
    }
    // 方法
    disp():void {
        console.log("电影名称为 :   "+this.movieName)
    }
}

export const getEmptyMovieDetailData =()=>{

}
