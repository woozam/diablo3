package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Death extends AbsData implements Serializable {

    private static final long serialVersionUID = -2959485116047843072L;

    @SerializedName("killer")
    private long killer;
    @SerializedName("time")
    private long time;
    @SerializedName("location")
    private long location;

    public Death() {
        super();
    }

    public long getKiller() {
        return killer;
    }

    public void setKiller(long killer) {
        this.killer = killer;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getLocation() {
        return location;
    }

    public void setLocation(long location) {
        this.location = location;
    }
}