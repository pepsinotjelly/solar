package com.bubble.model;

import java.util.Date;

public class CommodityInfo {
    private Integer id;

    private String name;

    private Integer price;

    private Integer firstLevelLable;

    private Integer secondaryLable;

    private Integer tertiaryLabel;

    private Integer isDel;

    private Date createTime;

    private Date modifyTime;

    private String feature;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getFirstLevelLable() {
        return firstLevelLable;
    }

    public void setFirstLevelLable(Integer firstLevelLable) {
        this.firstLevelLable = firstLevelLable;
    }

    public Integer getSecondaryLable() {
        return secondaryLable;
    }

    public void setSecondaryLable(Integer secondaryLable) {
        this.secondaryLable = secondaryLable;
    }

    public Integer getTertiaryLabel() {
        return tertiaryLabel;
    }

    public void setTertiaryLabel(Integer tertiaryLabel) {
        this.tertiaryLabel = tertiaryLabel;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature == null ? null : feature.trim();
    }
}