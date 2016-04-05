package com.woozam.wdthelper.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.google.android.gms.ads.AdView;
import com.woozam.wdthelper.D3Application;
import com.woozam.wdthelper.R;
import com.woozam.wdthelper.common.ADUtils;
import com.woozam.wdthelper.common.ScreenShotTask;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by woozam on 2016-02-06.
 */
public class EnchantWeaponFragment extends Fragment implements TextWatcher, OnItemSelectedListener, OnClickListener {

    private static final int DAMAGE_MIN_ONE_HAND = 1199;
    private static final int DAMAGE_MAX_ONE_HAND = 1490;
    private static final int DAMAGE_MIN_ONE_HAND_ANCIENT = 1560;
    private static final int DAMAGE_MAX_ONE_HAND_ANCIENT = 1940;
    private static final int DAMAGE_MIN_TWO_HAND = 1439;
    private static final int DAMAGE_MAX_TWO_HAND = 1788;
    private static final int DAMAGE_MIN_TWO_HAND_ANCIENT = 1870;
    private static final int DAMAGE_MAX_TWO_HAND_ANCIENT = 2325;
    private static final int DAMAGE_MIN_HAND_CROSSBOW = 1049;
    private static final int DAMAGE_MAX_HAND_CROSSBOW = 1304;
    private static final int DAMAGE_MIN_HAND_CROSSBOW_ANCIENT = 1365;
    private static final int DAMAGE_MAX_HAND_CROSSBOW_ANCIENT = 1700;

    private enum WeaponType {
        ONE_HAND, ONE_HAND_ANCIENT, TWO_HAND, TWO_HAND_ANCIENT, HAND_CROSSBOW, HAND_CROSSBOW_ANCIENT;

        @Override
        public String toString() {
            Context context = D3Application.getContext();
            switch (this) {
                case ONE_HAND:
                    return context.getString(R.string.weapon_one_hand);
                case ONE_HAND_ANCIENT:
                    return context.getString(R.string.weapon_one_hand_ancient);
                case TWO_HAND:
                    return context.getString(R.string.weapon_two_hand);
                case TWO_HAND_ANCIENT:
                    return context.getString(R.string.weapon_two_hand_ancient);
                case HAND_CROSSBOW:
                    return context.getString(R.string.weapon_hand_crossbow);
                case HAND_CROSSBOW_ANCIENT:
                    return context.getString(R.string.weapon_hand_crossbow_ancient);
            }
            return super.toString();
        }
    }

    private ScrollView mRoot;
    private View mLayout;

    private AppCompatSpinner mWeaponType;
    private ArrayList<WeaponType> mWeaponTypeList;
    private WeaponTypeSpinnerAdapter mWeaponTypeAdapter;

    private AppCompatEditText mWeaponDsp;
    private AppCompatEditText mWeaponAttackSpeed;

    private AppCompatEditText mWeaponAddDamageMin;
    private AppCompatEditText mWeaponAddDamageMax;
    private AppCompatEditText mWeaponDamagePercent;
    private AppCompatEditText mWeaponAttackSpeedPercent;

    private View mWeaponResultAttackSpeedPercentToDamagePercentText;
    private View mWeaponResultDamagePercentToAttackSpeedPercentText;
    private View mWeaponResultRemoveDamagePercentText;
    private View mWeaponResultRemoveAttackSpeedPercentText;

    private TextView mWeaponResultDamage;
    private TextView mWeaponResultDamagePercent;
    private TextView mWeaponResultAttackSpeedPercent;
    private TextView mWeaponResultAttackSpeedPercentToDamagePercent;
    private TextView mWeaponResultDamagePercentToAttackSpeedPercent;
    private TextView mWeaponResultRemoveDamagePercent;
    private TextView mWeaponResultRemoveAttackSpeedPercent;

    private View mHelp;

    private AdView mAdView;

    public EnchantWeaponFragment() {
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
        mRoot = (ScrollView) inflater.inflate(R.layout.fragment_enchant_weapon, container, false);
        mLayout = mRoot.findViewById(R.id.enchant_weapon_layout);
        mWeaponType = (AppCompatSpinner) mRoot.findViewById(R.id.enchant_weapon_type);
        mWeaponDsp = (AppCompatEditText) mRoot.findViewById(R.id.enchant_weapon_dps);
        mWeaponAttackSpeed = (AppCompatEditText) mRoot.findViewById(R.id.enchant_weapon_attack_speed);
        mWeaponAddDamageMin = (AppCompatEditText) mRoot.findViewById(R.id.enchant_weapon_current_add_damage_min);
        mWeaponAddDamageMax = (AppCompatEditText) mRoot.findViewById(R.id.enchant_weapon_current_add_damage_max);
        mWeaponDamagePercent = (AppCompatEditText) mRoot.findViewById(R.id.enchant_weapon_current_damage_percent);
        mWeaponAttackSpeedPercent = (AppCompatEditText) mRoot.findViewById(R.id.enchant_weapon_current_attack_speed_percent);
        mWeaponResultAttackSpeedPercentToDamagePercentText = mRoot.findViewById(R.id.enchant_weapon_preview_attack_speed_percent_to_damage_percent_max_text);
        mWeaponResultDamagePercentToAttackSpeedPercentText = mRoot.findViewById(R.id.enchant_weapon_preview_damage_percent_to_attack_speed_percent_max_text);
        mWeaponResultRemoveDamagePercentText = mRoot.findViewById(R.id.enchant_weapon_preview_remove_damage_percent_text);
        mWeaponResultRemoveAttackSpeedPercentText = mRoot.findViewById(R.id.enchant_weapon_preview_remove_attack_speed_percent_text);
        mWeaponResultDamage = (TextView) mRoot.findViewById(R.id.enchant_weapon_preview_add_damage_max);
        mWeaponResultDamagePercent = (TextView) mRoot.findViewById(R.id.enchant_weapon_preview_damage_percent_max);
        mWeaponResultAttackSpeedPercent = (TextView) mRoot.findViewById(R.id.enchant_weapon_preview_attack_speed_percent_max);
        mWeaponResultAttackSpeedPercentToDamagePercent = (TextView) mRoot.findViewById(R.id.enchant_weapon_preview_attack_speed_percent_to_damage_percent_max);
        mWeaponResultDamagePercentToAttackSpeedPercent = (TextView) mRoot.findViewById(R.id.enchant_weapon_preview_damage_percent_to_attack_speed_percent_max);
        mWeaponResultRemoveDamagePercent = (TextView) mRoot.findViewById(R.id.enchant_weapon_preview_remove_damage_percent);
        mWeaponResultRemoveAttackSpeedPercent = (TextView) mRoot.findViewById(R.id.enchant_weapon_preview_remove_attack_speed_percent);
        mHelp = mRoot.findViewById(R.id.enchant_weapon_help);
        mAdView = (AdView) mRoot.findViewById(R.id.enchant_weapon_ad_view);
        if (!ADUtils.USE_AD) {
            mAdView.setVisibility(View.GONE);
        }
        ADUtils.requestAD(mAdView);

        mWeaponTypeList = new ArrayList<>();
        mWeaponTypeList.add(WeaponType.ONE_HAND);
        mWeaponTypeList.add(WeaponType.ONE_HAND_ANCIENT);
        mWeaponTypeList.add(WeaponType.TWO_HAND);
        mWeaponTypeList.add(WeaponType.TWO_HAND_ANCIENT);
        mWeaponTypeList.add(WeaponType.HAND_CROSSBOW);
        mWeaponTypeList.add(WeaponType.HAND_CROSSBOW_ANCIENT);
        mWeaponTypeAdapter = new WeaponTypeSpinnerAdapter(getContext(), mWeaponTypeList);
        mWeaponType.setAdapter(mWeaponTypeAdapter);
        mWeaponType.setSelection(1);

        mWeaponType.setOnItemSelectedListener(this);
        mWeaponDsp.addTextChangedListener(this);
        mWeaponAttackSpeed.addTextChangedListener(this);
        mWeaponAddDamageMin.addTextChangedListener(this);
        mWeaponAddDamageMax.addTextChangedListener(this);
        mWeaponDamagePercent.addTextChangedListener(this);
        mWeaponAttackSpeedPercent.addTextChangedListener(this);
        mHelp.setOnClickListener(this);
        return mRoot;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdView.destroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mAdView.pause();
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

    @Override
    public void onClick(View v) {
        if (v == mHelp) {
            new Builder(getContext()).title(R.string.weapon_information).customView(R.layout.enchant_weapon_help, true).positiveText(R.string.ok).show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        calculate();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        calculate();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void calculate() {
        try {
            WeaponType weaponType = (WeaponType) mWeaponType.getSelectedItem();

            float dsp = getFloatValue(mWeaponDsp);
            float attackSpeed = getFloatValue(mWeaponAttackSpeed);
            float addDamageMin = getFloatValue(mWeaponAddDamageMin);
            float addDamageMax = getFloatValue(mWeaponAddDamageMax);
            float damagePercent = fromPercent(getFloatValue(mWeaponDamagePercent));
            float attackSpeedPercent = fromPercent(getFloatValue(mWeaponAttackSpeedPercent));

            if (damagePercent != 1 && attackSpeedPercent == 1) {
                mWeaponResultDamagePercentToAttackSpeedPercentText.setVisibility(View.VISIBLE);
                mWeaponResultDamagePercentToAttackSpeedPercent.setVisibility(View.VISIBLE);
            } else {
                mWeaponResultDamagePercentToAttackSpeedPercentText.setVisibility(View.GONE);
                mWeaponResultDamagePercentToAttackSpeedPercent.setVisibility(View.GONE);
            }

            if (attackSpeedPercent != 1 && damagePercent == 1) {
                mWeaponResultAttackSpeedPercentToDamagePercentText.setVisibility(View.VISIBLE);
                mWeaponResultAttackSpeedPercentToDamagePercent.setVisibility(View.VISIBLE);
            } else {
                mWeaponResultAttackSpeedPercentToDamagePercentText.setVisibility(View.GONE);
                mWeaponResultAttackSpeedPercentToDamagePercent.setVisibility(View.GONE);
            }

            if (damagePercent != 1) {
                mWeaponResultRemoveDamagePercentText.setVisibility(View.VISIBLE);
                mWeaponResultRemoveDamagePercent.setVisibility(View.VISIBLE);
            } else {
                mWeaponResultRemoveDamagePercentText.setVisibility(View.GONE);
                mWeaponResultRemoveDamagePercent.setVisibility(View.GONE);
            }

            if (attackSpeedPercent != 1) {
                mWeaponResultRemoveAttackSpeedPercentText.setVisibility(View.VISIBLE);
                mWeaponResultRemoveAttackSpeedPercent.setVisibility(View.VISIBLE);
            } else {
                mWeaponResultRemoveAttackSpeedPercentText.setVisibility(View.GONE);
                mWeaponResultRemoveAttackSpeedPercent.setVisibility(View.GONE);
            }

            float defaultDsp = dsp / damagePercent / attackSpeed - (addDamageMax + addDamageMin) / 2;
            float defaultAttackSpeed = attackSpeed / attackSpeedPercent;

            float previewAddDamageMin = getDamageMin(weaponType);
            float previewAddDamageMax = getDamageMax(weaponType);
            float previewDamagePercent = 1.1f;
            float previewAttackSpeedPercent = 1.07f;

            float result1 = Math.round((defaultDsp + (previewAddDamageMin + previewAddDamageMax) / 2) * defaultAttackSpeed * damagePercent * attackSpeedPercent * 10) / 10f;
            float result2 = Math.round((defaultDsp + (addDamageMin + addDamageMax) / 2) * defaultAttackSpeed * previewDamagePercent * attackSpeedPercent * 10) / 10f;
            float result3 = Math.round((defaultDsp + (addDamageMin + addDamageMax) / 2) * defaultAttackSpeed * damagePercent * previewAttackSpeedPercent * 10) / 10f;
            float result4 = Math.round((defaultDsp + (addDamageMin + addDamageMax) / 2) * defaultAttackSpeed * previewDamagePercent * 1 * 10) / 10f;
            float result5 = Math.round((defaultDsp + (addDamageMin + addDamageMax) / 2) * defaultAttackSpeed * 1 * previewAttackSpeedPercent * 10) / 10f;
            float result6 = Math.round((defaultDsp + (addDamageMin + addDamageMax) / 2) * defaultAttackSpeed * 1 * attackSpeedPercent * 10) / 10f;
            float result7 = Math.round((defaultDsp + (addDamageMin + addDamageMax) / 2) * defaultAttackSpeed * 1 * damagePercent * 10) / 10f;

            mWeaponResultDamage.setText(String.valueOf(result1));
            mWeaponResultDamagePercent.setText(String.valueOf(result2));
            mWeaponResultAttackSpeedPercent.setText(String.valueOf(result3));
            mWeaponResultAttackSpeedPercentToDamagePercent.setText(String.valueOf(result4));
            mWeaponResultDamagePercentToAttackSpeedPercent.setText(String.valueOf(result5));
            mWeaponResultRemoveDamagePercent.setText(String.valueOf(result6));
            mWeaponResultRemoveAttackSpeedPercent.setText(String.valueOf(result7));
        } catch (Exception ignored) {
        }
    }

    private float getDamageMax(WeaponType weaponType) {
        switch (weaponType) {
            case ONE_HAND:
                return DAMAGE_MAX_ONE_HAND;
            case ONE_HAND_ANCIENT:
                return DAMAGE_MAX_ONE_HAND_ANCIENT;
            case TWO_HAND:
                return DAMAGE_MAX_TWO_HAND;
            case TWO_HAND_ANCIENT:
                return DAMAGE_MAX_TWO_HAND_ANCIENT;
            case HAND_CROSSBOW:
                return DAMAGE_MAX_HAND_CROSSBOW;
            case HAND_CROSSBOW_ANCIENT:
                return DAMAGE_MAX_HAND_CROSSBOW_ANCIENT;
        }
        return 0;
    }

    private float getDamageMin(WeaponType weaponType) {
        switch (weaponType) {
            case ONE_HAND:
                return DAMAGE_MIN_ONE_HAND;
            case ONE_HAND_ANCIENT:
                return DAMAGE_MIN_ONE_HAND_ANCIENT;
            case TWO_HAND:
                return DAMAGE_MIN_TWO_HAND;
            case TWO_HAND_ANCIENT:
                return DAMAGE_MIN_TWO_HAND_ANCIENT;
            case HAND_CROSSBOW:
                return DAMAGE_MIN_HAND_CROSSBOW;
            case HAND_CROSSBOW_ANCIENT:
                return DAMAGE_MIN_HAND_CROSSBOW_ANCIENT;
        }
        return 0;
    }

    private float getFloatValue(AppCompatEditText editText) {
        try {
            return Float.valueOf(editText.getText().toString());
        } catch (Exception ignored) {
            return 0;
        }
    }

    private float fromPercent(float value) {
        return (value + 100) / 100;
    }

    public void saveScreenShot() {
        if (mAdView != null) {
            mAdView.setVisibility(View.GONE);
        }
        new ScreenShotTask(mLayout, "enchant_" + System.currentTimeMillis(), "enchants").execute();
    }

    private class WeaponTypeSpinnerAdapter extends ArrayAdapter<WeaponType> {

        public WeaponTypeSpinnerAdapter(Context context, Collection<WeaponType> battleTags) {
            super(context, R.layout.row_weapon_type_spinner, R.id.weapon_type_text_view);
            addAll(battleTags);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);
            TextView textView = (TextView) convertView.findViewById(R.id.weapon_type_text_view);
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            convertView = super.getDropDownView(position, convertView, parent);
            TextView textView = (TextView) convertView.findViewById(R.id.weapon_type_text_view);
            textView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.primary_text_light));
            return convertView;
        }
    }
}