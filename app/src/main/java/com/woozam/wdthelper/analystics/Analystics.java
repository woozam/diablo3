package com.woozam.wdthelper.analystics;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.woozam.wdthelper.BuildConfig;
import com.woozam.wdthelper.D3Application;
import com.woozam.wdthelper.R;

/**
 * Created by woozam on 2016-02-13.
 */
public class Analystics {

    private static Tracker mTracker;

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
    synchronized public static Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(D3Application.getContext());
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
            mTracker.send(new HitBuilders.EventBuilder().setCategory("start").setAction(String.valueOf(BuildConfig.DEBUG)).build());
        }
        return mTracker;
    }

    public static void screen(String screenName) {
        if (!BuildConfig.DEBUG) {
            Tracker tracker = getDefaultTracker();
            tracker.setScreenName(screenName);
            tracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    public static void event(String category, String action) {
        if (!BuildConfig.DEBUG) {
            Tracker tracker = getDefaultTracker();
            tracker.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).build());
        }
    }
}