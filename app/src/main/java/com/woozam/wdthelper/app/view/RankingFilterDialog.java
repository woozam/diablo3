package com.woozam.wdthelper.app.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import com.woozam.wdthelper.R;
import com.woozam.wdthelper.app.activity.RiftSeasonSpinnerAdapter;
import com.woozam.wdthelper.app.activity.RiftTypeSpinnerAdapter;
import com.woozam.wdthelper.app.activity.ServerSpinnerAdapter;
import com.woozam.wdthelper.data.RankingParameter;
import com.woozam.wdthelper.data.RiftSeason;
import com.woozam.wdthelper.data.RiftType;
import com.woozam.wdthelper.data.Server;

/**
 * Created by woozam on 2016-02-13.
 */
public class RankingFilterDialog extends AlertDialog {

    private AppCompatSpinner mServerSpinner;
    private AppCompatSpinner mTypeSpinner;
    private AppCompatSpinner mSeasonSpinner;
    private CheckBox mSeasonCheckBox;
    private CheckBox mHardcoreCheckBox;

    public RankingFilterDialog(Context context, OnClickListener onClickListener) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_ranking_filter, null);
        mServerSpinner = (AppCompatSpinner) view.findViewById(R.id.filter_server);
        mServerSpinner.setAdapter(new ServerSpinnerAdapter(getContext(), Server.toArrayList()));
        mTypeSpinner = (AppCompatSpinner) view.findViewById(R.id.filter_type);
        mTypeSpinner.setAdapter(new RiftTypeSpinnerAdapter(getContext(), RiftType.toArrayList()));
        mSeasonSpinner = (AppCompatSpinner) view.findViewById(R.id.filter_season);
        mSeasonSpinner.setAdapter(new RiftSeasonSpinnerAdapter(getContext(), RiftSeason.toArrayList()));
        mSeasonCheckBox = (CheckBox) view.findViewById(R.id.filter_is_season);
        mHardcoreCheckBox = (CheckBox) view.findViewById(R.id.filter_is_hardcore);
        setView(view);
        setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.ok), onClickListener);
        setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.cancel), onClickListener);
    }

    public RankingParameter getParameter() {
        RankingParameter params = new RankingParameter();
        params.server = (Server) mServerSpinner.getSelectedItem();
        params.type = (RiftType) mTypeSpinner.getSelectedItem();
        params.season = (RiftSeason) mSeasonSpinner.getSelectedItem();
        params.isSeason = mSeasonCheckBox.isChecked();
        params.isHardcore = mHardcoreCheckBox.isChecked();
        return params;
    }

    public RankingFilterDialog setParameter(RankingParameter params) {
        mServerSpinner.setSelection(Server.toArrayList().indexOf(params.server));
        mTypeSpinner.setSelection(RiftType.toArrayList().indexOf(params.type));
        mSeasonSpinner.setSelection(RiftSeason.toArrayList().indexOf(params.season));
        mSeasonCheckBox.setChecked(params.isSeason);
        mHardcoreCheckBox.setChecked(params.isHardcore);
        return this;
    }
}