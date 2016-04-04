package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Kills extends AbsData implements Serializable {

    private static final long serialVersionUID = -7106763990621988710L;

    @SerializedName("monsters")
    private long monsters;
    @SerializedName("elites")
    private long elites;
    @SerializedName("hardcoreMonsters")
    private long hardcoreMonsters;

    public Kills() {
        super();
    }

    public long getMonsters() {
        return monsters;
    }

    public void setMonsters(long monsters) {
        this.monsters = monsters;
    }

    public long getElites() {
        return elites;
    }

    public void setElites(long elites) {
        this.elites = elites;
    }

    public long getHardcoreMonsters() {
        return hardcoreMonsters;
    }

    public void setHardcoreMonsters(long hardcoreMonsters) {
        this.hardcoreMonsters = hardcoreMonsters;
    }
}