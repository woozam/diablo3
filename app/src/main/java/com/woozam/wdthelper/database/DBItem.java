package com.woozam.wdthelper.database;

import com.woozam.wdthelper.data.BattleTag;

/**
 * Created by woozam on 2016-02-06.
 */
public class DBItem {

    private String tooltipParams;
    private String icon;
    private String name;
    private String color;
    private BattleTag battleTag;
    private long heroId;
    private String heroName;

    public String getTooltipParams() {
        return tooltipParams;
    }

    public void setTooltipParams(String tooltipParams) {
        this.tooltipParams = tooltipParams;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BattleTag getBattleTag() {
        return battleTag;
    }

    public void setBattleTag(BattleTag battleTag) {
        this.battleTag = battleTag;
    }

    public long getHeroId() {
        return heroId;
    }

    public void setHeroId(long heroId) {
        this.heroId = heroId;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o == this) {
            return true;
        } else if (o instanceof DBItem) {
            return tooltipParams.equals(((DBItem) o).getTooltipParams()) && battleTag.equals(((DBItem) o).getBattleTag());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (battleTag.getBattleTag() + battleTag.getServer() + tooltipParams).hashCode();
    }
}