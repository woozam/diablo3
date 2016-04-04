package com.woozam.wdthelper.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.woozam.wdthelper.R;
import com.woozam.wdthelper.analystics.Analystics;
import com.woozam.wdthelper.data.BattleTag;

/**
 * Created by woozam on 2016-02-07.
 */
public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ProfileActivity.class.getSimpleName();

    public static final String EXTRA_BATTLE_TAG = "action_battle_tag";

    public static void createInstance(Context context, BattleTag battleTag) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRA_BATTLE_TAG, battleTag);
        context.startActivity(intent);
    }

    private BattleTag mBattleTag;
    private ProfileFragment mProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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

        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag("profile") != null) {
            mProfileFragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("profile");
        } else {
            mProfileFragment = new ProfileFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.profile_fragment_layout, mProfileFragment, "profile");
            ft.commit();
        }
        mProfileFragment.setBattlePreset(mBattleTag);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Analystics.screen(TAG);
    }
}