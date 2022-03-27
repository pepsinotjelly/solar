package com.bubble.vo;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/24 6:41 下午
 * @Desc :
 */
public class MovieDetail {
    String movieId;
    String movieName;
    String movieQuote;
    String imgUrl;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieQuote() {
        return movieQuote;
    }

    public void setMovieQuote(String movieQuote) {
        this.movieQuote = movieQuote;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
