package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by woozam on 2016-02-01.
 */
public class Gem extends AbsData {

    @SerializedName("item")
    private Item item;
    @SerializedName("isGem")
    private boolean isGem;
    @SerializedName("isJewel")
    private boolean isJewel;
    @SerializedName("attributes")
    private Attributes attributes;
    @SerializedName("jewelRank")
    private int jewelRank;
    @SerializedName("jewelSecondaryEffectUnlockRank")
    private int jewelSecondaryEffectUnlockRank;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isGem() {
        return isGem;
    }

    public void setIsGem(boolean isGem) {
        this.isGem = isGem;
    }

    public boolean isJewel() {
        return isJewel;
    }

    public void setIsJewel(boolean isJewel) {
        this.isJewel = isJewel;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public int getJewelRank() {
        return jewelRank;
    }

    public void setJewelRank(int jewelRank) {
        this.jewelRank = jewelRank;
    }

    public int getJewelSecondaryEffectUnlockRank() {
        return jewelSecondaryEffectUnlockRank;
    }

    public void setJewelSecondaryEffectUnlockRank(int jewelSecondaryEffectUnlockRank) {
        this.jewelSecondaryEffectUnlockRank = jewelSecondaryEffectUnlockRank;
    }
}