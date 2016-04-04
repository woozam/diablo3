package com.woozam.wdthelper.data;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.woozam.wdthelper.analystics.Analystics;
import com.woozam.wdthelper.database.D3DBManager;
import com.woozam.wdthelper.database.DBHero;
import com.woozam.wdthelper.database.DBItem;
import com.woozam.wdthelper.network.D3URLEncoder;
import com.woozam.wdthelper.network.GsonRequest;
import com.woozam.wdthelper.network.VolleySingleton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;

public class DataManager {

    public static final String API_KET = "3facmd76wg3z8ur5amnpsu5bw9q5tp5y";
    public static final String PROFILE = "https://%s.api.battle.net/d3/profile/";
    public static final String ITEM = "https://%s.api.battle.net/d3/data/item/";
    public static final String ICON_ITEM = "http://media.blizzard.com/d3/icons/items/large/";
    public static final String ICON_SKILL = "http://media.blizzard.com/d3/icons/skills/64/";
    public static final String TOOLTIP = "https://%s.battle.net/d3/%s/tooltip/";
    public static final String RANKING = "https://%s.battle.net/d3/%s/rankings/%s/%s/%s";

    public static final int TIMEOUT_MS = 8000;
    public static final int MAX_RETRIES = 3;
    public static final float BACKOFF_MULT = 1f;

    private static DataManager sInstance;

    public static DataManager getInstance() {
        if (sInstance == null) {
            sInstance = new DataManager();
        }
        return sInstance;
    }

    private ArrayList<OnBattleTagChangedListener> mOnBattleTagChangedListenerList = new ArrayList<>();
    private ArrayList<OnFavoriteHeroChangedListener> mOnFavoriteHeroChangedListener = new ArrayList<>();
    private ArrayList<OnFavoriteItemChangedListener> mOnFavoriteItemChangedListener = new ArrayList<>();
    private LinkedList<BattleTag> mBattleTagList = new LinkedList<>();
    private LinkedList<DBHero> mFavoriteHeroList = new LinkedList<>();
    private LinkedList<DBItem> mFavoriteItemList = new LinkedList<>();
    private RankingParameter mLastRankingParameter;

    public DataManager() {
        mBattleTagList.addAll(D3DBManager.getInstance().getBattleTagList());
        BattleTag addBattleTag = new BattleTag();
        addBattleTag.setAdd(true);
        mBattleTagList.add(addBattleTag);
        mFavoriteHeroList.addAll(D3DBManager.getInstance().getHeroList());
        mFavoriteItemList.addAll(D3DBManager.getInstance().getItemList());
        mLastRankingParameter = RankingParameter.load();
    }

    public void getProfile(final Context context, Server server, String battleTag, Listener<Profile> listener, ErrorListener errorListener, Object tag) {
        String url = String.format(PROFILE, server);
        url += D3URLEncoder.encode(battleTag, "utf-8") + "/?";
        url += "locale=" + Locale.getDefault().toString();
        url += "&apikey=" + API_KET;
        VolleySingleton.getInstance(context).addToRequestQueue(new GsonRequest<>(url, Profile.class, null, listener, errorListener).setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, MAX_RETRIES, BACKOFF_MULT)).setTag(tag));
    }

    public void getHero(final Context context, Server server, String battleTag, long heroId, Listener<Hero> listener, ErrorListener errorListener, Object tag) {
        String url = String.format(PROFILE, server);
        url += D3URLEncoder.encode(battleTag, "utf-8") + "/hero/";
        url += heroId + "?";
        url += "locale=" + Locale.getDefault().toString();
        url += "&apikey=" + API_KET;
        VolleySingleton.getInstance(context).addToRequestQueue(new GsonRequest<>(url, Hero.class, null, listener, errorListener).setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, MAX_RETRIES, BACKOFF_MULT)).setTag(tag));
    }

    public void getTooltip(final Context context, Server server, String tooltipParams, Listener<String> listener, ErrorListener errorListener, Object tag) {
        String url = String.format(TOOLTIP, server, Locale.getDefault().getLanguage());
        url += tooltipParams;
        VolleySingleton.getInstance(context).addToRequestQueue(new StringRequest(Request.Method.GET, url, listener, errorListener).setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, MAX_RETRIES, BACKOFF_MULT)).setTag(tag));
    }

    public void getItem(final Context context, Server server, String tooltipParams, Listener<Item> listener, ErrorListener errorListener, Object tag) {
        String url = String.format(ITEM, server);
        url += tooltipParams.split("/")[1] + "?";
        url += "locale=" + Locale.getDefault().toString();
        url += "&apikey=" + API_KET;
        VolleySingleton.getInstance(context).addToRequestQueue(new GsonRequest<>(url, Item.class, null, listener, errorListener).setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, MAX_RETRIES, BACKOFF_MULT)).setTag(tag));
    }

    public void getRanking(final Context context, RankingParameter params, Listener<String> listener, ErrorListener errorListener, Object tag) {
        String url = String.format(RANKING, params.server, Locale.getDefault().getLanguage(), params.isSeason ? "season" : "era", params.season, params.type.toString(params.isHardcore));
        VolleySingleton.getInstance(context).addToRequestQueue(new StringRequest(Request.Method.GET, url, listener, errorListener).setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, MAX_RETRIES, BACKOFF_MULT)).setTag(tag));
        setLastRankingParameter(params);
    }

    public void addBattleTag(BattleTag battleTag) {
        int index;
        if (!mBattleTagList.contains(battleTag)) {
            mBattleTagList.add(mBattleTagList.size() - 1, battleTag);
            index = mBattleTagList.size() - 2;
            D3DBManager.getInstance().insertBattleTag(battleTag, null);
        } else {
            index = mBattleTagList.indexOf(battleTag);
        }
        for (OnBattleTagChangedListener onBattleTagChangedListener : mOnBattleTagChangedListenerList) {
            onBattleTagChangedListener.onAddBattleTag(index);
        }
        Analystics.event("BattleTag", "Add");
    }

    public void removeBattleTag(BattleTag battleTag) {
        if (mBattleTagList.contains(battleTag)) {
            mBattleTagList.remove(battleTag);
            D3DBManager.getInstance().deleteBattleTag(battleTag, null);
            for (OnBattleTagChangedListener onBattleTagChangedListener : mOnBattleTagChangedListenerList) {
                onBattleTagChangedListener.onRemoveBattleTag(battleTag);
            }
        }
        Analystics.event("BattleTag", "Remove");
    }

    public boolean containBattleTag(BattleTag battleTag) {
        return mBattleTagList.contains(battleTag);
    }

    public LinkedList<BattleTag> getBattleTagList() {
        return (LinkedList<BattleTag>) mBattleTagList.clone();
    }

    public void addFavoriteHero(BattleTag battleTag, long heroId, String heroName, String heroClass, int heroGender) {
        DBHero hero = new DBHero();
        hero.setBattleTag(battleTag);
        hero.setHeroId(heroId);
        hero.setHeroName(heroName);
        hero.setHeroClass(heroClass);
        if (!mFavoriteHeroList.contains(hero)) {
            mFavoriteHeroList.add(hero);
            D3DBManager.getInstance().insertHero(battleTag, heroId, heroName, heroClass, heroGender, null);
            for (OnFavoriteHeroChangedListener onFavoriteHeroChangedListener : mOnFavoriteHeroChangedListener) {
                onFavoriteHeroChangedListener.onAddFavoriteHero(hero);
            }
        }
        Analystics.event("Favorite", "Add Hero");
    }

    public void removeHeroFavorite(BattleTag battleTag, long heroId) {
        DBHero hero = new DBHero();
        hero.setBattleTag(battleTag);
        hero.setHeroId(heroId);
        if (mFavoriteHeroList.contains(hero)) {
            mFavoriteHeroList.remove(hero);
            D3DBManager.getInstance().deleteHero(battleTag, heroId, null);
            for (OnFavoriteHeroChangedListener onFavoriteHeroChangedListener : mOnFavoriteHeroChangedListener) {
                onFavoriteHeroChangedListener.onRemoveFavoriteHero(hero);
            }
        }
        Analystics.event("Favorite", "Remove Hero");
    }

    public boolean containsFavoriteHero(BattleTag battleTag, long heroId) {
        DBHero hero = new DBHero();
        hero.setBattleTag(battleTag);
        hero.setHeroId(heroId);
        return containsFavoriteHero(hero);
    }

    public boolean containsFavoriteHero(DBHero hero) {
        return mFavoriteHeroList.contains(hero);
    }

    public LinkedList<DBHero> getFavoriteHeroList() {
        return (LinkedList<DBHero>) mFavoriteHeroList.clone();
    }

    public void addFavoriteItem(String tooltipParams, String icon, String name, String color, BattleTag battleTag, long heroId, String heroName) {
        DBItem item = new DBItem();
        item.setBattleTag(battleTag);
        item.setTooltipParams(tooltipParams);
        item.setIcon(icon);
        item.setName(name);
        item.setColor(color);
        item.setHeroId(heroId);
        item.setHeroName(heroName);
        if (!mFavoriteItemList.contains(item)) {
            mFavoriteItemList.add(item);
            D3DBManager.getInstance().insertItem(tooltipParams, icon, name, color, battleTag, heroId, heroName, null);
            for (OnFavoriteItemChangedListener onFavoriteItemChangedListener : mOnFavoriteItemChangedListener) {
                onFavoriteItemChangedListener.onAddFavoriteItem(item);
            }
        }
        Analystics.event("Favorite", "Add Item");
    }

    public void removeFavoriteItem(BattleTag battleTag, String tooltipParams) {
        DBItem item = new DBItem();
        item.setBattleTag(battleTag);
        item.setTooltipParams(tooltipParams);
        if (mFavoriteItemList.contains(item)) {
            mFavoriteItemList.remove(item);
            D3DBManager.getInstance().deleteItem(tooltipParams, battleTag, null);
            for (OnFavoriteItemChangedListener onFavoriteItemChangedListener : mOnFavoriteItemChangedListener) {
                onFavoriteItemChangedListener.onRemoveFavoriteItem(item);
            }
        }
        Analystics.event("Favorite", "Remove Item");
    }

    public boolean containsFavoriteItem(BattleTag battleTag, String tooltipParams) {
        DBItem item = new DBItem();
        item.setBattleTag(battleTag);
        item.setTooltipParams(tooltipParams);
        return containsFavoriteItem(item);
    }

    public boolean containsFavoriteItem(DBItem item) {
        return mFavoriteItemList.contains(item);
    }

    public LinkedList<DBItem> getFavoriteItemList() {
        return (LinkedList<DBItem>) mFavoriteItemList.clone();
    }

    public RankingParameter getLastRankingParameter() {
        return mLastRankingParameter;
    }

    public void setLastRankingParameter(RankingParameter lastRankingParameter) {
        this.mLastRankingParameter = lastRankingParameter;
        RankingParameter.save(mLastRankingParameter);
    }

    public interface OnBattleTagChangedListener {
        void onAddBattleTag(int selection);

        void onRemoveBattleTag(BattleTag battleTag);
    }

    public void addOnAddBattleTagListener(OnBattleTagChangedListener onBattleTagChangedListener) {
        mOnBattleTagChangedListenerList.add(onBattleTagChangedListener);
    }

    public void removeOnAddBattleTagListener(OnBattleTagChangedListener onBattleTagChangedListener) {
        mOnBattleTagChangedListenerList.remove(onBattleTagChangedListener);
    }

    public interface OnFavoriteHeroChangedListener {
        void onAddFavoriteHero(DBHero hero);

        void onRemoveFavoriteHero(DBHero hero);
    }

    public void addOnFavoriteHeroListener(OnFavoriteHeroChangedListener onHeroFavoriteChangedListener) {
        mOnFavoriteHeroChangedListener.add(onHeroFavoriteChangedListener);
    }

    public void removeOnFavoriteHeroListener(OnFavoriteHeroChangedListener onHeroFavoriteChangedListener) {
        mOnFavoriteHeroChangedListener.remove(onHeroFavoriteChangedListener);
    }

    public interface OnFavoriteItemChangedListener {
        void onAddFavoriteItem(DBItem hero);

        void onRemoveFavoriteItem(DBItem hero);
    }

    public void addOnFavoriteItemListener(OnFavoriteItemChangedListener onItemFavoriteChangedListener) {
        mOnFavoriteItemChangedListener.add(onItemFavoriteChangedListener);
    }

    public void removeOnFavoriteItemListener(OnFavoriteItemChangedListener onItemFavoriteChangedListener) {
        mOnFavoriteItemChangedListener.remove(onItemFavoriteChangedListener);
    }
}