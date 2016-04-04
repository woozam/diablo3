package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TimePlayed extends AbsData implements Serializable {

    private static final long serialVersionUID = -6767260262895905854L;

    @SerializedName("barbarian")
    private float barbarian;
    @SerializedName("crusader")
    private float crusader;
    @SerializedName("demon-hunter")
    private float demonHunter;
    @SerializedName("monk")
    private float monk;
    @SerializedName("witch-doctor")
    private float witchDoctor;
    @SerializedName("wizard")
    private float wizard;

    public TimePlayed() {
        super();
    }

    public float getBarbarian() {
        return barbarian;
    }

    public void setBarbarian(float barbarian) {
        this.barbarian = barbarian;
    }

    public float getCrusader() {
        return crusader;
    }

    public void setCrusader(float crusader) {
        this.crusader = crusader;
    }

    public float getDemonHunter() {
        return demonHunter;
    }

    public void setDemonHunter(float demonHunter) {
        this.demonHunter = demonHunter;
    }

    public float getMonk() {
        return monk;
    }

    public void setMonk(float monk) {
        this.monk = monk;
    }

    public float getWitchDoctor() {
        return witchDoctor;
    }

    public void setWitchDoctor(float witchDoctor) {
        this.witchDoctor = witchDoctor;
    }

    public float getWizard() {
        return wizard;
    }

    public void setWizard(float wizard) {
        this.wizard = wizard;
    }
}