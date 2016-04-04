package com.woozam.wdthelper.database;

import android.content.ContentValues;

import com.woozam.wdthelper.data.BattleTag;

/**
 * Created by woozam on 2016-02-06.
 */
public class HeroJob extends D3DBJob {

    private BattleTag mBattleTag;
    private long mHeroId;
    private String mHeroName;
    private String mHeroClass;
    private int mHeroGender;

    public HeroJob(BattleTag battleTag, long heroId, String heroName, String heroClass, int heroGender, int type, String requestId) {
        super(type, D3DBManager.TABLE_HERO, requestId);
        mBattleTag = battleTag;
        mHeroId = heroId;
        mHeroName = heroName;
        mHeroClass = heroClass;
        mHeroGender = heroGender;
    }

    @Override
    public String getNullColumnHack() {
        return null;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(D3DBManager.COLUMN_HERO_BATTLE_TAG, mBattleTag.getBattleTag());
        values.put(D3DBManager.COLUMN_HERO_ID, mHeroId);
        values.put(D3DBManager.COLUMN_HERO_NAME, mHeroName);
        values.put(D3DBManager.COLUMN_HERO_CLASS, mHeroClass);
        values.put(D3DBManager.COLUMN_HERO_GENDER, mHeroGender);
        values.put(D3DBManager.COLUMN_HERO_SERVER, mBattleTag.getServer().name());
        return values;
    }

    @Override
    public String getWhereClause() {
        String whereClause = null;
        switch (getType()) {
            case TYPE_INSERT:
                break;
            case TYPE_UPDATE:
                break;
            case TYPE_QUERY:
                break;
            case TYPE_DELETE:
                whereClause = D3DBManager.COLUMN_HERO_BATTLE_TAG + "=? AND " + D3DBManager.COLUMN_HERO_ID + "=? AND " + D3DBManager.COLUMN_HERO_SERVER  + "=?";
                break;

        }
        return whereClause;
    }

    @Override
    public String[] getWhereArgs() {
        String[] whereArgs = null;
        switch (getType()) {
            case TYPE_INSERT:
                break;
            case TYPE_UPDATE:
                break;
            case TYPE_QUERY:
                break;
            case TYPE_DELETE:
                whereArgs = new String[]{mBattleTag.getBattleTag(), String.valueOf(mHeroId), mBattleTag.getServer().name()};
                break;

        }
        return whereArgs;
    }

    @Override
    public String[] getColumns() {
        return null;
    }

    @Override
    public String getGroupBy() {
        return null;
    }

    @Override
    public String getHaving() {
        return null;
    }

    @Override
    public String getOrderBy() {
        return "_id ASC";
    }

    @Override
    public String getLimit() {
        return null;
    }
}