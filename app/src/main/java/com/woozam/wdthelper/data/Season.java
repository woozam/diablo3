package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Season extends AbsData implements Serializable, Comparable<Season> {

    private static final long serialVersionUID = 816365973139644678L;

    @SerializedName("seasonId")
    private long seasonId;
    @SerializedName("paragonLevel")
    private int paragonLevel;
    @SerializedName("paragonLevelHardcore")
    private int paragonLevelHardcore;
    @SerializedName("kills")
    private Kills kills;
    @SerializedName("timePlayed")
    private TimePlayed timePlayed;
    @SerializedName("highestHardcoreLevel")
    private int highestHardcoreLevel;
    @SerializedName("progression")
    private Progression progression;

    public Season() {
        super();
    }

    public long getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(long seasonId) {
        this.seasonId = seasonId;
    }

    public int getParagonLevel() {
        return paragonLevel;
    }

    public void setParagonLevel(int paragonLevel) {
        this.paragonLevel = paragonLevel;
    }

    public int getParagonLevelHardcore() {
        return paragonLevelHardcore;
    }

    public void setParagonLevelHardcore(int paragonLevelHardcore) {
        this.paragonLevelHardcore = paragonLevelHardcore;
    }

    public Kills getKills() {
        return kills;
    }

    public void setKills(Kills kills) {
        this.kills = kills;
    }

    public TimePlayed getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(TimePlayed timePlayed) {
        this.timePlayed = timePlayed;
    }

    public int getHighestHardcoreLevel() {
        return highestHardcoreLevel;
    }

    public void setHighestHardcoreLevel(int highestHardcoreLevel) {
        this.highestHardcoreLevel = highestHardcoreLevel;
    }

    public Progression getProgression() {
        return progression;
    }

    public void setProgression(Progression progression) {
        this.progression = progression;
    }

    @Override
    public int compareTo(Season another) {
        return (int) (this.getSeasonId() - another.getSeasonId());
    }
}