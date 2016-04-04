package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

public class HeroProgression extends AbsData {

    private static final long serialVersionUID = -4926178106981669390L;

    @SerializedName("act1")
    private Act act1;
    @SerializedName("act2")
    private Act act2;
    @SerializedName("act3")
    private Act act3;
    @SerializedName("act4")
    private Act act4;
    @SerializedName("act5")
    private Act act5;

    public HeroProgression() {
        super();
    }

    public Act getAct1() {
        return act1;
    }

    public void setAct1(Act act1) {
        this.act1 = act1;
    }

    public Act getAct2() {
        return act2;
    }

    public void setAct2(Act act2) {
        this.act2 = act2;
    }

    public Act getAct3() {
        return act3;
    }

    public void setAct3(Act act3) {
        this.act3 = act3;
    }

    public Act getAct4() {
        return act4;
    }

    public void setAct4(Act act4) {
        this.act4 = act4;
    }

    public Act getAct5() {
        return act5;
    }

    public void setAct5(Act act5) {
        this.act5 = act5;
    }
}