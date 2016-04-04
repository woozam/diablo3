package com.woozam.wdthelper.app.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.woozam.wdthelper.data.Hero;

/**
 * Created by woozam on 2016-02-06.
 */
public class FavoriteHeroAdapter extends HeroAdapter {

    private View.OnClickListener mOnClickListener;

    public FavoriteHeroAdapter(Context context, Hero[] heroes, View.OnClickListener onClickListener) {
        super(context, heroes);
        mOnClickListener = onClickListener;
    }

    @Override
    protected HeroViewHolder getViewHolder(LayoutInflater inflater) {
        return new FavoriteHeroViewHolder(inflater);
    }

    private class FavoriteHeroViewHolder extends HeroViewHolder {

        public FavoriteHeroViewHolder(LayoutInflater inflater) {
            super(inflater);
        }

        public void setHero(Hero hero) {
            super.setHero(hero);
            mClass.setText(hero.getBattleTag().toString());
            mMore.setVisibility(View.VISIBLE);
            mMore.setOnClickListener(mOnClickListener);
            mMore.setTag(hero);
            mMore.setFocusable(false);
        }
    }
}