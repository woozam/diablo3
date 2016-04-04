package com.woozam.wdthelper.data;

import android.content.Context;

import com.woozam.wdthelper.D3Application;
import com.woozam.wdthelper.R;

import java.io.Serializable;
import java.util.ArrayList;

public enum Server implements Serializable {

    US, EU, KR, TW;

    public String toString() {
        switch (this) {
            case EU:
                return "eu";
            case KR:
                return "kr";
            case TW:
                return "tw";
            case US:
                return "us";
            default:
                return "";
        }
    }

    public String getName() {
        Context context = D3Application.getContext();
        switch (this) {
            case EU:
                return context.getString(R.string.europe);
            case KR:
                return context.getString(R.string.korea);
            case TW:
                return context.getString(R.string.taiwan);
            case US:
                return context.getString(R.string.us);
            default:
                return "";
        }
    }

    private static ArrayList<Server> list = new ArrayList<>();

    static {
        list.add(US);
        list.add(EU);
        list.add(KR);
        list.add(TW);
    }

    public static ArrayList<Server> toArrayList() {
        return list;
    }
}