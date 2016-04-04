package com.woozam.wdthelper.app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;

import com.woozam.wdthelper.R;
import com.woozam.wdthelper.analystics.Analystics;
import com.woozam.wdthelper.common.PreferenceUtils;
import com.woozam.wdthelper.data.Bounty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by woozam on 2016-04-03.
 */
public class BonusBountyOrderActivity extends AppCompatActivity implements OnClickListener {

    public static final String TAG = BonusBountyOrderActivity.class.getSimpleName();

    public static void createInstance(Context context) {
        Intent intent = new Intent(context, BonusBountyOrderActivity.class);
        context.startActivity(intent);
    }

    private TextView mCurrent;
    private CheckBox mShowStatusBar;
    private BonusBountyOrderDailyPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_bounty_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mCurrent = (TextView) findViewById(R.id.bonus_bounty_order_current_content);
        mShowStatusBar = (CheckBox) findViewById(R.id.bonus_bounty_order_current_show_status_bar);
        mPagerAdapter = new BonusBountyOrderDailyPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.bonus_bounty_order_view_pager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem((int) ((float) (System.currentTimeMillis() + TimeZone.getDefault().getRawOffset()) / (float) (3600 * 24 * 1000)));

        mCurrent.setText(String.format("%s > %s > %s > %s > %s", Bounty.getCurrentBonusBountyOrder()));
        mShowStatusBar.setOnClickListener(this);
        mShowStatusBar.setChecked(PreferenceUtils.getBooleanValue(PreferenceUtils.KEY_BONUS_BOUNTY_ORDER_SHOW_STATUS_BAR, false));

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mShowStatusBar.setChecked(PreferenceUtils.getBooleanValue(PreferenceUtils.KEY_BONUS_BOUNTY_ORDER_SHOW_STATUS_BAR, false));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Analystics.screen(TAG);
    }

    @Override
    public void onClick(View v) {
        if (v == mShowStatusBar) {
            onShowStatusBarClick();
        }
    }

    private void onShowStatusBarClick() {
        if (mShowStatusBar.isChecked()) {
            Analystics.event("Button", "BonusBountyOrder On");
            PreferenceUtils.setValue(PreferenceUtils.KEY_BONUS_BOUNTY_ORDER_SHOW_STATUS_BAR, true);
            Bounty.showStatusBar(this);
        } else {
            Analystics.event("Button", "BonusBountyOrder Off");
            PreferenceUtils.setValue(PreferenceUtils.KEY_BONUS_BOUNTY_ORDER_SHOW_STATUS_BAR, false);
            Bounty.dismissStatusBar(this);
        }
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
                mCurrent.setText(String.format("%s > %s > %s > %s > %s", Bounty.getCurrentBonusBountyOrder()));
            }
        }
    };

    private class BonusBountyOrderDailyPagerAdapter extends FragmentPagerAdapter {

        public BonusBountyOrderDailyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt(BonusBountyOrderDailyFragment.EXTRA_DATE, position);
            return BonusBountyOrderDailyFragment.instantiate(BonusBountyOrderActivity.this, BonusBountyOrderDailyFragment.class.getName(), bundle);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd(c)", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return dateFormat.format(new Date((long) position * 3600 * 24 * 1000));
        }

        @Override
        public int getCount() {
            return 1000000;
        }
    }
}