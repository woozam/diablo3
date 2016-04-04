package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Progression extends AbsData implements Serializable {

    private static final long serialVersionUID = 6267280371767987609L;

    @SerializedName("act1")
    private boolean act1;
    @SerializedName("act2")
    private boolean act2;
    @SerializedName("act3")
    private boolean act3;
    @SerializedName("act4")
    private boolean act4;
    @SerializedName("act5")
    private boolean act5;

    public Progression() {
        super();
    }

    public boolean isAct1() {
        return act1;
    }

    public void setAct1(boolean act1) {
        this.act1 = act1;
    }

    public boolean isAct2() {
        return act2;
    }

    public void setAct2(boolean act2) {
        this.act2 = act2;
    }

    public boolean isAct3() {
        return act3;
    }

    public void setAct3(boolean act3) {
        this.act3 = act3;
    }

    public boolean isAct4() {
        return act4;
    }

    public void setAct4(boolean act4) {
        this.act4 = act4;
    }

    public boolean isAct5() {
        return act5;
    }

    public void setAct5(boolean act5) {
        this.act5 = act5;
    }
}