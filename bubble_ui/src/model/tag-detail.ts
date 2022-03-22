export type TagDetailType={
    tagId: number;
    tagName: string;
    tagColor: string;
}
export interface TagDetail {
    tagId: number;
    tagName: string;
    tagColor: string;
}

export class TagDetailDataImpl implements TagDetail {
    tagId: number;
    tagName: string;
    tagColor: string;

    // 构造函数
    constructor(tagId: number, tagName: string, tagColor: string) {
        this.tagId=tagId;
        this.tagName=tagName;
        this.tagColor= tagColor;
    }
}