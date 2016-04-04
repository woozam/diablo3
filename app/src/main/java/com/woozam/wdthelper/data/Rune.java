package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

public class Rune extends AbsData {

    private static final long serialVersionUID = -8615425971959955771L;

    @SerializedName("slug")
    private String slug;
    @SerializedName("type")
    private String type;
    @SerializedName("name")
    private String name;
    @SerializedName("level")
    private int level;
    @SerializedName("description")
    private String description;
    @SerializedName("simpleDescription")
    private String simpleDescription;
    @SerializedName("tooltipParams")
    private String tooltipParams;
    @SerializedName("skillCalcId")
    private String skillCalcId;
    @SerializedName("order")
    private int order;

    public Rune() {
        super();
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSimpleDescription() {
        return simpleDescription;
    }

    public void setSimpleDescription(String simpleDescription) {
        this.simpleDescription = simpleDescription;
    }

    public String getTooltipParams() {
        return tooltipParams;
    }

    public void setTooltipParams(String tooltipParams) {
        this.tooltipParams = tooltipParams;
    }

    public String getSkillCalcId() {
        return skillCalcId;
    }

    public void setSkillCalcId(String skillCalcId) {
        this.skillCalcId = skillCalcId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}