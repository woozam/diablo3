package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by woozam on 2016-02-01.
 */
public class Attributes extends AbsData {

    @SerializedName("primary")
    private Attribute[] primary;
    @SerializedName("secondary")
    private Attribute[] secondary;
    @SerializedName("passive")
    private Attribute[] passive;

    public Attribute[] getPrimary() {
        return primary;
    }

    public void setPrimary(Attribute[] primary) {
        this.primary = primary;
    }

    public Attribute[] getSecondary() {
        return secondary;
    }

    public void setSecondary(Attribute[] secondary) {
        this.secondary = secondary;
    }

    public Attribute[] getPassive() {
        return passive;
    }

    public void setPassive(Attribute[] passive) {
        this.passive = passive;
    }
}