package com.woozam.wdthelper.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.woozam.wdthelper.R;
import com.woozam.wdthelper.app.view.CareerView;
import com.woozam.wdthelper.app.view.ViewUtils;
import com.woozam.wdthelper.common.ADUtils;
import com.woozam.wdthelper.common.CommonUtils;
import com.woozam.wdthelper.common.PreferenceUtils;
import com.woozam.wdthelper.data.BattleTag;
import com.woozam.wdthelper.data.DataManager;
import com.woozam.wdthelper.data.Hero;
import com.woozam.wdthelper.data.Profile;
import com.woozam.wdthelper.data.Server;
import com.woozam.wdthelper.network.VolleySingleton;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by woozam on 2016-01-31.
 */
public class ProfileFragment extends Fragment implements Response.Listener<Profile>, Response.ErrorListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, TextView.OnEditorActionListener {

    private BattleTag mBattleTagPreset;
    private BattleTag mBattleTag;
    private View mRoot;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    private CareerView mCareerView;
    private HeroAdapter mHeroAdapter;
    private TextView mLastUpdate;
    private Profile mProfile;

    private View mAddBattleTagLayout;
    private View mAddBattleTag;
    private AppCompatSpinner mServerSpinner;
    private ServerSpinnerAdapter mServerSpinnerAdapter;
    private AppCompatEditText mAddBattleTagName;
    private AppCompatEditText mAddBattleTagNumber;

    private AdView mProfileAdView;
    private InterstitialAd mInterstitialAd;

    private Object mRequestTag = new Object();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_profile, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mRoot.findViewById(R.id.profile_swipe_refresh_layout);
        mListView = (ListView) mRoot.findViewById(R.id.profile_list_view);
        mCareerView = new CareerView(getContext());
        mHeroAdapter = new HeroAdapter(getContext(), new Hero[0]);
        mLastUpdate = new TextView(getContext());
        mLastUpdate.setTextColor(0xffa99877);
        mLastUpdate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        mLastUpdate.setGravity(Gravity.CENTER_HORIZONTAL);
        int padding = CommonUtils.convertDipToPx(6);
        mLastUpdate.setPadding(padding, padding, padding, padding);
        mAddBattleTagLayout = mRoot.findViewById(R.id.profile_add_battle_tag_layout);
        mAddBattleTag = mRoot.findViewById(R.id.profile_add_battle_tag);
        mServerSpinner = (AppCompatSpinner) mRoot.findViewById(R.id.profile_add_battle_tag_server);
        mServerSpinnerAdapter = new ServerSpinnerAdapter(getContext(), Server.toArrayList());
        mAddBattleTagName = (AppCompatEditText) mRoot.findViewById(R.id.profile_add_battle_tag_name);
        mAddBattleTagNumber = (AppCompatEditText) mRoot.findViewById(R.id.profile_add_battle_tag_number);
        mProfileAdView = new AdView(getContext());
        mProfileAdView.setAdUnitId(getString(R.string.ad_unit_id_profile));
        mProfileAdView.setAdSize(AdSize.SMART_BANNER);
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_id_profile_popup));

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mListView.addHeaderView(mCareerView);
        if (ADUtils.USE_AD) {
            mListView.addHeaderView(mProfileAdView);
        }
        mListView.setAdapter(mHeroAdapter);
        mListView.setOnItemClickListener(this);
        mListView.addFooterView(mLastUpdate);
        mAddBattleTag.setOnClickListener(this);
        mServerSpinner.setAdapter(mServerSpinnerAdapter);
        mServerSpinner.setSelection(Server.toArrayList().indexOf(Server.valueOf(PreferenceUtils.getStringValue(PreferenceUtils.KEY_LAST_BATTLE_TAG_SERVER, Server.KR.name()))));
        mAddBattleTagName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        mAddBattleTagName.setNextFocusDownId(R.id.profile_add_battle_tag_number);
        mAddBattleTagNumber.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mAddBattleTagNumber.setOnEditorActionListener(this);
        mAddBattleTagLayout.setVisibility(View.GONE);

        if (mBattleTagPreset != null) {
            mRoot.post(new Runnable() {
                @Override
                public void run() {
                    loadProfile(mBattleTagPreset);
                }
            });
        }

        return mRoot;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProfileAdView.destroy();
        VolleySingleton.getInstance(getContext()).getRequestQueue().cancelAll(mRequestTag);
    }

    @Override
    public void onResume() {
        super.onResume();
        mProfileAdView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mProfileAdView.pause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (mBattleTag == null) {
            menu.findItem(R.id.action_add_battle_tag).setVisible(false);
            menu.findItem(R.id.action_remove_battle_tag).setVisible(false);
        } else if (mBattleTag.isAdd()) {
            menu.findItem(R.id.action_add_battle_tag).setVisible(false);
            menu.findItem(R.id.action_remove_battle_tag).setVisible(false);
        } else if (DataManager.getInstance().containBattleTag(mBattleTag)) {
            menu.findItem(R.id.action_add_battle_tag).setVisible(false);
            menu.findItem(R.id.action_remove_battle_tag).setVisible(true);
        } else {
            menu.findItem(R.id.action_add_battle_tag).setVisible(true);
            menu.findItem(R.id.action_remove_battle_tag).setVisible(false);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_battle_tag: {
                DataManager.getInstance().addBattleTag(mBattleTag);
                return true;
            }
            case R.id.action_remove_battle_tag: {
                DataManager.getInstance().removeBattleTag(mBattleTag);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void setBattlePreset(BattleTag battleTag) {
        mBattleTagPreset = battleTag;
    }

    private void setProfile(final Profile profile) {
        if (!isAdded()) {
            return;
        }
        mProfile = profile;
        mCareerView.setCareer(profile);
        if (profile.getLastUpdated() > 0) {
            String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(profile.getLastUpdated() * 1000));
            mLastUpdate.setText(getString(R.string.last_update) + timeStamp);
        } else {
            mLastUpdate.setText(R.string.load_error);
        }
        mHeroAdapter.setHeros(profile.getHeroes());
        loadProfileAd();
    }

    private void clearProfile() {
        mCareerView.clearCareer();
        mLastUpdate.setText(null);
        mHeroAdapter.setHeros(new Hero[0]);
    }

    public void loadProfile(final BattleTag battleTag) {
        if (mRoot == null) {
            return;
        }
        if (battleTag.isAdd()) {
            mSwipeRefreshLayout.setVisibility(View.GONE);
            mAddBattleTagLayout.setVisibility(View.VISIBLE);
            getActivity().setTitle(getString(R.string.app_name));
            mAddBattleTagName.setText(null);
            mAddBattleTagNumber.setText(null);
        } else {
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            mAddBattleTagLayout.setVisibility(View.GONE);
            getActivity().setTitle(battleTag.getBattleTag().replace("-", "#"));
            if (mBattleTag == null || !mBattleTag.equals(battleTag)) {
                clearProfile();
            }
            DataManager.getInstance().getProfile(getContext(), battleTag.getServer(), battleTag.getBattleTag(), ProfileFragment.this, ProfileFragment.this, mRequestTag);
            mSwipeRefreshLayout.setRefreshing(true);
            CommonUtils.hideSoftInput(getContext(), mAddBattleTagName);
            CommonUtils.hideSoftInput(getContext(), mAddBattleTagNumber);
        }
        mBattleTag = battleTag;
        getActivity().invalidateOptionsMenu();
    }

    private void loadProfileAd() {
        if (ADUtils.USE_AD) {
            ADUtils.requestAD(mProfileAdView);
            if (mInterstitialAd.isLoaded() && isResumed()) {
                mInterstitialAd.show();
            } else {
                ADUtils.requestAD(mInterstitialAd, 0.4f);
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        if (isResumed()){
                            mInterstitialAd.show();
                        }
                    }
                });
            }
        }
    }

    public BattleTag getBattleTag() {
        return mBattleTag;
    }

    @Override
    public void onResponse(Profile response) {
        mSwipeRefreshLayout.setRefreshing(false);
        setProfile(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), R.string.load_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int headerViewsCount = mListView.getHeaderViewsCount();
        if (position > headerViewsCount - 1) {
            position -= headerViewsCount;
            if (position < mHeroAdapter.getCount()) {
                Hero hero = mHeroAdapter.getItem(position);
                HeroActivity.createInstance(getContext(), mBattleTag, hero.getId(), hero.getName());
            }
        }
    }

    @Override
    public void onRefresh() {
        if (mBattleTag != null) {
            loadProfile(mBattleTag);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mAddBattleTag) {
            addBattleTag();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v == mAddBattleTagNumber) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addBattleTag();
                return true;
            }
        }
        return false;
    }

    private void addBattleTag() {
        String battleTagName = mAddBattleTagName.getText().toString();
        if (TextUtils.isEmpty(battleTagName)) {
            ViewUtils.setError(getContext(), mAddBattleTagName, null);
            return;
        }
        String battleTagNumber = mAddBattleTagNumber.getText().toString();
        if (TextUtils.isEmpty(battleTagNumber)) {
            ViewUtils.setError(getContext(), mAddBattleTagNumber, null);
            return;
        }
        Server server = (Server) mServerSpinner.getSelectedItem();
        PreferenceUtils.setValue(PreferenceUtils.KEY_LAST_BATTLE_TAG_SERVER, server.name());
        BattleTag battleTag = new BattleTag();
        battleTag.setBattleTag(String.format("%s-%s", battleTagName, battleTagNumber));
        battleTag.setServer(server);
        DataManager.getInstance().addBattleTag(battleTag);
        CommonUtils.hideSoftInput(getActivity(), mAddBattleTagName);
        CommonUtils.hideSoftInput(getActivity(), mAddBattleTagNumber);
        mAddBattleTagName.setText(null);
        mAddBattleTagNumber.setText(null);
    }

    public void removeAds() {
        if (mProfileAdView != null) {
            mListView.removeHeaderView(mProfileAdView);
        }
    }
}