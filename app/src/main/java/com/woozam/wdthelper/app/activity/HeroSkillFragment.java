package com.woozam.wdthelper.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.woozam.wdthelper.R;
import com.woozam.wdthelper.app.view.SkillActiveItem;
import com.woozam.wdthelper.app.view.SkillPassiveItem;
import com.woozam.wdthelper.common.ScreenShotTask;
import com.woozam.wdthelper.data.Hero;

/**
 * Created by woozam on 2016-02-02.
 */
public class HeroSkillFragment extends AbsHeroFragment {

    private View mRoot;
    private View mItemLayout;
    private SkillActiveItem mActiveMouseLeft;
    private SkillActiveItem mActiveMouseRight;
    private SkillActiveItem mActive1;
    private SkillActiveItem mActive2;
    private SkillActiveItem mActive3;
    private SkillActiveItem mActive4;
    private SkillPassiveItem mPassive1;
    private SkillPassiveItem mPassive2;
    private SkillPassiveItem mPassive3;
    private SkillPassiveItem mPassive4;

    public HeroSkillFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_hero_skill, container, false);
        mItemLayout = mRoot.findViewById(R.id.hero_skill_item_layout);
        mActiveMouseLeft = (SkillActiveItem) mRoot.findViewById(R.id.hero_skill_active_mouse_left);
        mActiveMouseRight = (SkillActiveItem) mRoot.findViewById(R.id.hero_skill_active_mouse_right);
        mActive1 = (SkillActiveItem) mRoot.findViewById(R.id.hero_skill_active_1);
        mActive2 = (SkillActiveItem) mRoot.findViewById(R.id.hero_skill_active_2);
        mActive3 = (SkillActiveItem) mRoot.findViewById(R.id.hero_skill_active_3);
        mActive4 = (SkillActiveItem) mRoot.findViewById(R.id.hero_skill_active_4);
        mPassive1 = (SkillPassiveItem) mRoot.findViewById(R.id.hero_skill_passive_1);
        mPassive2 = (SkillPassiveItem) mRoot.findViewById(R.id.hero_skill_passive_2);
        mPassive3 = (SkillPassiveItem) mRoot.findViewById(R.id.hero_skill_passive_3);
        mPassive4 = (SkillPassiveItem) mRoot.findViewById(R.id.hero_skill_passive_4);
        setHero();
        return mRoot;
    }

    @Override
    public void setHero(Hero hero) {
        super.setHero(hero);
        setHero();
    }

    private void setHero() {
        try {
            if (mRoot != null && mHero != null) {
                mActiveMouseLeft.setSkill(mBattleTag.getServer(), mHero.getSkills().getActive()[0]);
                mActiveMouseRight.setSkill(mBattleTag.getServer(), mHero.getSkills().getActive()[1]);
                mActive1.setSkill(mBattleTag.getServer(), mHero.getSkills().getActive()[2]);
                mActive2.setSkill(mBattleTag.getServer(), mHero.getSkills().getActive()[3]);
                mActive3.setSkill(mBattleTag.getServer(), mHero.getSkills().getActive()[4]);
                mActive4.setSkill(mBattleTag.getServer(), mHero.getSkills().getActive()[5]);
                mPassive1.setSkill(mBattleTag.getServer(), mHero.getSkills().getPassive()[0]);
                mPassive2.setSkill(mBattleTag.getServer(), mHero.getSkills().getPassive()[1]);
                mPassive3.setSkill(mBattleTag.getServer(), mHero.getSkills().getPassive()[2]);
                mPassive4.setSkill(mBattleTag.getServer(), mHero.getSkills().getPassive()[3]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_hero_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_capture: {
                saveScreenShot();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveScreenShot() {
        new ScreenShotTask(mItemLayout, mBattleTag.getBattleTag() + "_" + mHero.getName() + "_" + "skills_" + System.currentTimeMillis(), "hero skills").execute();
    }
}