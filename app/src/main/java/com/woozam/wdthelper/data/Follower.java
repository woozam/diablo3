package com.woozam.wdthelper.data;

/**
 * Created by woozam on 2016-02-13.
 */
public class Follower extends AbsData {

    private String slug;
    private int level;
    private Items items;
    private Stats stats;
    private FollowerSkills[] skills;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public FollowerSkills[] getSkills() {
        return skills;
    }

    public void setSkills(FollowerSkills[] skills) {
        this.skills = skills;
    }
}
