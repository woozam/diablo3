package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

public class Skills extends AbsData {

    private static final long serialVersionUID = -7081075991981862139L;

    @SerializedName("active")
    private Active[] active;
    @SerializedName("passive")
    private Passive[] passive;

    public Skills() {
        super();
    }

    public Active[] getActive() {
        return active;
    }

    public void setActive(Active[] active) {
        this.active = active;
    }

    public Passive[] getPassive() {
        return passive;
    }

    public void setPassive(Passive[] passive) {
        this.passive = passive;
    }
}