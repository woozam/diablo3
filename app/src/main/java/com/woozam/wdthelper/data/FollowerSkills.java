package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by woozam on 2016-02-13.
 */
public class FollowerSkills extends AbsSkill {

    @SerializedName("skill")
    private FollowerSkill skill;

    public FollowerSkill getSkill() {
        return skill;
    }

    public void setSkill(FollowerSkill skill) {
        this.skill = skill;
    }
}