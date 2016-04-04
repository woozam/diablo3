package com.woozam.wdthelper.app.activity;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.woozam.wdthelper.R;
import com.woozam.wdthelper.data.RiftSeason;

import java.util.Collection;

/**
 * Created by woozam on 2016-02-13.
 */
public class RiftSeasonSpinnerAdapter extends ArrayAdapter<RiftSeason> {

    public RiftSeasonSpinnerAdapter(Context context, Collection<RiftSeason> servers) {
        super(context, R.layout.row_server_spinner, R.id.server_spinner_text_view);
        addAll(servers);
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