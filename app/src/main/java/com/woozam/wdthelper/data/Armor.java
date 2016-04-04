package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by woozam on 2016-02-15.
 */
public class Armor extends AbsData {

    @SerializedName("min")
    private float min;
    @SerializedName("max")
    private float max;

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }
}
