package com.woozam.wdthelper.app.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.woozam.wdthelper.R;
import com.woozam.wdthelper.common.CommonUtils;
import com.woozam.wdthelper.data.CalculatorDPSItem;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by woozam on 2016-04-07.
 */
public class CalculatorDSPItemDialog extends AlertDialog implements OnItemSelectedListener, TextWatcher {

    private CalculatorDSPItemTypeAdapter mAdapter;
    private AppCompatSpinner mType;
    private ViewGroup mRow1;
    private ViewGroup mRow2;
    private ViewGroup mRow3;
    private TextView mRow1Name;
    private TextView mRow2Name;
    private TextView mRow3Name;
    private EditText mRow1Value;
    private EditText mRow2Value;
    private EditText mRow3Value;

    public CalculatorDSPItemDialog(Context context, OnClickListener onClickListener) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_calculator_dps_item, null);
        ArrayList<CalculatorDPSItem> itemList = new ArrayList<>();
        itemList.add(new CalculatorDPSItem(CalculatorDPSItem.TYPE_PARAGON, 0));
        itemList.add(new CalculatorDPSItem(CalculatorDPSItem.TYPE_CALDESANN, 0));
        itemList.add(new CalculatorDPSItem(CalculatorDPSItem.TYPE_ATTACK_SPEED, 0));
        itemList.add(new CalculatorDPSItem(CalculatorDPSItem.TYPE_CRITICAL_CHANCE, 0));
        itemList.add(new CalculatorDPSItem(CalculatorDPSItem.TYPE_CRITICAL_DAMAGE, 0));
        mAdapter = new CalculatorDSPItemTypeAdapter(context, itemList);
        mType = (AppCompatSpinner) view.findViewById(R.id.calculator_dps_item_type);
        mType.setAdapter(mAdapter);
        mType.setOnItemSelectedListener(this);
        mRow1 = (ViewGroup) view.findViewById(R.id.calculator_dps_item_row_1);
        mRow2 = (ViewGroup) view.findViewById(R.id.calculator_dps_item_row_2);
        mRow3 = (ViewGroup) view.findViewById(R.id.calculator_dps_item_row_3);
        mRow1.setPadding(CommonUtils.convertDipToPx(getContext(), 2), 0, 0, 0);
        mRow2.setPadding(CommonUtils.convertDipToPx(getContext(), 2), 0, 0, 0);
        mRow3.setPadding(CommonUtils.convertDipToPx(getContext(), 2), 0, 0, 0);
        mRow1Name = (TextView) mRow1.findViewById(R.id.calculator_dps_name);
        mRow2Name = (TextView) mRow2.findViewById(R.id.calculator_dps_name);
        mRow3Name = (TextView) mRow3.findViewById(R.id.calculator_dps_name);
        mRow1Value = (EditText) mRow1.findViewById(R.id.calculator_dps_value);
        mRow2Value = (EditText) mRow2.findViewById(R.id.calculator_dps_value);
        mRow3Value = (EditText) mRow3.findViewById(R.id.calculator_dps_value);
        mRow3Value.setEnabled(false);
        mRow1Value.addTextChangedListener(this);
        mRow2Value.addTextChangedListener(this);
        mRow3Name.setText("추가 스탯");
        mRow2.setVisibility(View.GONE);
        mRow3.setVisibility(View.GONE);
        setView(view);
        setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.ok), onClickListener);
        setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.cancel), onClickListener);
    }

    private void setType(CalculatorDPSItem item) {
        mRow2.setVisibility(View.GONE);
        mRow3.setVisibility(View.GONE);
        switch (item.getType()) {
            case CalculatorDPSItem.TYPE_PARAGON:
                mRow3.setVisibility(View.VISIBLE);
                mRow1Name.setText("추가 정복자 레벨");
                mRow1Value.setText(null);
                mRow1Value.setHint("레벨");
                break;
            case CalculatorDPSItem.TYPE_CALDESANN:
                mRow2.setVisibility(View.VISIBLE);
                mRow3.setVisibility(View.VISIBLE);
                mRow1Name.setText("이전 칼데산 등급");
                mRow1Value.setText(null);
                mRow1Value.setHint("없음");
                mRow2Name.setText("새로운 칼데산 등급");
                mRow2Value.setText(null);
                mRow2Value.setHint("등급");
                break;
            case CalculatorDPSItem.TYPE_ATTACK_SPEED:
                mRow1Name.setText("공격 속도");
                mRow1Value.setText(null);
                mRow1Value.setHint("공격 속도(%)");
                break;
            case CalculatorDPSItem.TYPE_CRITICAL_CHANCE:
                mRow1Name.setText("극대화 확률");
                mRow1Value.setText(null);
                mRow1Value.setHint("극대화 확률(%)");
                break;
            case CalculatorDPSItem.TYPE_CRITICAL_DAMAGE:
                mRow1Name.setText("극대화 피해");
                mRow1Value.setText(null);
                mRow1Value.setHint("극대화 피해(%)");
                break;
        }
        calculate();
    }

    public CalculatorDPSItem getItem() {
        return (CalculatorDPSItem) mType.getSelectedItem();
    }

    private void calculate() {
        CalculatorDPSItem item = (CalculatorDPSItem) mType.getSelectedItem();
        float result = 0;
        try {
            switch (item.getType()) {
                case CalculatorDPSItem.TYPE_PARAGON:
                    result = getFloatValue(mRow1Value) * 5;
                    break;
                case CalculatorDPSItem.TYPE_CALDESANN:
                    result = (getFloatValue(mRow2Value) - getFloatValue(mRow1Value)) * 5;
                    break;
                case CalculatorDPSItem.TYPE_ATTACK_SPEED:
                    result = getFloatValue(mRow1Value);
                    break;
                case CalculatorDPSItem.TYPE_CRITICAL_CHANCE:
                    result = getFloatValue(mRow1Value);
                    break;
                case CalculatorDPSItem.TYPE_CRITICAL_DAMAGE:
                    result = getFloatValue(mRow1Value);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        item.setStat(result);
        mRow3Value.setText(item.getStatString());
    }

    private float getFloatValue(EditText editText) {
        try {
            return Float.parseFloat(editText.getText().toString());
        } catch (Exception ignored) {
        }
        return 0;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CalculatorDPSItem item = mAdapter.getItem(position);
        item.setStat(0);
        setType(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
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

    public class CalculatorDSPItemTypeAdapter extends ArrayAdapter<CalculatorDPSItem> {

        public CalculatorDSPItemTypeAdapter(Context context, Collection<CalculatorDPSItem> itemList) {
            super(context, R.layout.row_server_spinner, R.id.server_spinner_text_view);
            addAll(itemList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);
            TextView textView = (TextView) convertView.findViewById(R.id.server_spinner_text_view);
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            textView.setText(getItem(position).getName());
            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            convertView = super.getDropDownView(position, convertView, parent);
            TextView textView = (TextView) convertView.findViewById(R.id.server_spinner_text_view);
            textView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.primary_text_light));
            textView.setText(getItem(position).getName());
            return convertView;
        }
    }
}
