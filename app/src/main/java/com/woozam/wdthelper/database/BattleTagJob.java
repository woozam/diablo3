package com.woozam.wdthelper.database;

import android.content.ContentValues;

import com.woozam.wdthelper.data.BattleTag;

/**
 * Created by woozam on 2016-02-06.
 */
public class BattleTagJob extends D3DBJob {

    private BattleTag mBattleTag;

    public BattleTagJob(BattleTag battleTag, int type, String requestId) {
        super(type, D3DBManager.TABLE_BATTLE_TAG, requestId);
        mBattleTag = battleTag;
    }

    @Override
    public String getNullColumnHack() {
        return null;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(D3DBManager.COLUMN_BATTLE_TAG, mBattleTag.getBattleTag());
        values.put(D3DBManager.COLUMN_SERVER, mBattleTag.getServer().name());
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
                whereClause = D3DBManager.COLUMN_BATTLE_TAG + "=? AND " + D3DBManager.COLUMN_SERVER  + "=?";
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
                whereArgs = new String[]{mBattleTag.getBattleTag(), mBattleTag.getServer().name()};
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