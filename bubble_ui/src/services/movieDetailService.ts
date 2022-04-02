import http from "../http-common";
import {MovieDetail, MovieDetailBaseResp} from "../model/movie-detail"
import {List} from "@douyinfe/semi-ui";

export function getMovieDetailByMovieId(id: string | undefined) {
    return http.get<MovieDetail>(`/movie/detail/?id=${id}`);
}

export function getMovieDetailByTagId(tagId: string | undefined) {
    return http.get<List<MovieDetail>>(`/movie/tag/?tagId=${tagId}`);
}

export function getRecommendMovieDetailByUserId(page: string | undefined, jwt: string | undefined) {
    return http.get<List<MovieDetail>>(`/movie/recommend/?page=${page}`, {headers: { Authorization: `${jwt}` }});
}