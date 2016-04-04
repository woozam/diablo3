package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;
import com.woozam.wdthelper.R;

import java.io.Serializable;

public class Hero extends AbsData implements Serializable {

    private static final long serialVersionUID = 2257841095567113457L;

    @SerializedName("id")
    protected long id;
    @SerializedName("name")
    private String name;
    @SerializedName("class")
    private String heroClass;
    @SerializedName("gender")
    private int gender;
    @SerializedName("level")
    private int level;
    @SerializedName("kills")
    private Kills kills;
    @SerializedName("paragonLevel")
    private int paragonLevel;
    @SerializedName("hardcore")
    private boolean hardcore;
    @SerializedName("seasonal")
    private boolean seasonal;
    @SerializedName("last-updated")
    private long lastUpdated;
    @SerializedName("dead")
    private boolean dead;
    @SerializedName("seasonCreated")
    private int seasonCreated;
    @SerializedName("skills")
    private Skills skills;
    @SerializedName("items")
    private Items items;
    @SerializedName("followers")
    private Followers followers;
    @SerializedName("legendaryPowers")
    private Item[] legendaryPowers;
    @SerializedName("stats")
    private Stats stats;
    @SerializedName("progression")
    private HeroProgression progression;
    @SerializedName("battleTag")
    private BattleTag battleTag;

    public Hero() {
        super();
    }

    public int getParagonLevel() {
        return paragonLevel;
    }

    public void setParagonLevel(int paragonLevel) {
        this.paragonLevel = paragonLevel;
    }

    public boolean isSeasonal() {
        return seasonal;
    }

    public void setSeasonal(boolean seasonal) {
        this.seasonal = seasonal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isHardcore() {
        return hardcore;
    }

    public void setHardcore(boolean hardcore) {
        this.hardcore = hardcore;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String getHeroClass() {
        return heroClass;
    }

    public void setHeroClass(String heroClass) {
        this.heroClass = heroClass;
    }

    public int getHeroLargeImage() {
        switch (heroClass) {
            case HeroClass.BB:
                if (gender == 0) {
                    return R.mipmap.img_bb_male_large;
                } else {
                    return R.mipmap.img_bb_female_large;
                }
            case HeroClass.CR:
                if (gender == 0) {
                    return R.mipmap.img_cr_male_large;
                } else {
                    return R.mipmap.img_cr_female_large;
                }
            case HeroClass.DH:
                if (gender == 0) {
                    return R.mipmap.img_dh_male_large;
                } else {
                    return R.mipmap.img_dh_female_large;
                }
            case HeroClass.MK:
                if (gender == 0) {
                    return R.mipmap.img_mk_male_large;
                } else {
                    return R.mipmap.img_mk_female_large;
                }
            case HeroClass.WD:
                if (gender == 0) {
                    return R.mipmap.img_wd_male_large;
                } else {
                    return R.mipmap.img_wd_female_large;
                }
            case HeroClass.WZ:
                if (gender == 0) {
                    return R.mipmap.img_wz_male_large;
                } else {
                    return R.mipmap.img_wz_female_large;
                }
            default:
                return 0;
        }
    }

    public Kills getKills() {
        return kills;
    }

    public void setKills(Kills kills) {
        this.kills = kills;
    }

    public HeroProgression getProgression() {
        return progression;
    }

    public void setProgression(HeroProgression progression) {
        this.progression = progression;
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public int getSeasonCreated() {
        return seasonCreated;
    }

    public void setSeasonCreated(int seasonCreated) {
        this.seasonCreated = seasonCreated;
    }

    public Followers getFollowers() {
        return followers;
    }

    public void setFollowers(Followers followers) {
        this.followers = followers;
    }

    public Item[] getLegendaryPowers() {
        return legendaryPowers;
    }

    public void setLegendaryPowers(Item[] legendaryPowers) {
        this.legendaryPowers = legendaryPowers;
    }

    public BattleTag getBattleTag() {
        return battleTag;
    }

    public void setBattleTag(BattleTag battleTag) {
        this.battleTag = battleTag;
    }
}