package com.woozam.wdthelper.app.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woozam.wdthelper.R;
import com.woozam.wdthelper.common.ScreenShotTask;
import com.woozam.wdthelper.data.Hero;
import com.woozam.wdthelper.data.Stats;
import com.woozam.wdthelper.network.VolleySingleton;

import java.math.BigDecimal;

public class HeroStatFragment extends AbsHeroFragment {

    private View mRoot;
    private View mStatLayout;
    private TextView mAbilityName;
    private TextView mAbilityValue;
    private TextView mAttackName;
    private TextView mAttackValue;
    private TextView mDefenseName;
    private TextView mDefenseValue;
    private TextView mResistName;
    private TextView mResistValue;
    private TextView mVitalityName;
    private TextView mVitalityValue;
    private TextView mAdventureName;
    private TextView mAdventureValue;

    public HeroStatFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hero_stat, container, false);
        mStatLayout = view.findViewById(R.id.hero_stat_layout);
        mAbilityName = (TextView) view.findViewById(R.id.hero_stat_ability_name);
        mAbilityValue = (TextView) view.findViewById(R.id.hero_stat_ability_value);
        mAttackName = (TextView) view.findViewById(R.id.hero_stat_attack_name);
        mAttackValue = (TextView) view.findViewById(R.id.hero_stat_attack_value);
        mDefenseName = (TextView) view.findViewById(R.id.hero_stat_defense_name);
        mDefenseValue = (TextView) view.findViewById(R.id.hero_stat_defense_value);
        mResistName = (TextView) view.findViewById(R.id.hero_stat_resist_name);
        mResistValue = (TextView) view.findViewById(R.id.hero_stat_resist_value);
        mVitalityName = (TextView) view.findViewById(R.id.hero_stat_vitality_name);
        mVitalityValue = (TextView) view.findViewById(R.id.hero_stat_vitality_value);
        mAdventureName = (TextView) view.findViewById(R.id.hero_stat_adventure_name);
        mAdventureValue = (TextView) view.findViewById(R.id.hero_stat_adventure_value);
        mRoot = view;
        setHero();
        return view;
    }

    @Override
    public void setHero(Hero hero) {
        super.setHero(hero);
        setHero();
    }

    private void setHero() {
        try {
            if (mRoot != null && mHero != null) {
                Stats stats = mHero.getStats();
                StringBuilder name = new StringBuilder();
                StringBuilder value = new StringBuilder();
                name.append(getString(R.string.stat_strength)).append("\n");
                name.append(getString(R.string.stat_dexterity)).append("\n");
                name.append(getString(R.string.stat_intelligence)).append("\n");
                name.append(getString(R.string.stat_vitality)).append("\n");
                name.append(getString(R.string.stat_damage)).append("\n");
                name.append(getString(R.string.stat_toughness)).append("\n");
                name.append(getString(R.string.stat_healing)).append("\n");
                name.append(getString(R.string.stat_primary_resource)).append("\n");
                name.append(getString(R.string.stat_secondary_resource));
                value.append(stats.getStrength()).append("\n");
                value.append(stats.getDexterity()).append("\n");
                value.append(stats.getIntelligence()).append("\n");
                value.append(stats.getVitality()).append("\n");
                value.append(BigDecimal.valueOf(stats.getDamage()).longValue()).append("\n");
                value.append(BigDecimal.valueOf(stats.getToughness()).longValue()).append("\n");
                value.append(BigDecimal.valueOf(stats.getHealing()).longValue()).append("\n");
                value.append(stats.getPrimaryResource()).append("\n");
                value.append(stats.getSecondaryResource());
                mAbilityName.setText(name.toString());
                mAbilityValue.setText(value.toString());
                name.delete(0, name.length());
                name.append(getString(R.string.stat_damage_increased_by_skills)).append("\n");
                name.append(getString(R.string.stat_attack_per_second)).append("\n");
                name.append(getString(R.string.stat_critical_hit_chance)).append("\n");
                name.append(getString(R.string.stat_critical_hit_damage)).append("\n");
                name.append(getString(R.string.stat_thorns));
                value.delete(0, value.length());
                value.append(String.format("%d%%", Math.round(stats.getDamageIncrease() * 100))).append("\n");
                value.append(String.format("%.2f", Math.round(stats.getAttackSpeed() * 100) / 100f)).append("\n");
                value.append(String.format("%.1f%%", Math.round(stats.getCritChance() * 100 * 10) / 10f)).append("\n");
                value.append(String.format("+%.1f%%", Math.round((stats.getCritDamage() - 1) * 100 * 10) / 10f)).append("\n");
                value.append(stats.getThorns());
                mAttackName.setText(name.toString());
                mAttackValue.setText(value.toString());
                name.delete(0, name.length());
                name.append(getString(R.string.stat_armor)).append("\n");
                name.append(getString(R.string.stat_damage_reduction)).append("\n");
                name.append(getString(R.string.stat_block_chance)).append("\n");
                name.append(getString(R.string.stat_block_amount));
                value.delete(0, value.length());
                value.append(stats.getArmor()).append("\n");
                value.append(String.format("%.1f%%", stats.getDamageReduction() * 100)).append("\n");
                value.append(String.format("%.1f%%", stats.getBlockChance() * 100)).append("\n");
                value.append(String.format("%d~%d", stats.getBlockAmountMin(), stats.getBlockAmountMax()));
                mDefenseName.setText(name.toString());
                mDefenseValue.setText(value.toString());
                name.delete(0, name.length());
                name.append(getString(R.string.stat_physical_resistance)).append("\n");
                name.append(getString(R.string.stat_fire_resistance)).append("\n");
                name.append(getString(R.string.stat_cold_resistance)).append("\n");
                name.append(getString(R.string.stat_lightning_resistance)).append("\n");
                name.append(getString(R.string.stat_poison_resistance)).append("\n");
                name.append(getString(R.string.stat_arcane_holy_resistance));
                value.delete(0, value.length());
                value.append(stats.getPhysicalResist()).append("\n");
                value.append(stats.getFireResist()).append("\n");
                value.append(stats.getColdResist()).append("\n");
                value.append(stats.getLightningResist()).append("\n");
                value.append(stats.getPoisonResist()).append("\n");
                value.append(stats.getArcaneResist());
                mResistName.setText(name.toString());
                mResistValue.setText(value.toString());
                name.delete(0, name.length());
                name.append(getString(R.string.stat_maximum_life)).append("\n");
                name.append(getString(R.string.stat_life_per_hit)).append("\n");
                name.append(getString(R.string.stat_life_per_kill)).append("\n");
                name.append(getString(R.string.stat_life_steal));
                value.delete(0, value.length());
                value.append(stats.getLife()).append("\n");
                value.append(stats.getLifeOnHit()).append("\n");
                value.append(stats.getLifePerKill()).append("\n");
                value.append(stats.getLifeSteal());
                mVitalityName.setText(name.toString());
                mVitalityValue.setText(value.toString());
                name.delete(0, name.length());
                name.append(getString(R.string.stat_magic_find)).append("\n");
                name.append(getString(R.string.stat_gold_find));
                value.delete(0, value.length());
                value.append(String.format("%d%%", Math.round(stats.getMagicFind() * 100))).append("\n");
                value.append(String.format("%d%%", Math.round(stats.getGoldFind() * 100)));
                mAdventureName.setText(name.toString());
                mAdventureValue.setText(value.toString());
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
        new ScreenShotTask(mStatLayout, mBattleTag.getBattleTag() + "_" + mHero.getName() + "_" + "statistics_" + System.currentTimeMillis(), "hero statistics").execute();
    }
}