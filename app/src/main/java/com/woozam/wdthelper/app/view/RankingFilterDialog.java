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

    private AppCompatSpinner mServerSpiner;
    private AppCompatSpinner mTypeSpiner;
    private AppCompatSpinner mSeasonSpiner;
    private CheckBox mSeasonCheckBox;
    private CheckBox mHardcoreCheckBox;

    public RankingFilterDialog(Context context, OnClickListener onClickListener) {
        super(context, R.style.AlertDialog);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_ranking_filter, null);
        mServerSpiner = (AppCompatSpinner) view.findViewById(R.id.filter_server);
        mServerSpiner.setAdapter(new ServerSpinnerAdapter(getContext(), Server.toArrayList()));
        mTypeSpiner = (AppCompatSpinner) view.findViewById(R.id.filter_type);
        mTypeSpiner.setAdapter(new RiftTypeSpinnerAdapter(getContext(), RiftType.toArrayList()));
        mSeasonSpiner = (AppCompatSpinner) view.findViewById(R.id.filter_season);
        mSeasonSpiner.setAdapter(new RiftSeasonSpinnerAdapter(getContext(), RiftSeason.toArrayList()));
        mSeasonCheckBox = (CheckBox) view.findViewById(R.id.filter_is_season);
        mHardcoreCheckBox = (CheckBox) view.findViewById(R.id.filter_is_hardcore);
        setView(view);
        setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.ok), onClickListener);
        setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.cancel), onClickListener);
    }

    public RankingParameter getParameter() {
        RankingParameter params = new RankingParameter();
        params.server = (Server) mServerSpiner.getSelectedItem();
        params.type = (RiftType) mTypeSpiner.getSelectedItem();
        params.season = (RiftSeason) mSeasonSpiner.getSelectedItem();
        params.isSeason = mSeasonCheckBox.isChecked();
        params.isHardcore = mHardcoreCheckBox.isChecked();
        return params;
    }

    public RankingFilterDialog setParameter(RankingParameter params) {
        mServerSpiner.setSelection(Server.toArrayList().indexOf(params.server));
        mTypeSpiner.setSelection(RiftType.toArrayList().indexOf(params.type));
        mSeasonSpiner.setSelection(RiftSeason.toArrayList().indexOf(params.season));
        mSeasonCheckBox.setChecked(params.isSeason);
        mHardcoreCheckBox.setChecked(params.isHardcore);
        return this;
    }
}