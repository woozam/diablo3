package com.woozam.wdthelper.app.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woozam.wdthelper.R;
import com.woozam.wdthelper.data.Bounty;

import java.util.ArrayList;

/**
 * Created by woozam on 2016-04-03.
 */
public class BonusBountyOrderAdapter extends D3BaseAdapter {

    private ArrayList<Bounty[]> mBonusBountyOrderList;

    public BonusBountyOrderAdapter(Context context, ArrayList<Bounty[]> bonusBountyOrderList) {
        super(context);
        mBonusBountyOrderList = bonusBountyOrderList;
    }

    @Override
    public int getCount() {
        return mBonusBountyOrderList.size();
    }

    @Override
    public Bounty[] getItem(int position) {
        return mBonusBountyOrderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BonusBountyOrderViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new BonusBountyOrderViewHolder(mInflater);
            convertView = viewHolder.mRoot;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BonusBountyOrderViewHolder) convertView.getTag();
        }

        viewHolder.setBonusBountyOrder(getItem(position), position);

        return convertView;
    }

    private class BonusBountyOrderViewHolder extends BaseViewHolder {

        private TextView mTime;
        private TextView mContent;

        public BonusBountyOrderViewHolder(LayoutInflater inflater) {
            super(inflater, R.layout.row_bonus_bounty_order);
            mTime = (TextView) findViewById(R.id.bonus_bounty_order_time);
            mContent = (TextView) findViewById(R.id.bonus_bounty_order_content);
        }

        public void setBonusBountyOrder(Bounty[] bonusBountyOrder, int position) {
            if (position < 12) {
                mTime.setText(String.format(mTime.getContext().getString(R.string.bonus_bounty_order_time_am), position));
            } else {
                mTime.setText(String.format(mTime.getContext().getString(R.string.bonus_bounty_order_time_pm), position));
            }
            mContent.setText(String.format("%s > %s > %s > %s > %s", bonusBountyOrder));
            mRoot.setBackgroundColor(position % 2 == 1 ? 0xff12110f : 0xff171614);
        }
    }
}