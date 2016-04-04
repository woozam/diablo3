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
import com.woozam.wdthelper.app.view.TooltipDialog;
import com.woozam.wdthelper.common.ADUtils;
import com.woozam.wdthelper.data.DataManager;
import com.woozam.wdthelper.data.Hero;
import com.woozam.wdthelper.data.Item;
import com.woozam.wdthelper.database.DBItem;

import java.util.LinkedList;

/**
 * Created by woozam on 2016-02-06.
 */
public class FavoriteItemFragment extends Fragment implements AdapterView.OnItemClickListener, DataManager.OnFavoriteItemChangedListener, View.OnClickListener {

    private View mRoot;
    private ListView mListView;
    private FavoriteItemAdapter mFavoriteItemAdapter;
    private LinkedList<DBItem> mItemList;

    private AdView mFavoriteAdView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_favorite_item, container, false);
        mListView = (ListView) mRoot.findViewById(R.id.favorite_item_list_view);
        mFavoriteAdView = new AdView(getContext());
        mFavoriteAdView.setAdUnitId(getString(R.string.ad_unit_id_favorite_2));
        mFavoriteAdView.setAdSize(AdSize.SMART_BANNER);

        mItemList = new LinkedList<>();
        mFavoriteItemAdapter = new FavoriteItemAdapter(getContext(), mItemList, this);
        mListView.setEmptyView(mRoot.findViewById(R.id.favorite_item_empty_view));
        if (ADUtils.USE_AD) {
            mListView.addHeaderView(mFavoriteAdView);
        }
        mListView.setAdapter(mFavoriteItemAdapter);
        mListView.setOnItemClickListener(this);

        DataManager.getInstance().addOnFavoriteItemListener(this);
        loadFavoriteItem();

        return mRoot;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DataManager.getInstance().removeOnFavoriteItemListener(this);
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

    private void loadFavoriteItem() {
        mItemList.clear();
        mItemList.addAll(DataManager.getInstance().getFavoriteItemList());
        mFavoriteItemAdapter.notifyDataSetChanged();
        ADUtils.requestAD(mFavoriteAdView);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int headerViewsCount = mListView.getHeaderViewsCount();
        if (position > headerViewsCount - 1) {
            position -= headerViewsCount;
            DBItem dbItem = mFavoriteItemAdapter.getItem(position);
            Hero hero = new Hero();
            hero.setId(dbItem.getHeroId());
            hero.setName(dbItem.getHeroName());
            Item item = new Item();
            item.setName(dbItem.getName());
            item.setIcon(dbItem.getIcon());
            item.setDisplayColor(dbItem.getColor());
            item.setTooltipParams(dbItem.getTooltipParams());
            new TooltipDialog(getContext()).setItem(dbItem.getBattleTag(), hero, item).show();
        }
    }

    @Override
    public void onAddFavoriteItem(DBItem hero) {
        loadFavoriteItem();
    }

    @Override
    public void onRemoveFavoriteItem(DBItem hero) {
        loadFavoriteItem();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.favorite_item_more:
                showMoreMenu((DBItem) v.getTag(), v);
                break;
        }
    }

    private void showMoreMenu(final DBItem item, View view) {
        PopupMenu menu = new PopupMenu(getContext(), view);
        menu.inflate(R.menu.menu_favorite_item_more);
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_view_profile:
                        ProfileActivity.createInstance(getContext(), item.getBattleTag());
                        return true;
                    case R.id.action_view_hero:
                        HeroActivity.createInstance(getContext(), item.getBattleTag(), item.getHeroId(), item.getHeroName());
                        return true;
                }
                return false;
            }
        });
        menu.show();
    }
}