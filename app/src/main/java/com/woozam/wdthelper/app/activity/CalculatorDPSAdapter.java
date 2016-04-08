package com.woozam.wdthelper.app.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woozam.wdthelper.R;
import com.woozam.wdthelper.common.CommonUtils;
import com.woozam.wdthelper.data.CalculatorDPSItem;

import java.util.ArrayList;

/**
 * Created by woozam on 2016-04-06.
 */
public class CalculatorDPSAdapter extends D3BaseAdapter {

    private ArrayList<CalculatorDPSItem> mCalculatorDPSItemList;
    private OnClickListener mOnClickListener;

    public CalculatorDPSAdapter(Context context, ArrayList<CalculatorDPSItem> calculatorDPSItemList, OnClickListener onClickListener) {
        super(context);
        mCalculatorDPSItemList = calculatorDPSItemList;
        mOnClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return mCalculatorDPSItemList.size();
    }

    @Override
    public CalculatorDPSItem getItem(int position) {
        return mCalculatorDPSItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ItemViewHolder(mInflater);
            convertView = viewHolder.mRoot;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder) convertView.getTag();
        }

        CalculatorDPSItem item = getItem(position);
        viewHolder.setItem(item);
        viewHolder.mRoot.setBackgroundColor(position % 2 == 1 ? 0xff171614 : 0xff12110f);
        viewHolder.mRemove.setOnClickListener(mOnClickListener);
        viewHolder.mRemove.setTag(item);

        return convertView;
    }

    private class ItemViewHolder extends BaseViewHolder {

        private TextView mName;
        private TextView mValue;
        private View mRemove;

        public ItemViewHolder(LayoutInflater inflater) {
            super(inflater, R.layout.row_calculator_dps);
            mName = (TextView) findViewById(R.id.calculator_dps_name);
            mValue = (TextView) findViewById(R.id.calculator_dps_value);
            mValue.getLayoutParams().width = CommonUtils.convertDipToPx(mContext, 100);
            mRemove = findViewById(R.id.calculator_dps_remove);
            mRemove.setVisibility(View.VISIBLE);
        }

        public void setItem(CalculatorDPSItem item) {
            mName.setText(item.getName());
            mValue.setText(item.getStatString());
        }
    }
}