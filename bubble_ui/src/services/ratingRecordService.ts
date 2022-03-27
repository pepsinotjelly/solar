import http from "../http-common";
import MovieDetail from "../model/movie-detail"
import {List} from "@douyinfe/semi-ui";
import RatingRecord from "../model/rating-record";

export function submitRatingRecord(ratingRecord:RatingRecord){
    return http.post<RatingRecord>("/rating/record",ratingRecord)
}