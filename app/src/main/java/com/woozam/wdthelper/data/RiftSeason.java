package com.woozam.wdthelper.data;

import android.content.Context;

import com.woozam.wdthelper.D3Application;
import com.woozam.wdthelper.R;

import java.util.ArrayList;

/**
 * Created by woozam on 2016-02-13.
 */
public enum RiftSeason {
    SEASON_1, SEASON_2, SEASON_3, SEASON_4, SEASON_5;

    @Override
    public String toString() {
        switch (this) {
            case SEASON_1:
                return "1";
            case SEASON_2:
                return "2";
            case SEASON_3:
                return "3";
            case SEASON_4:
                return "4";
            case SEASON_5:
                return "5";
        }
        return super.toString();
    }

    public String getName() {
        Context context = D3Application.getContext();
        switch (this) {
            case SEASON_1:
                return context.getString(R.string.season_1);
            case SEASON_2:
                return context.getString(R.string.season_2);
            case SEASON_3:
                return context.getString(R.string.season_3);
            case SEASON_4:
                return context.getString(R.string.season_4);
            case SEASON_5:
                return context.getString(R.string.season_5);
            default:
                return "";
        }
    }

    private static ArrayList<RiftSeason> list = new ArrayList<>();

    static {
        list.add(SEASON_1);
        list.add(SEASON_2);
        list.add(SEASON_3);
        list.add(SEASON_4);
        list.add(SEASON_5);
    }

    public static ArrayList<RiftSeason> toArrayList() {
        return list;
    }
}