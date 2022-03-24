import {List} from "@douyinfe/semi-ui";

export default interface TagDetail {
    tagId: number;
    tagName: string;
    tagColor: string;
}
export const getEmptyTagDetail =():TagDetail=>{
    return {tagId:-1,tagName:'',tagColor:''};
};
// export const getEmptyTagDetailList =():List<TagDetail>=>{
//     return new List<TagDetail>([{ tagId:-1,tagName:'',tagColor:''}])
//
// }
