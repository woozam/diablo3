package com.woozam.wdthelper.app.activity;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by woozam on 2016-01-31.
 */
public abstract class BaseViewHolder {

    protected View mRoot;

    public BaseViewHolder(LayoutInflater inflater, int layoutId) {
        mRoot = inflater.inflate(layoutId, null);
    }

    protected View findViewById(int id) {
        return mRoot.findViewById(id);
    }
}