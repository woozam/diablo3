package com.woozam.wdthelper.common;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.woozam.wdthelper.BuildConfig;

/**
 * Created by woozam on 2016-02-06.
 */
public class ADUtils {

    public static boolean USE_AD = !PreferenceUtils.getBooleanValue(PreferenceUtils.KEY_PURCHASED_REMOVE_ADS, false);

    public static void setRemoveAdsPurchased() {
        USE_AD = false;
        PreferenceUtils.setValue(PreferenceUtils.KEY_PURCHASED_REMOVE_ADS, true);
    }

    public static void requestAD(AdView adView) {
        if (USE_AD && adView != null) {
            AdRequest adRequest;
            if (BuildConfig.DEBUG) {
                adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
            } else {
                adRequest = new AdRequest.Builder().build();
            }
            adView.loadAd(adRequest);
        }
    }

    public static void requestAD(InterstitialAd adView) {
        if (USE_AD && adView != null) {
            AdRequest adRequest;
            if (BuildConfig.DEBUG) {
                adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
            } else {
                adRequest = new AdRequest.Builder().build();
            }
            adView.loadAd(adRequest);
        }
    }

    public static void requestAD(InterstitialAd adView, float probability) {
        if (Math.random() < probability) {
            requestAD(adView);
        }
    }
}