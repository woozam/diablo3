package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

public class Active extends AbsSkill {

    private static final long serialVersionUID = -1174791233172396124L;

    @SerializedName("skill")
    private Skill skill;
    @SerializedName("rune")
    private Rune rune;

    public Active() {
        super();
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Rune getRune() {
        return rune;
    }

    public void setRune(Rune rune) {
        this.rune = rune;
    }
}