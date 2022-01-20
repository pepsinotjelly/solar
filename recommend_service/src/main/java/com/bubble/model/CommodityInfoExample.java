package com.bubble.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommodityInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CommodityInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(Integer value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(Integer value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(Integer value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(Integer value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(Integer value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<Integer> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<Integer> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(Integer value1, Integer value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andFirstLevelLableIsNull() {
            addCriterion("first_level_lable is null");
            return (Criteria) this;
        }

        public Criteria andFirstLevelLableIsNotNull() {
            addCriterion("first_level_lable is not null");
            return (Criteria) this;
        }

        public Criteria andFirstLevelLableEqualTo(Integer value) {
            addCriterion("first_level_lable =", value, "firstLevelLable");
            return (Criteria) this;
        }

        public Criteria andFirstLevelLableNotEqualTo(Integer value) {
            addCriterion("first_level_lable <>", value, "firstLevelLable");
            return (Criteria) this;
        }

        public Criteria andFirstLevelLableGreaterThan(Integer value) {
            addCriterion("first_level_lable >", value, "firstLevelLable");
            return (Criteria) this;
        }

        public Criteria andFirstLevelLableGreaterThanOrEqualTo(Integer value) {
            addCriterion("first_level_lable >=", value, "firstLevelLable");
            return (Criteria) this;
        }

        public Criteria andFirstLevelLableLessThan(Integer value) {
            addCriterion("first_level_lable <", value, "firstLevelLable");
            return (Criteria) this;
        }

        public Criteria andFirstLevelLableLessThanOrEqualTo(Integer value) {
            addCriterion("first_level_lable <=", value, "firstLevelLable");
            return (Criteria) this;
        }

        public Criteria andFirstLevelLableIn(List<Integer> values) {
            addCriterion("first_level_lable in", values, "firstLevelLable");
            return (Criteria) this;
        }

        public Criteria andFirstLevelLableNotIn(List<Integer> values) {
            addCriterion("first_level_lable not in", values, "firstLevelLable");
            return (Criteria) this;
        }

        public Criteria andFirstLevelLableBetween(Integer value1, Integer value2) {
            addCriterion("first_level_lable between", value1, value2, "firstLevelLable");
            return (Criteria) this;
        }

        public Criteria andFirstLevelLableNotBetween(Integer value1, Integer value2) {
            addCriterion("first_level_lable not between", value1, value2, "firstLevelLable");
            return (Criteria) this;
        }

        public Criteria andSecondaryLableIsNull() {
            addCriterion("secondary_lable is null");
            return (Criteria) this;
        }

        public Criteria andSecondaryLableIsNotNull() {
            addCriterion("secondary_lable is not null");
            return (Criteria) this;
        }

        public Criteria andSecondaryLableEqualTo(Integer value) {
            addCriterion("secondary_lable =", value, "secondaryLable");
            return (Criteria) this;
        }

        public Criteria andSecondaryLableNotEqualTo(Integer value) {
            addCriterion("secondary_lable <>", value, "secondaryLable");
            return (Criteria) this;
        }

        public Criteria andSecondaryLableGreaterThan(Integer value) {
            addCriterion("secondary_lable >", value, "secondaryLable");
            return (Criteria) this;
        }

        public Criteria andSecondaryLableGreaterThanOrEqualTo(Integer value) {
            addCriterion("secondary_lable >=", value, "secondaryLable");
            return (Criteria) this;
        }

        public Criteria andSecondaryLableLessThan(Integer value) {
            addCriterion("secondary_lable <", value, "secondaryLable");
            return (Criteria) this;
        }

        public Criteria andSecondaryLableLessThanOrEqualTo(Integer value) {
            addCriterion("secondary_lable <=", value, "secondaryLable");
            return (Criteria) this;
        }

        public Criteria andSecondaryLableIn(List<Integer> values) {
            addCriterion("secondary_lable in", values, "secondaryLable");
            return (Criteria) this;
        }

        public Criteria andSecondaryLableNotIn(List<Integer> values) {
            addCriterion("secondary_lable not in", values, "secondaryLable");
            return (Criteria) this;
        }

        public Criteria andSecondaryLableBetween(Integer value1, Integer value2) {
            addCriterion("secondary_lable between", value1, value2, "secondaryLable");
            return (Criteria) this;
        }

        public Criteria andSecondaryLableNotBetween(Integer value1, Integer value2) {
            addCriterion("secondary_lable not between", value1, value2, "secondaryLable");
            return (Criteria) this;
        }

        public Criteria andTertiaryLabelIsNull() {
            addCriterion("tertiary_label is null");
            return (Criteria) this;
        }

        public Criteria andTertiaryLabelIsNotNull() {
            addCriterion("tertiary_label is not null");
            return (Criteria) this;
        }

        public Criteria andTertiaryLabelEqualTo(Integer value) {
            addCriterion("tertiary_label =", value, "tertiaryLabel");
            return (Criteria) this;
        }

        public Criteria andTertiaryLabelNotEqualTo(Integer value) {
            addCriterion("tertiary_label <>", value, "tertiaryLabel");
            return (Criteria) this;
        }

        public Criteria andTertiaryLabelGreaterThan(Integer value) {
            addCriterion("tertiary_label >", value, "tertiaryLabel");
            return (Criteria) this;
        }

        public Criteria andTertiaryLabelGreaterThanOrEqualTo(Integer value) {
            addCriterion("tertiary_label >=", value, "tertiaryLabel");
            return (Criteria) this;
        }

        public Criteria andTertiaryLabelLessThan(Integer value) {
            addCriterion("tertiary_label <", value, "tertiaryLabel");
            return (Criteria) this;
        }

        public Criteria andTertiaryLabelLessThanOrEqualTo(Integer value) {
            addCriterion("tertiary_label <=", value, "tertiaryLabel");
            return (Criteria) this;
        }

        public Criteria andTertiaryLabelIn(List<Integer> values) {
            addCriterion("tertiary_label in", values, "tertiaryLabel");
            return (Criteria) this;
        }

        public Criteria andTertiaryLabelNotIn(List<Integer> values) {
            addCriterion("tertiary_label not in", values, "tertiaryLabel");
            return (Criteria) this;
        }

        public Criteria andTertiaryLabelBetween(Integer value1, Integer value2) {
            addCriterion("tertiary_label between", value1, value2, "tertiaryLabel");
            return (Criteria) this;
        }

        public Criteria andTertiaryLabelNotBetween(Integer value1, Integer value2) {
            addCriterion("tertiary_label not between", value1, value2, "tertiaryLabel");
            return (Criteria) this;
        }

        public Criteria andIsDelIsNull() {
            addCriterion("is_del is null");
            return (Criteria) this;
        }

        public Criteria andIsDelIsNotNull() {
            addCriterion("is_del is not null");
            return (Criteria) this;
        }

        public Criteria andIsDelEqualTo(Integer value) {
            addCriterion("is_del =", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotEqualTo(Integer value) {
            addCriterion("is_del <>", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelGreaterThan(Integer value) {
            addCriterion("is_del >", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_del >=", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelLessThan(Integer value) {
            addCriterion("is_del <", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelLessThanOrEqualTo(Integer value) {
            addCriterion("is_del <=", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelIn(List<Integer> values) {
            addCriterion("is_del in", values, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotIn(List<Integer> values) {
            addCriterion("is_del not in", values, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelBetween(Integer value1, Integer value2) {
            addCriterion("is_del between", value1, value2, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotBetween(Integer value1, Integer value2) {
            addCriterion("is_del not between", value1, value2, "isDel");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}