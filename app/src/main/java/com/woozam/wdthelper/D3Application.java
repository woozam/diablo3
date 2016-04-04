package com.woozam.wdthelper;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.webkit.WebView;

import com.woozam.wdthelper.common.PreferenceUtils;
import com.woozam.wdthelper.data.Bounty;

/**
 * Created by woozam on 2016-01-31.
 */
public class D3Application extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        if (Build.VERSION.SDK_INT >= 21) {
            WebView.enableSlowWholeDocumentDraw();
        }
        if (PreferenceUtils.getBooleanValue(PreferenceUtils.KEY_BONUS_BOUNTY_ORDER_SHOW_STATUS_BAR, false)) {
            Bounty.showStatusBar(this);
        }
    }
}