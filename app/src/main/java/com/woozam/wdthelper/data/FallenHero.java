package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FallenHero extends Hero implements Serializable {

    private static final long serialVersionUID = -5502332740821320135L;

    @SerializedName("death")
    private Death death;

    public FallenHero() {
        super();
    }

    public Death getDeath() {
        return death;
    }

    public void setDeath(Death death) {
        this.death = death;
    }
}