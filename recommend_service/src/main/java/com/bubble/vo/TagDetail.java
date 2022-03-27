package com.bubble.vo;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/24 7:41 下午
 * @Desc :
 */
public class TagDetail {
    String tagId;
    String tagName;
    String tagColor;
    String tagImgUrl;

    public String getTagImgUrl() {
        return tagImgUrl;
    }

    public void setTagImgUrl(String tagImgUrl) {
        this.tagImgUrl = tagImgUrl;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagColor() {
        return tagColor;
    }

    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }
}
