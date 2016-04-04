package com.woozam.wdthelper;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;

/**
 * Created by woozam on 2016-01-31.
 */
public class ProgressDrawable extends AnimationDrawable {

    public ProgressDrawable(Context context) {
        super();
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_01), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_02), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_03), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_04), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_05), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_06), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_07), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_08), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_09), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_10), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_11), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_12), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_13), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_14), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_15), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_16), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_17), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_18), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_19), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_20), 33);
        addFrame(ContextCompat.getDrawable(context, R.mipmap.spinner_21), 33);
        setOneShot(false);
    }
}