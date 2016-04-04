package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

public class Items extends AbsData {

    private static final long serialVersionUID = -6741412017383284958L;

    @SerializedName("head")
    private Item head;
    @SerializedName("torso")
    private Item torso;
    @SerializedName("feet")
    private Item feet;
    @SerializedName("hands")
    private Item hands;
    @SerializedName("shoulders")
    private Item shoulders;
    @SerializedName("legs")
    private Item legs;
    @SerializedName("bracers")
    private Item bracers;
    @SerializedName("mainHand")
    private Item mainHand;
    @SerializedName("offHand")
    private Item offHand;
    @SerializedName("waist")
    private Item waist;
    @SerializedName("rightFinger")
    private Item rightFinger;
    @SerializedName("leftFinger")
    private Item leftFinger;
    @SerializedName("neck")
    private Item neck;
    @SerializedName("special")
    private Item special;

    public Items() {
        super();
    }

    public Item getNeck() {
        return neck;
    }

    public void setNeck(Item neck) {
        this.neck = neck;
    }

    public Item getHead() {
        return head;
    }

    public void setHead(Item head) {
        this.head = head;
    }

    public Item getTorso() {
        return torso;
    }

    public void setTorso(Item torso) {
        this.torso = torso;
    }

    public Item getFeet() {
        return feet;
    }

    public void setFeet(Item feet) {
        this.feet = feet;
    }

    public Item getHands() {
        return hands;
    }

    public void setHands(Item hands) {
        this.hands = hands;
    }

    public Item getShoulders() {
        return shoulders;
    }

    public void setShoulders(Item shoulders) {
        this.shoulders = shoulders;
    }

    public Item getLegs() {
        return legs;
    }

    public void setLegs(Item legs) {
        this.legs = legs;
    }

    public Item getBracers() {
        return bracers;
    }

    public void setBracers(Item bracers) {
        this.bracers = bracers;
    }

    public Item getMainHand() {
        return mainHand;
    }

    public void setMainHand(Item mainHand) {
        this.mainHand = mainHand;
    }

    public Item getOffHand() {
        return offHand;
    }

    public void setOffHand(Item offHand) {
        this.offHand = offHand;
    }

    public Item getWaist() {
        return waist;
    }

    public void setWaist(Item waist) {
        this.waist = waist;
    }

    public Item getRightFinger() {
        return rightFinger;
    }

    public void setRightFinger(Item rightFinger) {
        this.rightFinger = rightFinger;
    }

    public Item getLeftFinger() {
        return leftFinger;
    }

    public void setLeftFinger(Item leftFinger) {
        this.leftFinger = leftFinger;
    }

    public Item getSpecial() {
        return special;
    }

    public void setSpecial(Item special) {
        this.special = special;
    }

    public String setItem(Item item) {
        String id = item.getId();
        if (head != null && head.getId() != null && !head.isDescriptionLoaded() && head.getId().equals(id)) {
            head = item;
            return "head";
        } else if (torso != null && torso.getId() != null && !torso.isDescriptionLoaded() && torso.getId().equals(id)) {
            torso = item;
            return "torso";
        } else if (feet != null && feet.getId() != null && !feet.isDescriptionLoaded() && feet.getId().equals(id)) {
            feet = item;
            return "feet";
        } else if (hands != null && hands.getId() != null && !hands.isDescriptionLoaded() && hands.getId().equals(id)) {
            hands = item;
            return "hands";
        } else if (shoulders != null && shoulders.getId() != null && !shoulders.isDescriptionLoaded() && shoulders.getId().equals(id)) {
            shoulders = item;
            return "shoulders";
        } else if (legs != null && legs.getId() != null && !legs.isDescriptionLoaded() && legs.getId().equals(id)) {
            legs = item;
            return "legs";
        } else if (bracers != null && bracers.getId() != null && !bracers.isDescriptionLoaded() && bracers.getId().equals(id)) {
            bracers = item;
            return "bracers";
        } else if (mainHand != null && mainHand.getId() != null && !mainHand.isDescriptionLoaded() && mainHand.getId().equals(id)) {
            mainHand = item;
            return "mainHand";
        } else if (offHand != null && offHand.getId() != null && !offHand.isDescriptionLoaded() && offHand.getId().equals(id)) {
            offHand = item;
            return "offHand";
        } else if (waist != null && waist.getId() != null && !waist.isDescriptionLoaded() && waist.getId().equals(id)) {
            waist = item;
            return "waist";
        } else if (rightFinger != null && rightFinger.getId() != null && !rightFinger.isDescriptionLoaded() && rightFinger.getId().equals(id)) {
            rightFinger = item;
            return "rightFinger";
        } else if (leftFinger != null && leftFinger.getId() != null && !leftFinger.isDescriptionLoaded() && leftFinger.getId().equals(id)) {
            leftFinger = item;
            return "leftFinger";
        } else if (neck != null && neck.getId() != null && !neck.isDescriptionLoaded() && neck.getId().equals(id)) {
            neck = item;
            return "neck";
        } else if (special != null && special.getId() != null && !special.isDescriptionLoaded() && special.getId().equals(id)) {
            special = item;
            return "special";
        } else {
            return "";
        }
    }
}