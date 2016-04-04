package com.woozam.wdthelper.database;

import android.content.ContentValues;

/**
 * Created by woozam on 2016-02-06.
 */
public abstract class D3DBJob {

    public static final int TYPE_INSERT = 0;
    public static final int TYPE_UPDATE = 1;
    public static final int TYPE_DELETE = 2;
    public static final int TYPE_QUERY = 10;

    private int mType;
    private String mTableName;
    private String mRequestId;

    public D3DBJob(int type, String tableName, String requestId) {
        mType = type;
        mTableName = tableName;
        mRequestId = requestId;
    }

    public int getType() {
        return mType;
    }

    public String getTableName() {
        return mTableName;
    }

    public String getRequestId() {
        return mRequestId;
    }

    public abstract String getNullColumnHack();
    public abstract ContentValues getContentValues();
    public abstract String getWhereClause();
    public abstract String[] getWhereArgs();
    public abstract String[] getColumns();
    public abstract String getGroupBy();
    public abstract String getHaving();
    public abstract String getOrderBy();
    public abstract String getLimit();
}