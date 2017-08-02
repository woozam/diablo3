package com.woozam.wdthelper.data;

import android.content.Context;

import com.woozam.wdthelper.D3Application;
import com.woozam.wdthelper.R;

public enum HeroClass {

    BB_M, BB_F, WZ_M, WZ_F, DH_M, DH_F, MK_M, MK_F, WD_M, WD_F, CR_M, CR_F, NE_M, NE_F;

    public static final String BB = "barbarian";
    public static final String WZ = "wizard";
    public static final String DH = "demon-hunter";
    public static final String MK = "monk";
    public static final String WD = "witch-doctor";
    public static final String CR = "crusader";
    public static final String NE = "necromancer";
    public static final int MALE = 0;
    public static final int FEMALE = 1;

    public static HeroClass getHeroClass(String heroClassString, int gender) {
        HeroClass heroClass = null;
        if (heroClassString.equals(BB)) {
            if (gender == MALE) {
                heroClass = BB_M;
            } else if (gender == FEMALE) {
                heroClass = BB_F;
            }
        } else if (heroClassString.equals(WZ)) {
            if (gender == MALE) {
                heroClass = WZ_M;
            } else if (gender == FEMALE) {
                heroClass = WZ_F;
            }
        } else if (heroClassString.equals(DH)) {
            if (gender == MALE) {
                heroClass = DH_M;
            } else if (gender == FEMALE) {
                heroClass = DH_F;
            }
        } else if (heroClassString.equals(MK)) {
            if (gender == MALE) {
                heroClass = MK_M;
            } else if (gender == FEMALE) {
                heroClass = MK_F;
            }
        } else if (heroClassString.equals(WD)) {
            if (gender == MALE) {
                heroClass = WD_M;
            } else if (gender == FEMALE) {
                heroClass = WD_F;
            }
        } else if (heroClassString.equals(CR)) {
            if (gender == MALE) {
                heroClass = CR_M;
            } else if (gender == FEMALE) {
                heroClass = CR_F;
            }
        } else if (heroClassString.equals(NE)) {
            if (gender == MALE) {
                heroClass = NE_M;
            } else if (gender == FEMALE) {
                heroClass = NE_F;
            }
        }
        return heroClass;
    }

    @Override
    public String toString() {
        Context context = D3Application.getContext();
        switch (this) {
            case BB_F:
            case BB_M:
                return context.getString(R.string.barbarian);
            case CR_F:
            case CR_M:
                return context.getString(R.string.crusader);
            case DH_F:
            case DH_M:
                return context.getString(R.string.demon_hunter);
            case MK_F:
            case MK_M:
                return context.getString(R.string.monk);
            case WD_F:
            case WD_M:
                return context.getString(R.string.witch_doctor);
            case WZ_F:
            case WZ_M:
                return context.getString(R.string.wizard);
            case NE_F:
            case NE_M:
                return context.getString(R.string.necromancer);
            default:
                return "";
        }
    }
}