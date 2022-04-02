import http from "../http-common";
import {List} from "@douyinfe/semi-ui";
import RatingRecord from "../model/rating-record";

export function submitRatingRecord(ratingRecord:RatingRecord,jwt :string){
    return http.post<RatingRecord>("/rating/record",ratingRecord,{headers: {Authorization: `${jwt}`}})
}