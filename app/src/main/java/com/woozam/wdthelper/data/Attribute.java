package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by woozam on 2016-02-01.
 */
public class Attribute {

    @SerializedName("text")
    private String text;
    @SerializedName("color")
    private String color;
    @SerializedName("affixType")
    private String affixType;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAffixType() {
        return affixType;
    }

    public void setAffixType(String affixType) {
        this.affixType = affixType;
    }
}