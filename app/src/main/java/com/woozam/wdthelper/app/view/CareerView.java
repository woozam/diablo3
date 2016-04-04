package com.woozam.wdthelper.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woozam.wdthelper.R;
import com.woozam.wdthelper.data.Profile;

/**
 * Created by woozam on 2016-01-31.
 */
public class CareerView extends RelativeLayout {

    private View mContentLayout;
    private TextView mMonsters;
    private TextView mElites;
    private TextView mParagonLevel;
    private TextView mParagonLevelHardcore;
    private TextView mParagonLevelSeason;
    private TextView mParagonLevelSeasonHardcore;

    public CareerView(Context context) {
        super(context);
        initialize();
    }

    public CareerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public CareerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.career_layout, this, true);
        mContentLayout = findViewById(R.id.career_content_layout);
        mMonsters = (TextView) findViewById(R.id.career_monsters_count);
        mElites = (TextView) findViewById(R.id.career_elites_count);
        mParagonLevel = (TextView) findViewById(R.id.career_paragon_level);
        mParagonLevelHardcore = (TextView) findViewById(R.id.career_paragon_level_hardcore);
        mParagonLevelSeason = (TextView) findViewById(R.id.career_paragon_level_season);
        mParagonLevelSeasonHardcore = (TextView) findViewById(R.id.career_paragon_level_season_hardcore);
        mContentLayout.setVisibility(INVISIBLE);
    }

    public void setCareer(Profile profile) {
        try {
            mContentLayout.setVisibility(VISIBLE);
            mMonsters.setText(String.valueOf(profile.getKills().getMonsters()));
            mElites.setText(String.valueOf(profile.getKills().getElites()));
            mParagonLevel.setText(String.valueOf(profile.getParagonLevel()));
            mParagonLevelHardcore.setText(String.valueOf(profile.getParagonLevelHardcore()));
            mParagonLevelSeason.setText(String.valueOf(profile.getParagonLevelSeason()));
            mParagonLevelSeasonHardcore.setText(String.valueOf(profile.getParagonLevelSeasonHardcore()));
        } catch (Exception e) {
            mContentLayout.setVisibility(INVISIBLE);
            e.printStackTrace();
        }
    }

    public void clearCareer() {
        mContentLayout.setVisibility(INVISIBLE);
        mMonsters.setText(null);
        mElites.setText(null);
        mParagonLevel.setText(null);
        mParagonLevelHardcore.setText(null);
        mParagonLevelSeason.setText(null);
        mParagonLevelSeasonHardcore.setText(null);
    }
}