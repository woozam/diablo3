package com.woozam.wdthelper.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.woozam.wdthelper.R;
import com.woozam.wdthelper.common.ADUtils;
import com.woozam.wdthelper.data.DataManager;
import com.woozam.wdthelper.data.Hero;
import com.woozam.wdthelper.database.DBHero;

import java.util.LinkedList;

/**
 * Created by woozam on 2016-02-06.
 */
public class FavoriteHeroFragment extends Fragment implements AdapterView.OnItemClickListener, DataManager.OnFavoriteHeroChangedListener, View.OnClickListener {

    private View mRoot;
    private ListView mListView;
    private FavoriteHeroAdapter mHeroAdapter;
    private Hero[] mHeroList;

    private AdView mFavoriteAdView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_favorite_hero, container, false);
        mListView = (ListView) mRoot.findViewById(R.id.favorite_hero_list_view);
        mFavoriteAdView = new AdView(getContext());
        mFavoriteAdView.setAdUnitId(getString(R.string.ad_unit_id_favorite_1));
        mFavoriteAdView.setAdSize(AdSize.SMART_BANNER);

        mHeroList = new Hero[0];
        mHeroAdapter = new FavoriteHeroAdapter(getContext(), mHeroList, this);
        mListView.setEmptyView(mRoot.findViewById(R.id.favorite_hero_empty_view));
        if (ADUtils.USE_AD) {
            mListView.addHeaderView(mFavoriteAdView);
        }
        mListView.setAdapter(mHeroAdapter);
        mListView.setOnItemClickListener(this);

        DataManager.getInstance().addOnFavoriteHeroListener(this);
        loadHeroFavorite();

        return mRoot;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DataManager.getInstance().removeOnFavoriteHeroListener(this);
        mFavoriteAdView.destroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mFavoriteAdView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mFavoriteAdView.pause();
    }

    private void loadHeroFavorite() {
        LinkedList<DBHero> heroFavoriteList = DataManager.getInstance().getFavoriteHeroList();
        mHeroList = new Hero[heroFavoriteList.size()];
        for (int i = 0; i < heroFavoriteList.size(); i++) {
            DBHero heroItem = heroFavoriteList.get(i);
            Hero hero = new Hero();
            hero.setBattleTag(heroItem.getBattleTag());
            hero.setId(heroItem.getHeroId());
            hero.setName(heroItem.getHeroName());
            hero.setHeroClass(heroItem.getHeroClass());
            hero.setGender(heroItem.getHeroGender());
            mHeroList[i] = hero;
        }
        mHeroAdapter.setHeros(mHeroList);
        ADUtils.requestAD(mFavoriteAdView);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int headerViewsCount = mListView.getHeaderViewsCount();
        if (position > headerViewsCount - 1) {
            position -= headerViewsCount;
            Hero hero = mHeroAdapter.getItem(position);
            HeroActivity.createInstance(getContext(), hero.getBattleTag(), hero.getId(), hero.getName());
        }
    }

    @Override
    public void onAddFavoriteHero(DBHero hero) {
        loadHeroFavorite();
    }

    @Override
    public void onRemoveFavoriteHero(DBHero hero) {
        loadHeroFavorite();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hero_list_row_more:
                showMoreMenu((Hero) v.getTag(), v);
                break;
        }
    }

    private void showMoreMenu(final Hero hero, View view) {
        PopupMenu menu = new PopupMenu(getContext(), view);
        menu.inflate(R.menu.menu_favorite_hero_more);
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_view_profile:
                        ProfileActivity.createInstance(getContext(), hero.getBattleTag());
                        return true;
                }
                return false;
            }
        });
        menu.show();
    }
}