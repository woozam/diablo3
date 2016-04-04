package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SeasonalProfiles extends AbsData implements Serializable {

    private static final long serialVersionUID = 5336030991076646261L;

    @SerializedName("season5")
    private Season season5;
    @SerializedName("season4")
    private Season season4;
    @SerializedName("season3")
    private Season season3;
    @SerializedName("season2")
    private Season season2;
    @SerializedName("season1")
    private Season season1;

    public SeasonalProfiles() {
        super();
    }

    public Season getSeason4() {
        return season4;
    }

    public void setSeason4(Season season4) {
        this.season4 = season4;
    }

    public Season getSeason3() {
        return season3;
    }

    public void setSeason3(Season season3) {
        this.season3 = season3;
    }

    public Season getSeason2() {
        return season2;
    }

    public void setSeason2(Season season2) {
        this.season2 = season2;
    }

    public Season getSeason1() {
        return season1;
    }

    public void setSeason1(Season season1) {
        this.season1 = season1;
    }

    public Season getSeason5() {
        return season5;
    }

    public void setSeason5(Season season5) {
        this.season5 = season5;
    }
}