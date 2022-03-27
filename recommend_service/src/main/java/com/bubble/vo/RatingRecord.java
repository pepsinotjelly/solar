package com.bubble.vo;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/26 9:40 上午
 * @Desc :
 */
public class RatingRecord {
    int movieId;
    int userId;
    String rating;
    String comment;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
