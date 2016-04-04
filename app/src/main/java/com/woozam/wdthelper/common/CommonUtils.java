package com.woozam.wdthelper.common;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.woozam.wdthelper.D3Application;

/**
 * Created by woozam on 2016-01-31.
 */
public class CommonUtils {

    public static DisplayMetrics DISPLAY_METRICS;
    private static int screenWidth;
    private static int screenHeight;

    public static int convertDipToPx(int dip) {
        if (DISPLAY_METRICS == null)
            DISPLAY_METRICS = D3Application.getContext().getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, DISPLAY_METRICS);
    }

    public static int convertDipToPx(Context context, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }

    public static int convertDipToPx(int dip, Context context) {
        if (DISPLAY_METRICS == null) DISPLAY_METRICS = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, DISPLAY_METRICS);
    }

    public static int getScreenWidth() {
        screenWidth = D3Application.getContext().getResources().getDisplayMetrics().widthPixels;
        return screenWidth;
    }

    public static int getScreenHeight() {
        screenHeight = D3Application.getContext().getResources().getDisplayMetrics().heightPixels;
        return screenHeight;
    }

    public static String replaceInvalidCharatctersForFile(String string) {
        if (string == null) return null;
        string = string.replaceAll("[:\\?*/\"<>|]", "");
        return string;
    }

    public static void showSoftInput(final Context context, final EditText editText) {
        editText.requestFocus();
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 200);
    }

    public static void hideSoftInput(final Context context, final View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
            if (context instanceof Activity) {
                imm.hideSoftInputFromWindow(((Activity) context).getWindow().getDecorView().getWindowToken(), 0);
            }
        }
    }

    public static boolean isSoftInputShown(final Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }
}