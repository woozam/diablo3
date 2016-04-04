package com.woozam.wdthelper.app.view;

import android.content.Context;
import android.os.Vibrator;

import com.woozam.wdthelper.D3Application;

/**
 * Created by woozam on 2016-01-31.
 */
public class D3Vibrator {

    private static D3Vibrator sInstance;

    public static D3Vibrator getsInstance() {
        if (sInstance == null) {
            synchronized (Vibrator.class) {
                if (sInstance == null) {
                    sInstance = new D3Vibrator();
                }
            }
        }
        return sInstance;
    }

    private Vibrator mVibrator;

    private D3Vibrator() {
        mVibrator = (Vibrator) D3Application.getContext().getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void vibrate(long milliseconds) {
        if (mVibrator != null) {
            mVibrator.vibrate(milliseconds);
        }
    }

    public void vibrate(long[] pattern, int repeat) {
        if (mVibrator != null) {
            mVibrator.vibrate(pattern, repeat);
        }
    }
}