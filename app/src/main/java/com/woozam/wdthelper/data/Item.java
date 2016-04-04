package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

public class Item extends AbsData {

    private static final long serialVersionUID = -2141686515749017445L;

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("icon")
    private String icon;
    @SerializedName("displayColor")
    private String displayColor;
    @SerializedName("tooltipParams")
    private String tooltipParams;
    @SerializedName("setItemsEquipped")
    private String[] setItemsEquipped;
    @SerializedName("attributes")
    private Attributes attributes;
    @SerializedName("flavorText")
    private String flavorText;
    @SerializedName("gems")
    private Gem[] gems;
    @SerializedName("augmentation")
    private String augmentation;
    @SerializedName("descriptionLoaded")
    private boolean descriptionLoaded = false;
    @SerializedName("armor")
    private Armor armor;
    @SerializedName("dps")
    private DPS dps;
//            "attacksPerSecond": {
//        "min": 1.399999976158142,
//                "max": 1.399999976158142
//    },
    public Item() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDisplayColor() {
        return displayColor;
    }

    public void setDisplayColor(String displayColor) {
        this.displayColor = displayColor;
    }

    public String getTooltipParams() {
        return tooltipParams;
    }

    public void setTooltipParams(String tooltipParams) {
        this.tooltipParams = tooltipParams;
    }

    public String[] getSetItemsEquipped() {
        return setItemsEquipped;
    }

    public void setSetItemsEquipped(String[] setItemsEquipped) {
        this.setItemsEquipped = setItemsEquipped;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public Gem[] getGems() {
        return gems;
    }

    public void setGems(Gem[] gems) {
        this.gems = gems;
    }

    public boolean isDescriptionLoaded() {
        return descriptionLoaded;
    }

    public void setDescriptionLoaded(boolean descriptionLoaded) {
        this.descriptionLoaded = descriptionLoaded;
    }

    public String getAugmentation() {
        return augmentation;
    }

    public void setAugmentation(String augmentation) {
        this.augmentation = augmentation;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public DPS getDps() {
        return dps;
    }

    public void setDps(DPS dps) {
        this.dps = dps;
    }
}