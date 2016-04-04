package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

public class CompletedQuest extends AbsData {

    private static final long serialVersionUID = -54194512011367380L;

    @SerializedName("slug")
    private String slug;
    @SerializedName("name")
    private String name;

    public CompletedQuest() {
        super();
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}