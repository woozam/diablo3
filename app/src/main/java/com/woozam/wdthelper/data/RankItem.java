package com.woozam.wdthelper.data;

public class RankItem extends AbsData {

    private String rank;
    private BattleTag battleTag;
    private String name;
    private String icon;
    private int riftLevel;
    private String riftTime;
    private String completedTime;
    private long heroId;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public BattleTag getBattleTag() {
        return battleTag;
    }

    public void setBattleTag(BattleTag battleTag) {
        this.battleTag = battleTag;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getRiftLevel() {
        return riftLevel;
    }

    public void setRiftLevel(int riftLevel) {
        this.riftLevel = riftLevel;
    }

    public String getRiftTime() {
        return riftTime;
    }

    public void setRiftTime(String riftTime) {
        this.riftTime = riftTime;
    }

    public String getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(String completedTime) {
        this.completedTime = completedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getHeroId() {
        return heroId;
    }

    public void setHeroId(long heroId) {
        this.heroId = heroId;
    }
}