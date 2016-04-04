package com.woozam.wdthelper.database;

import android.content.ContentValues;

import com.woozam.wdthelper.data.BattleTag;

/**
 * Created by woozam on 2016-02-06.
 */
public class ItemJob extends D3DBJob {

    private String mTooltipParams;
    private String mIcon;
    private String mName;
    private String mColor;
    private BattleTag mBattleTag;
    private long mHeroId;
    private String mHeroName;

    public ItemJob(String tooltipParams, String icon, String name, String color, BattleTag battleTag, long heroId, String heroName, int type, String requestId) {
        super(type, D3DBManager.TABLE_ITEM, requestId);
        mTooltipParams = tooltipParams;
        mIcon = icon;
        mName = name;
        mColor = color;
        mBattleTag = battleTag;
        mHeroId = heroId;
        mHeroName = heroName;
    }

    @Override
    public String getNullColumnHack() {
        return null;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(D3DBManager.COLUMN_ITEM_TOOLTIP_PARAMS, mTooltipParams);
        values.put(D3DBManager.COLUMN_ITEM_ICON, mIcon);
        values.put(D3DBManager.COLUMN_ITEM_NAME, mName);
        values.put(D3DBManager.COLUMN_ITEM_COLOR, mColor);
        values.put(D3DBManager.COLUMN_ITEM_BATTLE_TAG, mBattleTag.getBattleTag());
        values.put(D3DBManager.COLUMN_ITEM_HERO_ID, mHeroId);
        values.put(D3DBManager.COLUMN_ITEM_HERO_NAME, mHeroName);
        values.put(D3DBManager.COLUMN_ITEM_SERVER, mBattleTag.getServer().name());
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
                whereClause = D3DBManager.COLUMN_ITEM_TOOLTIP_PARAMS + "=? AND " + D3DBManager.COLUMN_ITEM_SERVER  + "=?";
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
                whereArgs = new String[]{mTooltipParams, mBattleTag.getServer().name()};
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