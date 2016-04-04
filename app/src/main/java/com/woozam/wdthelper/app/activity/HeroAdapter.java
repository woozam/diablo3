package com.woozam.wdthelper.app.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.woozam.wdthelper.R;
import com.woozam.wdthelper.data.Hero;
import com.woozam.wdthelper.data.HeroClass;

/**
 * Created by woozam on 2016-01-31.
 */
public class HeroAdapter extends D3BaseAdapter {

    private Hero[] mHeroes;

    public HeroAdapter(Context context, Hero[] heroes) {
        super(context);
        mHeroes = heroes;
    }

    public void setHeros(Hero[] heroes) {
        mHeroes = heroes;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mHeroes == null ? 0 : mHeroes.length;
    }

    @Override
    public Hero getItem(int position) {
        return mHeroes[position];
    }

    @Override
    public long getItemId(int position) {
        return mHeroes[position].getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            HeroViewHolder viewHolder = getViewHolder(mInflater);
            convertView = viewHolder.mRoot;
            convertView.setTag(viewHolder);
        }

        Hero hero = getItem(position);
        HeroViewHolder viewHolder = (HeroViewHolder) convertView.getTag();
        viewHolder.setHero(hero);
        viewHolder.mRoot.setBackgroundColor(position % 2 == 1 ? 0xff12110f : 0xff171614);

        return convertView;
    }

    protected HeroViewHolder getViewHolder(LayoutInflater inflater) {
        return new HeroViewHolder(inflater);
    }

    public class HeroViewHolder extends BaseViewHolder {

        protected ImageView mImage;
        protected TextView mName;
        protected TextView mClass;
        protected View mMore;

        public HeroViewHolder(LayoutInflater inflater) {
            super(inflater, R.layout.row_hero);
            mImage = (ImageView) findViewById(R.id.hero_list_row_image);
            mName = (TextView) findViewById(R.id.hero_list_row_name);
            mClass = (TextView) findViewById(R.id.hero_list_row_class);
            mMore = findViewById(R.id.hero_list_row_more);
        }

        public void setHero(Hero hero) {
            mImage.setImageResource(hero.getHeroLargeImage());
            mName.setText(hero.getName());
            mName.setCompoundDrawablesWithIntrinsicBounds(0, 0, hero.isSeasonal() ? R.mipmap.img_seasonal_leaf : 0, 0);
            mName.setTextColor(hero.isHardcore() ? 0xffa20101 : 0xffad835a);
            mClass.setText(String.format("%s (%d)", HeroClass.getHeroClass(hero.getHeroClass(), hero.getGender()).toString(), hero.getLevel()));
        }
    }
}