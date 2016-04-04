package com.woozam.wdthelper.app.activity;

import android.support.v4.app.Fragment;

import com.woozam.wdthelper.data.BattleTag;
import com.woozam.wdthelper.data.Hero;

/**
 * Created by woozam on 2016-02-01.
 */
public abstract class AbsHeroFragment extends Fragment {

    protected BattleTag mBattleTag;
    protected Hero mHero;

    public AbsHeroFragment() {
        super();
    }

    public void setBattleTag(BattleTag battleTag) {
        mBattleTag = battleTag;
    }

    public void setHero(Hero hero) {
        mHero = hero;
    }
}