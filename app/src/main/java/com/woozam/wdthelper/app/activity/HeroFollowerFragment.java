package com.woozam.wdthelper.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.woozam.wdthelper.R;
import com.woozam.wdthelper.app.view.FollowerLayout;
import com.woozam.wdthelper.common.ScreenShotTask;
import com.woozam.wdthelper.data.Hero;

/**
 * Created by woozam on 2016-02-13.
 */
public class HeroFollowerFragment extends AbsHeroFragment implements View.OnClickListener {

    private View mRoot;
    private FollowerLayout mEnchantress;
    private FollowerLayout mScoundrel;
    private FollowerLayout mTemplar;
    private View mEnchantressScreenShot;
    private View mScoundrelScreenShot;
    private View mTemplarScreenShot;

    public HeroFollowerFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_hero_follower, container, false);
        mEnchantress = (FollowerLayout) mRoot.findViewById(R.id.hero_follower_enchantress_layout);
        mScoundrel = (FollowerLayout) mRoot.findViewById(R.id.hero_follower_scoundrel_layout);
        mTemplar = (FollowerLayout) mRoot.findViewById(R.id.hero_follower_templar_layout);
        mEnchantressScreenShot = mRoot.findViewById(R.id.hero_follower_enchantress_screen_shot);
        mScoundrelScreenShot = mRoot.findViewById(R.id.hero_follower_scoundrel_screen_shot);
        mTemplarScreenShot = mRoot.findViewById(R.id.hero_follower_templar_screen_shot);
        mEnchantressScreenShot.setOnClickListener(this);
        mScoundrelScreenShot.setOnClickListener(this);
        mTemplarScreenShot.setOnClickListener(this);
        setHero();
        return mRoot;
    }

    @Override
    public void setHero(Hero hero) {
        super.setHero(hero);
        setHero();
    }

    private void setHero() {
        try {
            if (mRoot != null && mHero != null) {
                mEnchantress.setFollower(mBattleTag, mHero, mHero.getFollowers().getEnchantress());
                mScoundrel.setFollower(mBattleTag, mHero, mHero.getFollowers().getScoundrel());
                mTemplar.setFollower(mBattleTag, mHero, mHero.getFollowers().getTemplar());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mEnchantressScreenShot) {
            screenShot(mEnchantress, getString(R.string.enchantress));
        } else if (v == mScoundrelScreenShot) {
            screenShot(mScoundrel, getString(R.string.scoundrel));
        } else if (v == mTemplarScreenShot) {
            screenShot(mTemplar, getString(R.string.templar));
        }
    }

    private void screenShot(View view, String name) {
        new ScreenShotTask(view, mBattleTag.getBattleTag() + "_" + mHero.getName() + "_" + "follower_" + name + "_" + System.currentTimeMillis(), "hero items").execute();
    }
}