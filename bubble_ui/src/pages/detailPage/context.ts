export type MovieDetail = {
    movieName: string;
    imgUrl: string;
    movieQuote: string;
}

export class MovieDetailData {
    movieName: string;
    imgUrl: string;
    movieQuote: string;

    constructor(movieName: string, imgUrl: string, movieQuote: string) {
        this.movieName = movieName;
        this.imgUrl = imgUrl;
        this.movieQuote = movieQuote;
    }

}