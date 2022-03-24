import {List} from "@douyinfe/semi-ui";
export type TagColor =
    | 'amber'
    | 'blue'
    | 'cyan'
    | 'green'
    | 'grey'
    | 'indigo'
    | 'light-blue'
    | 'light-green'
    | 'lime'
    | 'orange'
    | 'pink'
    | 'purple'
    | 'red'
    | 'teal'
    | 'violet'
    | 'yellow'
    | 'white';
export default interface TagDetail {
    tagId: number;
    tagName: string;
    tagColor: TagColor;
}
export const getEmptyTagDetail =():TagDetail=>{
    return {tagId:-1,tagName:'',tagColor:'white'};
};
// export const getEmptyTagDetailList =():List<TagDetail>=>{
//     return new List<TagDetail>([{ tagId:-1,tagName:'',tagColor:''}])
//
// }
