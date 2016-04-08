package com.woozam.wdthelper.app.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.woozam.wdthelper.R;
import com.woozam.wdthelper.app.view.CalculatorDSPItemDialog;
import com.woozam.wdthelper.data.CalculatorDPSItem;

import java.util.ArrayList;

/**
 * Created by woozam on 2016-04-06.
 */
public class CalculatorDPSFragment extends Fragment implements OnClickListener, OnEditorActionListener, TextWatcher {

    private View mRoot;
    private ArrayList<CalculatorDPSItem> mCalculatorDPSItemList;
    private CalculatorDPSAdapter mAdapter;
    private ListView mListView;
    private ViewGroup mHeader;
    private View mHelp;
    private TextView mHeaderMainStatName;
    private TextView mHeaderAttackSpeedName;
    private TextView mHeaderCriticalChanceName;
    private TextView mHeaderCriticalDamageName;
    private TextView mHeaderDPSName;
    private AppCompatEditText mHeaderMainStat;
    private AppCompatEditText mHeaderAttackSpeed;
    private AppCompatEditText mHeaderCriticalChance;
    private AppCompatEditText mHeaderCriticalDamage;
    private AppCompatEditText mHeaderDPS;
    private ViewGroup mFooter;
    private View mAdd;
    private ViewGroup mResult;
    private TextView mResultName;
    private AppCompatEditText mResultValue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_calculator_dps, container, false);
        mCalculatorDPSItemList = new ArrayList<>();
        mAdapter = new CalculatorDPSAdapter(getContext(), mCalculatorDPSItemList, this);
        mListView = (ListView) mRoot.findViewById(R.id.calculator_dps_list_view);
        mHeader = (ViewGroup) inflater.inflate(R.layout.header_calculator_dps, null, false);
        mHeader.getChildAt(1).setBackgroundColor(0xff171614);
        mHeader.getChildAt(2).setBackgroundColor(0xff12110f);
        mHeader.getChildAt(3).setBackgroundColor(0xff171614);
        mHeader.getChildAt(4).setBackgroundColor(0xff12110f);
        mHeader.getChildAt(5).setBackgroundColor(0xff171614);
        mHelp = mHeader.getChildAt(0).findViewById(R.id.calculator_dps_header_help);
        mHelp.setOnClickListener(this);
        mHeaderMainStatName = (TextView) mHeader.getChildAt(1).findViewById(R.id.calculator_dps_name);
        mHeaderAttackSpeedName = (TextView) mHeader.getChildAt(2).findViewById(R.id.calculator_dps_name);
        mHeaderCriticalChanceName = (TextView) mHeader.getChildAt(3).findViewById(R.id.calculator_dps_name);
        mHeaderCriticalDamageName = (TextView) mHeader.getChildAt(4).findViewById(R.id.calculator_dps_name);
        mHeaderDPSName = (TextView) mHeader.getChildAt(5).findViewById(R.id.calculator_dps_name);
        mHeaderMainStat = (AppCompatEditText) mHeader.getChildAt(1).findViewById(R.id.calculator_dps_value);
        mHeaderAttackSpeed = (AppCompatEditText) mHeader.getChildAt(2).findViewById(R.id.calculator_dps_value);
        mHeaderCriticalChance = (AppCompatEditText) mHeader.getChildAt(3).findViewById(R.id.calculator_dps_value);
        mHeaderCriticalDamage = (AppCompatEditText) mHeader.getChildAt(4).findViewById(R.id.calculator_dps_value);
        mHeaderDPS = (AppCompatEditText) mHeader.getChildAt(5).findViewById(R.id.calculator_dps_value);
        mHeaderMainStatName.setText("주 스탯");
        mHeaderAttackSpeedName.setText("공격 속도");
        mHeaderCriticalChanceName.setText("극대화 확률");
        mHeaderCriticalDamageName.setText("극대화 피해");
        mHeaderDPSName.setText("DSP");
        mHeaderMainStat.setHint("주 스탯");
        mHeaderAttackSpeed.setHint("공격 속도");
        mHeaderCriticalChance.setHint("극대화 확률(%)");
        mHeaderCriticalDamage.setHint("극대화 피해(%)");
        mHeaderDPS.setHint("DSP");
        mHeaderDPS.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mHeaderMainStat.setOnEditorActionListener(this);
        mHeaderAttackSpeed.setOnEditorActionListener(this);
        mHeaderCriticalChance.setOnEditorActionListener(this);
        mHeaderCriticalDamage.setOnEditorActionListener(this);
        mHeaderMainStat.addTextChangedListener(this);
        mHeaderAttackSpeed.addTextChangedListener(this);
        mHeaderCriticalChance.addTextChangedListener(this);
        mHeaderCriticalDamage.addTextChangedListener(this);
        mHeaderDPS.addTextChangedListener(this);
        mFooter = (ViewGroup) inflater.inflate(R.layout.footer_calculator_dps, null, false);
        mFooter.setBackgroundColor(mAdapter.getCount() % 2 == 1 ? 0xff171614 : 0xff12110f);
        mAdd = mFooter.findViewById(R.id.footer_calculator_dps);
        mAdd.setOnClickListener(this);
        mResult = (ViewGroup) inflater.inflate(R.layout.row_calculator_dps, null, false);
        mResult.setBackgroundColor(mAdapter.getCount() % 2 == 1 ? 0xff12110f : 0xff171614);
        mResultName = (TextView) mResult.findViewById(R.id.calculator_dps_name);
        mResultValue = (AppCompatEditText) mResult.findViewById(R.id.calculator_dps_value);
        mResultName.setText("계산 결과");
        mResultValue.setEnabled(false);
        mListView.addHeaderView(mHeader);
        mListView.addFooterView(mFooter);
        mListView.addFooterView(mResult);
        mListView.setAdapter(mAdapter);
        calculate();
        return mRoot;
    }

    @Override
    public void onClick(View v) {
        if (v == mAdd) {
            add();
        } else if (v == mHelp) {
        } else if (v.getId() == R.id.calculator_dps_remove) {
            CalculatorDPSItem item = (CalculatorDPSItem) v.getTag();
            mCalculatorDPSItemList.remove(item);
            mAdapter.notifyDataSetChanged();
            calculate();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            if (v == mHeaderMainStat) {
                mHeaderAttackSpeed.requestFocus();
            } else if (v == mHeaderAttackSpeed) {
                mHeaderCriticalChance.requestFocus();
            } else if (v == mHeaderCriticalChance) {
                mHeaderCriticalDamage.requestFocus();
            } else if (v == mHeaderCriticalDamage) {
                mHeaderDPS.requestFocus();
            }
            return true;
        } else {
            return false;
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

    private void add() {
        new CalculatorDSPItemDialog(getContext(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    CalculatorDPSItem item = ((CalculatorDSPItemDialog) dialog).getItem();
                    mCalculatorDPSItemList.add(item);
                    mAdapter.notifyDataSetChanged();
                    calculate();
                }
            }
        }).show();
    }

    private void calculate() {
        try {
            float mainStat = getFloatValue(mHeaderMainStat);
            float attackSpeed = getFloatValue(mHeaderAttackSpeed);
            float criticalChance = fromPercent(getFloatValue(mHeaderCriticalChance)) - 1;
            float criticalDamage = fromPercent(getFloatValue(mHeaderCriticalDamage));
            float dsp = getFloatValue(mHeaderDPS);
            float defaultDsp = dsp / (((mainStat / 100 + 1) * (criticalChance * criticalDamage + 1 * (1 - criticalChance))) * attackSpeed);

            for (CalculatorDPSItem item : mCalculatorDPSItemList) {
                switch (item.getType()) {
                    case CalculatorDPSItem.TYPE_PARAGON:
                        mainStat += item.getStat();
                        break;
                    case CalculatorDPSItem.TYPE_CALDESANN:
                        mainStat += item.getStat();
                        break;
                    case CalculatorDPSItem.TYPE_ATTACK_SPEED:
                        attackSpeed *= (item.getStat() / 100f + 1);
                        break;
                    case CalculatorDPSItem.TYPE_CRITICAL_CHANCE:
                        criticalChance += (item.getStat() / 100f);
                        break;
                    case CalculatorDPSItem.TYPE_CRITICAL_DAMAGE:
                        criticalDamage += (item.getStat() / 100f);
                        break;
                }
            }

            float result = Math.round(defaultDsp * attackSpeed * (mainStat / 100 + 1) * ((1 - criticalChance) + criticalDamage * criticalChance) * 10) / 10f;

            mResultValue.setText(String.valueOf(result));
        } catch (Exception ignored) {
        }
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
}