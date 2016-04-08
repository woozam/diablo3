package com.woozam.wdthelper.data;

/**
 * Created by woozam on 2016-04-06.
 */
public class CalculatorDPSItem {

    public static final int TYPE_PARAGON = 0;
    public static final int TYPE_CALDESANN = 1;
    public static final int TYPE_ATTACK_SPEED = 2;
    public static final int TYPE_CRITICAL_CHANCE = 3;
    public static final int TYPE_CRITICAL_DAMAGE = 4;

    private int mType;
    private float mStat;

    public CalculatorDPSItem(int type, float stat) {
        mType = type;
        mStat = stat;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public String getName() {
        switch (mType) {
            case TYPE_PARAGON:
                return "정복자";
            case TYPE_CALDESANN:
                return "칼데산";
            case TYPE_ATTACK_SPEED:
                return "공격 속도";
            case TYPE_CRITICAL_CHANCE:
                return "극대화 확률";
            case TYPE_CRITICAL_DAMAGE:
                return "극대화 피해";
        }
        return "";
    }

    public float getStat() {
        return mStat;
    }

    public void setStat(float stat) {
        mStat = stat;
    }

    public String getStatString() {
        switch (mType) {
            case TYPE_PARAGON:
            case TYPE_CALDESANN:
                return String.format("%+.0f", mStat);
            case TYPE_ATTACK_SPEED:
                return String.format("%.0f%%", mStat);
            case TYPE_CRITICAL_CHANCE:
                return String.format("%.1f%%", mStat);
            case TYPE_CRITICAL_DAMAGE:
                return String.format("%.0f%%", mStat);
        }
        return "";
    }
}