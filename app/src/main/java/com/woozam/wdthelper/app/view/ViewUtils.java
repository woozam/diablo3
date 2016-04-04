package com.woozam.wdthelper.app.view;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.Toast;

import com.woozam.wdthelper.common.CommonUtils;

/**
 * Created by woozam on 2016-01-31.
 */
public class ViewUtils {

    private static final long[] ERROR_VIBRATE_PATTERN = {0, 80, 80, 200};

    public static void setError(Context context, EditText editText, CharSequence error) {
        if (error != null) {
            Toast.makeText(context, error, Toast.LENGTH_LONG).show();
        }
        D3Vibrator.getsInstance().vibrate(ERROR_VIBRATE_PATTERN, -1);
        editText.startAnimation(getShakeHorizontalAnimation(0));
        CommonUtils.showSoftInput(context, editText);
    }

    public static Animation getShakeHorizontalAnimation(int startOffset) {
        final AnimationSet aniSet = new AnimationSet(false);
        int length = CommonUtils.convertDipToPx(3);
        int duration = 40;
        TranslateAnimation a1 = new TranslateAnimation(startOffset, -length + startOffset, 0, 0);
        a1.setDuration(duration);
        TranslateAnimation a2 = new TranslateAnimation(-length + startOffset, length + startOffset, 0, 0);
        a2.setDuration(duration * 2);
        a2.setStartOffset(duration);
        TranslateAnimation a3 = new TranslateAnimation(length + startOffset, startOffset, 0, 0);
        a3.setDuration(duration);
        a3.setStartOffset(duration * 3);
        TranslateAnimation a4 = new TranslateAnimation(startOffset, -length + startOffset, 0, 0);
        a4.setDuration(duration);
        a4.setStartOffset(duration * 4);
        TranslateAnimation a5 = new TranslateAnimation(-length + startOffset, length + startOffset, 0, 0);
        a5.setDuration(duration * 2);
        a5.setStartOffset(duration * 5);
        TranslateAnimation a6 = new TranslateAnimation(length + startOffset, startOffset, 0, 0);
        a6.setDuration(duration);
        a6.setStartOffset(duration * 7);
        aniSet.addAnimation(a1);
        aniSet.addAnimation(a2);
        aniSet.addAnimation(a3);
        aniSet.addAnimation(a4);
        aniSet.addAnimation(a5);
        aniSet.addAnimation(a6);
        return aniSet;
    }
}