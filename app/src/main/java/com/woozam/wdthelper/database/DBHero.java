package com.woozam.wdthelper.database;

import com.woozam.wdthelper.data.BattleTag;

/**
 * Created by woozam on 2016-02-06.
 */
public class DBHero {

    private BattleTag battleTag;
    private long heroId;
    private String heroName;
    private String heroClass;
    private int heroGender;

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

    public String getHeroClass() {
        return heroClass;
    }

    public void setHeroClass(String heroClass) {
        this.heroClass = heroClass;
    }

    public int getHeroGender() {
        return heroGender;
    }

    public void setHeroGender(int heroGender) {
        this.heroGender = heroGender;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o == this) {
            return true;
        } else if (o instanceof DBHero) {
            return heroId == ((DBHero) o).getHeroId() && battleTag.equals(((DBHero) o).getBattleTag());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (battleTag.getBattleTag() + battleTag.getServer() + heroId).hashCode();
    }
}