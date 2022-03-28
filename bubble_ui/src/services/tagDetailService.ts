import http from "../http-common";
import {List} from "@douyinfe/semi-ui";
import TagDetail from "../model/tag-detail";

export function getTagDetailByMovieId(id: string | undefined) {
    return http.get<List<TagDetail>>(`/tag/detail-to-movie/?movieId=${id}`);
}

export function getTagDetailByTagId(tagId: string| undefined) {
    return http.get<TagDetail>(`/tag/detail/?tagId=${tagId}`);
}
