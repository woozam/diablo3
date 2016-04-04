package com.woozam.wdthelper.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.woozam.wdthelper.R;
import com.woozam.wdthelper.analystics.Analystics;
import com.woozam.wdthelper.billing.BillingUtils;
import com.woozam.wdthelper.billing.util.IabHelper;
import com.woozam.wdthelper.billing.util.IabResult;
import com.woozam.wdthelper.billing.util.Inventory;
import com.woozam.wdthelper.billing.util.Purchase;
import com.woozam.wdthelper.common.ADUtils;
import com.woozam.wdthelper.common.Email;
import com.woozam.wdthelper.common.PreferenceUtils;
import com.woozam.wdthelper.data.BattleTag;
import com.woozam.wdthelper.data.DataManager;

import java.util.Collection;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DataManager.OnBattleTagChangedListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private BattleTagSpinnerAdapter mBattleTagSpinnerAdapter;
    private AppCompatSpinner mSpinner;
    private ProfileFragment mProfileFragment;
    private IabHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag("profile") != null) {
            mProfileFragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("profile");
        } else {
            mProfileFragment = new ProfileFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.main_content, mProfileFragment, "profile");
            ft.commit();
        }

        int selection = PreferenceUtils.getIntValue(PreferenceUtils.KEY_LAST_BATTLE_TAG, 0);
        mBattleTagSpinnerAdapter = new BattleTagSpinnerAdapter(this, DataManager.getInstance().getBattleTagList());
        mSpinner = (AppCompatSpinner) navigationView.getHeaderView(0).findViewById(R.id.main_spinner);
        mSpinner.setAdapter(mBattleTagSpinnerAdapter);
        mSpinner.setOnItemSelectedListener(this);
        mSpinner.setSelection(selection);

        DataManager.getInstance().addOnAddBattleTagListener(this);

        if (ADUtils.USE_AD) {
            String base64EncodedPublicKey = BillingUtils.getLicenseKey();
            mHelper = new IabHelper(this, base64EncodedPublicKey);
            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult result) {
                    if (!result.isSuccess()) {
                        Log.d(TAG, "Problem setting up In-app Billing: " + result);
                    } else {
                        checkRemoveAdsPurchased();
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataManager.getInstance().removeOnAddBattleTagListener(this);
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Analystics.screen(TAG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mHelper != null && !mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void setTitle(final CharSequence title) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ActionBar ab = getSupportActionBar();
                if (ab != null) {
                    ab.setDisplayShowTitleEnabled(false);
                    MainActivity.super.setTitle(title);
                    ab.setDisplayShowTitleEnabled(true);
                } else {
                    MainActivity.super.setTitle(title);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mBattleTagSpinnerAdapter.getCount() > 1 && ((BattleTag) mSpinner.getSelectedItem()).isAdd()) {
            int selection = PreferenceUtils.getIntValue(PreferenceUtils.KEY_LAST_BATTLE_TAG, 0);
            mSpinner.setSelection(selection);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_favorite) {
            FavoriteActivity.createInstance(this);
        } else if (id == R.id.nav_ranking) {
            RankingsActivity.createInstance(this);
        } else if (id == R.id.nav_enchant) {
            EnchantActivity.createInstance(this);
        } else if (id == R.id.nav_remove_ad) {
            purchaseRemoveAds();
        } else if (id == R.id.nav_suggestion) {
            Email.sendEmail(this);
        } else if (id == R.id.nav_bonus_bounty_order) {
            BonusBountyOrderActivity.createInstance(this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setBattleTag(BattleTag battleTag) {
        mProfileFragment.loadProfile(battleTag);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onAddBattleTag(int selection) {
        mBattleTagSpinnerAdapter.clear();
        mBattleTagSpinnerAdapter.addAll(DataManager.getInstance().getBattleTagList());
        mSpinner.setSelection(selection);
        BattleTag battleTag = mBattleTagSpinnerAdapter.getItem(selection);
        setBattleTag(battleTag);
        if (!battleTag.isAdd()) {
            PreferenceUtils.setValue(PreferenceUtils.KEY_LAST_BATTLE_TAG, selection);
        }
    }

    @Override
    public void onRemoveBattleTag(BattleTag battleTag) {
        invalidateOptionsMenu();
        mBattleTagSpinnerAdapter.remove(battleTag);
        mSpinner.setSelection(0);
        battleTag = mBattleTagSpinnerAdapter.getItem(0);
        setBattleTag(battleTag);
        if (!battleTag.isAdd()) {
            PreferenceUtils.setValue(PreferenceUtils.KEY_LAST_BATTLE_TAG, 0);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        BattleTag battleTag = mBattleTagSpinnerAdapter.getItem(position);
        setBattleTag(battleTag);
        if (!battleTag.isAdd()) {
            PreferenceUtils.setValue(PreferenceUtils.KEY_LAST_BATTLE_TAG, position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void checkRemoveAdsPurchased() {
        mHelper.queryInventoryAsync(mCheckInventoryListener);
    }

    private IabHelper.QueryInventoryFinishedListener mCheckInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            if (result.isSuccess()) {
                if (inventory.hasPurchase(BillingUtils.SKU_REMOVE_AD)) {
                    ADUtils.setRemoveAdsPurchased();
                    if (mProfileFragment != null) {
                        mProfileFragment.removeAds();
                    }
                } else {
                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    navigationView.getMenu().findItem(R.id.nav_remove_ad).setVisible(true);
                }
            }
        }
    };

    private void purchaseRemoveAds() {
        if (mHelper != null) {
            mHelper.launchPurchaseFlow(this, BillingUtils.SKU_REMOVE_AD, IabHelper.ITEM_TYPE_INAPP, IabHelper.REQUEST_CODE, mPurchaseFinishedListener, null);
        }
    }

    private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (result.isFailure()) {
            } else if (purchase.getSku().equals(BillingUtils.SKU_REMOVE_AD)) {
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.getMenu().findItem(R.id.nav_remove_ad).setVisible(false);
                ADUtils.setRemoveAdsPurchased();
                if (mProfileFragment != null) {
                    mProfileFragment.removeAds();
                }
            }
        }
    };

    private class BattleTagSpinnerAdapter extends ArrayAdapter<BattleTag> {

        public BattleTagSpinnerAdapter(Context context, Collection<BattleTag> battleTags) {
            super(context, R.layout.row_battle_tag_spinner, R.id.battle_tag_spinner_text_view);
            addAll(battleTags);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);
            TextView textView = (TextView) convertView.findViewById(R.id.battle_tag_spinner_text_view);
            textView.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white_bb));
            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            convertView = super.getDropDownView(position, convertView, parent);
            TextView textView = (TextView) convertView.findViewById(R.id.battle_tag_spinner_text_view);
            textView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.primary_text_light));
            return convertView;
        }
    }
}