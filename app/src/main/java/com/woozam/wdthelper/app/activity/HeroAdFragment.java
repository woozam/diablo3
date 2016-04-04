package com.woozam.wdthelper.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdView;
import com.woozam.wdthelper.R;
import com.woozam.wdthelper.common.ADUtils;
import com.woozam.wdthelper.data.Hero;

/**
 * Created by woozam on 2016-02-05.
 */
public class HeroAdFragment extends AbsHeroFragment {

    private AdView mAD1;
    private boolean mLoaded = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hero_ad, container, false);
        mAD1 = (AdView) view.findViewById(R.id.ad_view_1);
        setHero();
        return view;
    }

    @Override
    public void setHero(Hero hero) {
        super.setHero(hero);
        setHero();
    }

    private void setHero() {
        if (mAD1 != null && !mLoaded) {
            ADUtils.requestAD(mAD1);
            mLoaded = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAD1.destroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAD1.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mAD1.pause();
    }
}