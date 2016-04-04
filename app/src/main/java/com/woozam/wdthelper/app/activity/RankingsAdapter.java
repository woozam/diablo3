package com.woozam.wdthelper.app.activity;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.woozam.wdthelper.R;
import com.woozam.wdthelper.data.RankItem;
import com.woozam.wdthelper.network.VolleySingleton;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by woozam on 2016-02-13.
 */
public class RankingsAdapter extends D3BaseAdapter implements Filterable {

    private ArrayList<RankItem> mFilteredRankItemList;
    private ArrayList<RankItem> mRankItemList;
    private RankItemFilter mFilter;
    private CharSequence mLastConstraint;

    public RankingsAdapter(Context context, ArrayList<RankItem> rankItemList) {
        super(context);
        mFilteredRankItemList = new ArrayList<>();
        mRankItemList = rankItemList;
        mFilter = new RankItemFilter();
    }

    @Override
    public int getCount() {
        return mFilteredRankItemList.size();
    }

    @Override
    public RankItem getItem(int position) {
        return mFilteredRankItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        mFilter.filter(mLastConstraint);
    }

    private void notifyDataSetChangedOnly() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            RankItemViewHolder viewHolder = new RankItemViewHolder(mInflater);
            convertView = viewHolder.mRoot;
            convertView.setTag(viewHolder);
        }

        RankItem rankItem = getItem(position);
        RankItem prevRankItem = position == 0 ? null : getItem(position - 1);
        RankItemViewHolder viewHolder = (RankItemViewHolder) convertView.getTag();
        viewHolder.setRank(rankItem, prevRankItem);
        viewHolder.mRoot.setBackgroundColor(position % 2 == 1 ? 0xff12110f : 0xff171614);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class RankItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            mLastConstraint = constraint;
            FilterResults result = new FilterResults();
            ArrayList<RankItem> rankItemList = (ArrayList<RankItem>) mRankItemList.clone();
            ArrayList<RankItem> filteredItemList = new ArrayList<>();
            if (TextUtils.isEmpty(constraint)) {
                result.values = rankItemList;
            } else {
                for (RankItem rankItem : rankItemList) {
                    if (rankItem.getName().contains(constraint) || rankItem.getBattleTag().getBattleTag().contains(constraint)) {
                        filteredItemList.add(rankItem);
                    }
                }
                result.values = filteredItemList;
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mFilteredRankItemList.clear();
            mFilteredRankItemList.addAll((Collection<? extends RankItem>) results.values);
            notifyDataSetChangedOnly();
        }
    }

    private class RankItemViewHolder extends BaseViewHolder {

        private TextView mRank;
        private NetworkImageView mIcon;
        private TextView mName;
        private TextView mRiftTime;
        private TextView mCompletedTime;

        public RankItemViewHolder(LayoutInflater inflater) {
            super(inflater, R.layout.row_rank_item);
            mRank = (TextView) findViewById(R.id.rank_item_rank);
            mIcon = (NetworkImageView) findViewById(R.id.rank_item_image);
            mName = (TextView) findViewById(R.id.rank_item_name);
            mRiftTime = (TextView) findViewById(R.id.rank_item_rift_time);
            mCompletedTime = (TextView) findViewById(R.id.rank_item_completed_time);
        }

        private void setRank(RankItem rankItem, RankItem prevRankItem) {
            if (prevRankItem != null && TextUtils.equals(rankItem.getRank(), prevRankItem.getRank())) {
                mRank.setText(null);
            } else {
                mRank.setText(String.format(mContext.getString(R.string.greater_rift_tier), rankItem.getRank(), rankItem.getRiftLevel()));
            }
            mIcon.setImageUrl(rankItem.getIcon(), VolleySingleton.getInstance(mContext).getImageLoader());
            mName.setText(rankItem.getName());
            mRiftTime.setText(rankItem.getRiftTime());
            mCompletedTime.setText(rankItem.getCompletedTime());
        }
    }
}