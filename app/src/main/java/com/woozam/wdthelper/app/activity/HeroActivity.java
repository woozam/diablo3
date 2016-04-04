package com.woozam.wdthelper.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.woozam.wdthelper.R;
import com.woozam.wdthelper.analystics.Analystics;
import com.woozam.wdthelper.app.view.ProgressDialog;
import com.woozam.wdthelper.common.ADUtils;
import com.woozam.wdthelper.data.BattleTag;
import com.woozam.wdthelper.data.DataManager;
import com.woozam.wdthelper.data.Hero;
import com.woozam.wdthelper.database.DBHero;
import com.woozam.wdthelper.network.VolleySingleton;

import java.util.List;

/**
 * Created by woozam on 2016-01-31.
 */
public class HeroActivity extends AppCompatActivity implements Response.Listener<Hero>, Response.ErrorListener, DataManager.OnFavoriteHeroChangedListener, ViewPager.OnPageChangeListener {

    private static final String TAG = HeroActivity.class.getSimpleName();

    public static final String EXTRA_BATTLE_TAG = "battle_tag";
    public static final String EXTRA_HERO_ID = "hero_id";
    public static final String EXTRA_HERO_NAME = "hero_name";

    public static void createInstance(Context context, BattleTag battleTag, long heroId, String heroName) {
        Intent intent = new Intent(context, HeroActivity.class);
        intent.putExtra(EXTRA_BATTLE_TAG, battleTag);
        intent.putExtra(EXTRA_HERO_ID, heroId);
        intent.putExtra(EXTRA_HERO_NAME, heroName);
        context.startActivity(intent);
    }

    private BattleTag mBattleTag;
    private long mHeroId;
    private String mHeroName;
    private Hero mHero;

    private PagerTabStrip mPagerTabStrip;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ProgressDialog mProgressDialog;

    private Object mRequestTag = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);
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

        Intent intent = getIntent();
        mBattleTag = (BattleTag) intent.getSerializableExtra(EXTRA_BATTLE_TAG);
        mHeroId = intent.getLongExtra(EXTRA_HERO_ID, -1);
        mHeroName = intent.getStringExtra(EXTRA_HERO_NAME);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.hero_view_pager);
        mPagerTabStrip = (PagerTabStrip) findViewById(R.id.hero_view_pager_tab_strip);

        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        if (ADUtils.USE_AD) {
            mViewPager.setCurrentItem(2);
        } else {
            mViewPager.setCurrentItem(1);
        }
        mViewPager.addOnPageChangeListener(this);

        setTitle(mHeroName);

        mProgressDialog = new ProgressDialog(this);
        DataManager.getInstance().getHero(this, mBattleTag.getServer(), mBattleTag.getBattleTag(), mHeroId, this, this, mRequestTag);
        DataManager.getInstance().addOnFavoriteHeroListener(this);
        mProgressDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataManager.getInstance().removeOnFavoriteHeroListener(this);
        VolleySingleton.getInstance(this).getRequestQueue().cancelAll(mRequestTag);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Analystics.screen(TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hero, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mHero == null) {
            menu.findItem(R.id.action_add_favorite).setVisible(false);
            menu.findItem(R.id.action_remove_favorite).setVisible(false);
        } else if (DataManager.getInstance().containsFavoriteHero(mBattleTag, mHeroId)) {
            menu.findItem(R.id.action_add_favorite).setVisible(false);
            menu.findItem(R.id.action_remove_favorite).setVisible(true);
        } else {
            menu.findItem(R.id.action_add_favorite).setVisible(true);
            menu.findItem(R.id.action_remove_favorite).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String heroName = mHero.getName();
        String heroClass = mHero.getHeroClass();
        int heroGender = mHero.getGender();
        switch (item.getItemId()) {
            case R.id.action_add_favorite: {
                DataManager.getInstance().addFavoriteHero(mBattleTag, mHeroId, heroName, heroClass, heroGender);
                return true;
            }
            case R.id.action_remove_favorite: {
                DataManager.getInstance().removeHeroFavorite(mBattleTag, mHeroId);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Hero response) {
        mHero = response;
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof AbsHeroFragment) {
                ((AbsHeroFragment) fragment).setBattleTag(mBattleTag);
                ((AbsHeroFragment) fragment).setHero(response);
            }
        }
        invalidateOptionsMenu();
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, R.string.load_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAddFavoriteHero(DBHero hero) {
        Toast.makeText(this, R.string.favorite_added, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRemoveFavoriteHero(DBHero hero) {
        Toast.makeText(this, R.string.favorite_removed, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Analystics.screen(mSectionsPagerAdapter.getPageTitle(position).toString());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            AbsHeroFragment fragment = null;
            switch (position) {
                case 0:
                    return new HeroSkillFragment();
                case 1:
                    if (ADUtils.USE_AD) {
                        fragment = new HeroAdFragment();
                    } else {
                        fragment = new HeroItemFragment();
                    }
                    break;
                case 2:
                    if (ADUtils.USE_AD) {
                        fragment = new HeroItemFragment();
                    } else {
                        fragment = new HeroStatFragment();
                    }
                    break;
                case 3:
                    if (ADUtils.USE_AD) {
                        fragment = new HeroStatFragment();
                    } else {
                        fragment = new HeroFollowerFragment();
                    }
                    break;
                case 4:
                    fragment = new HeroFollowerFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            if (fragment != null && mHero != null) {
                ((AbsHeroFragment) fragment).setBattleTag(mBattleTag);
                ((AbsHeroFragment) fragment).setHero(mHero);
            }
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public int getCount() {
            if (ADUtils.USE_AD) {
                return 5;
            } else {
                return 4;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.skills);
                case 1:
                    if (ADUtils.USE_AD) {
                        return getString(R.string.advertisements);
                    } else {
                        return getString(R.string.items);
                    }
                case 2:
                    if (ADUtils.USE_AD) {
                        return getString(R.string.items);
                    } else {
                        return getString(R.string.statistics);
                    }
                case 3:
                    if (ADUtils.USE_AD) {
                        return getString(R.string.statistics);
                    } else {
                        return getString(R.string.followers);
                    }
                case 4:
                    return getString(R.string.followers);
            }
            return null;
        }
    }
}