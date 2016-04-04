package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Profile extends AbsData implements Serializable {

    private static final long serialVersionUID = 7069899118180262809L;

    @SerializedName("battleTag")
    private String battleTag;
    @SerializedName("paragonLevel")
    private int paragonLevel;
    @SerializedName("paragonLevelHardcore")
    private int paragonLevelHardcore;
    @SerializedName("paragonLevelSeason")
    private int paragonLevelSeason;
    @SerializedName("paragonLevelSeasonHardcore")
    private int paragonLevelSeasonHardcore;
    @SerializedName("guildName")
    private String guildName;
    @SerializedName("heroes")
    private Hero[] heroes;
    @SerializedName("lastHeroPlayed")
    private long lastHeroPlayed;
    @SerializedName("lastUpdated")
    private long lastUpdated;
    @SerializedName("kills")
    private Kills kills;
    @SerializedName("highestHardcoreLevel")
    private int highestHardcoreLevel;
    @SerializedName("timePlayed")
    private TimePlayed timePlayed;
    @SerializedName("progression")
    private Progression progression;
    @SerializedName("fallenHeroes")
    private FallenHero[] fallenHeroes;
    @SerializedName("blacksmith")
    private Blacksmith blacksmith;
    @SerializedName("jeweler")
    private Jeweler jeweler;
    @SerializedName("mystic")
    private Mystic mystic;
    @SerializedName("blacksmithHardcore")
    private Blacksmith blacksmithHardcore;
    @SerializedName("jewelerHardcore")
    private Jeweler jewelerHardcore;
    @SerializedName("mysticHardcore")
    private Mystic mysticHardcore;
    @SerializedName("blacksmithSeason")
    private Blacksmith blacksmithSeason;
    @SerializedName("jewelerSeason")
    private Jeweler jewelerSeason;
    @SerializedName("mysticSeason")
    private Mystic mysticSeason;
    @SerializedName("blacksmithSeasonHardcore")
    private Blacksmith blacksmithSeasonHardcore;
    @SerializedName("jewelerSeasonHardcore")
    private Jeweler jewelerSeasonHardcore;
    @SerializedName("mysticSeasonHardcore")
    private Mystic mysticSeasonHardcore;
    @SerializedName("seasonalProfiles")
    private SeasonalProfiles seasonalProfiles;

    public Profile() {
        super();
    }

    public String getBattleTag() {
        return battleTag;
    }

    public void setBattleTag(String battleTag) {
        this.battleTag = battleTag;
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

    public int getParagonLevelSeason() {
        return paragonLevelSeason;
    }

    public void setParagonLevelSeason(int paragonLevelSeason) {
        this.paragonLevelSeason = paragonLevelSeason;
    }

    public int getParagonLevelSeasonHardcore() {
        return paragonLevelSeasonHardcore;
    }

    public void setParagonLevelSeasonHardcore(int paragonLevelSeasonHardcore) {
        this.paragonLevelSeasonHardcore = paragonLevelSeasonHardcore;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public long getLastHeroPlayed() {
        return lastHeroPlayed;
    }

    public void setLastHeroPlayed(long lastHeroPlayed) {
        this.lastHeroPlayed = lastHeroPlayed;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getHighestHardcoreLevel() {
        return highestHardcoreLevel;
    }

    public void setHighestHardcoreLevel(int highestHardcoreLevel) {
        this.highestHardcoreLevel = highestHardcoreLevel;
    }

    public Hero[] getHeroes() {
        return heroes;
    }

    public void setHeroes(Hero[] heroes) {
        this.heroes = heroes;
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

    public Progression getProgression() {
        return progression;
    }

    public void setProgression(Progression progression) {
        this.progression = progression;
    }

    public FallenHero[] getFellenHeroes() {
        return fallenHeroes;
    }

    public void setFellenHeroes(FallenHero[] fellenHeroes) {
        this.fallenHeroes = fellenHeroes;
    }

    public Blacksmith getBlacksmith() {
        return blacksmith;
    }

    public void setBlacksmith(Blacksmith blacksmith) {
        this.blacksmith = blacksmith;
    }

    public Jeweler getJeweler() {
        return jeweler;
    }

    public void setJeweler(Jeweler jeweler) {
        this.jeweler = jeweler;
    }

    public Mystic getMystic() {
        return mystic;
    }

    public void setMystic(Mystic mystic) {
        this.mystic = mystic;
    }

    public Blacksmith getBlacksmithHardcore() {
        return blacksmithHardcore;
    }

    public void setBlacksmithHardcore(Blacksmith blacksmithHardcore) {
        this.blacksmithHardcore = blacksmithHardcore;
    }

    public Jeweler getJewelerHardcore() {
        return jewelerHardcore;
    }

    public void setJewelerHardcore(Jeweler jewelerHardcore) {
        this.jewelerHardcore = jewelerHardcore;
    }

    public Mystic getMysticHardcore() {
        return mysticHardcore;
    }

    public void setMysticHardcore(Mystic mysticHardcore) {
        this.mysticHardcore = mysticHardcore;
    }

    public Blacksmith getBlacksmithSeason() {
        return blacksmithSeason;
    }

    public void setBlacksmithSeason(Blacksmith blacksmithSeason) {
        this.blacksmithSeason = blacksmithSeason;
    }

    public Jeweler getJewelerSeason() {
        return jewelerSeason;
    }

    public void setJewelerSeason(Jeweler jewelerSeason) {
        this.jewelerSeason = jewelerSeason;
    }

    public Mystic getMysticSeason() {
        return mysticSeason;
    }

    public void setMysticSeason(Mystic mysticSeason) {
        this.mysticSeason = mysticSeason;
    }

    public Blacksmith getBlacksmithSeasonHardcore() {
        return blacksmithSeasonHardcore;
    }

    public void setBlacksmithSeasonHardcore(Blacksmith blacksmithSeasonHardcore) {
        this.blacksmithSeasonHardcore = blacksmithSeasonHardcore;
    }

    public Jeweler getJewelerSeasonHardcore() {
        return jewelerSeasonHardcore;
    }

    public void setJewelerSeasonHardcore(Jeweler jewelerSeasonHardcore) {
        this.jewelerSeasonHardcore = jewelerSeasonHardcore;
    }

    public Mystic getMysticSeasonHardcore() {
        return mysticSeasonHardcore;
    }

    public void setMysticSeasonHardcore(Mystic mysticSeasonHardcore) {
        this.mysticSeasonHardcore = mysticSeasonHardcore;
    }

    public SeasonalProfiles getSeasonalProfiles() {
        return seasonalProfiles;
    }

    public void setSeasonalProfiles(SeasonalProfiles seasonalProfiles) {
        this.seasonalProfiles = seasonalProfiles;
    }
}