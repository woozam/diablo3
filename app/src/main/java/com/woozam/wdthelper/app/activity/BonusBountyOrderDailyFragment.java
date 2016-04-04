package com.woozam.wdthelper.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.woozam.wdthelper.R;
import com.woozam.wdthelper.data.Bounty;

/**
 * Created by woozam on 2016-04-03.
 */
public class BonusBountyOrderDailyFragment extends Fragment {

    public static final String EXTRA_DATE = "extra_date";

    private View mRoot;
    private BonusBountyOrderAdapter mAdapter;
    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int date = getArguments().getInt(EXTRA_DATE);
        mRoot = inflater.inflate(R.layout.fragment_daily_bonus_bounty_order, container, false);
        mAdapter = new BonusBountyOrderAdapter(getContext(), Bounty.getDailyBonusBountyOrderList(date));
        mListView = (ListView) mRoot.findViewById(R.id.daily_bonus_bounty_order_list_view);
        mListView.setAdapter(mAdapter);
        return mRoot;
    }
}