package com.woozam.wdthelper.app.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

/**
 * Created by woozam on 2016-01-31.
 */
public abstract class D3BaseAdapter extends BaseAdapter {

    protected Context mContext;
    protected LayoutInflater mInflater;

    protected View mLoadingFooterView;
    protected boolean mLoadingFooterAdded = false;

    public D3BaseAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        //        mLoadingFooterView = mInflater.inflate(R.layout.loading_footer_view, null);
    }

    public void destroy() {
    }

    public static class RHViewHolder {

        public final View mRoot;

        public RHViewHolder(LayoutInflater inflater, int layoutId) {
            mRoot = inflater.inflate(layoutId, null);
        }

        public RHViewHolder(View root) {
            mRoot = root;
        }

        public View findViewById(int id) {
            return mRoot.findViewById(id);
        }
    }

    public void addLoadingFooter() {
        if (!mLoadingFooterAdded) {
            mLoadingFooterAdded = true;
            notifyDataSetChanged();
        }
    }

    public void removeLoadingFooter() {
        if (mLoadingFooterAdded) {
            mLoadingFooterAdded = false;
            notifyDataSetChanged();
        }
    }

    public boolean isLoading() {
        return mLoadingFooterAdded;
    }
}