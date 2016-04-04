package com.woozam.wdthelper.data;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.support.v7.app.NotificationCompat.Builder;

import com.woozam.wdthelper.D3Application;
import com.woozam.wdthelper.R;
import com.woozam.wdthelper.app.activity.BonusBountyOrderActivity;
import com.woozam.wdthelper.common.PreferenceUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

/**
 * Created by woozam on 2016-04-03.
 */
public class Bounty {

    private static BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (PreferenceUtils.getBooleanValue(PreferenceUtils.KEY_BONUS_BOUNTY_ORDER_SHOW_STATUS_BAR, false)) {
                showStatusBar(context);
            }
        }
    };

    static {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
        D3Application.getContext().registerReceiver(mBroadcastReceiver, intentFilter);
    }

    public static final Bounty BOUNTY_ACT_1 = new Bounty(1);
    public static final Bounty BOUNTY_ACT_2 = new Bounty(2);
    public static final Bounty BOUNTY_ACT_3 = new Bounty(3);
    public static final Bounty BOUNTY_ACT_4 = new Bounty(4);
    public static final Bounty BOUNTY_ACT_5 = new Bounty(5);

    public static final Bounty[][] BONUS_BOUNTY_ORDERS = new Bounty[][]{ //
            {BOUNTY_ACT_5, BOUNTY_ACT_4, BOUNTY_ACT_3, BOUNTY_ACT_1, BOUNTY_ACT_2}, //
            {BOUNTY_ACT_4, BOUNTY_ACT_3, BOUNTY_ACT_5, BOUNTY_ACT_1, BOUNTY_ACT_2}, //
            {BOUNTY_ACT_3, BOUNTY_ACT_5, BOUNTY_ACT_1, BOUNTY_ACT_2, BOUNTY_ACT_4}, //
            {BOUNTY_ACT_5, BOUNTY_ACT_1, BOUNTY_ACT_2, BOUNTY_ACT_4, BOUNTY_ACT_3}, //
            {BOUNTY_ACT_1, BOUNTY_ACT_2, BOUNTY_ACT_4, BOUNTY_ACT_5, BOUNTY_ACT_3}, //
            {BOUNTY_ACT_2, BOUNTY_ACT_4, BOUNTY_ACT_5, BOUNTY_ACT_3, BOUNTY_ACT_1}, //
            {BOUNTY_ACT_4, BOUNTY_ACT_5, BOUNTY_ACT_2, BOUNTY_ACT_3, BOUNTY_ACT_1}, //
            {BOUNTY_ACT_5, BOUNTY_ACT_2, BOUNTY_ACT_3, BOUNTY_ACT_1, BOUNTY_ACT_4}, //
            {BOUNTY_ACT_2, BOUNTY_ACT_3, BOUNTY_ACT_1, BOUNTY_ACT_4, BOUNTY_ACT_5}, //
            {BOUNTY_ACT_3, BOUNTY_ACT_1, BOUNTY_ACT_4, BOUNTY_ACT_2, BOUNTY_ACT_5}, //
            {BOUNTY_ACT_1, BOUNTY_ACT_4, BOUNTY_ACT_2, BOUNTY_ACT_5, BOUNTY_ACT_3}, //
            {BOUNTY_ACT_4, BOUNTY_ACT_2, BOUNTY_ACT_1, BOUNTY_ACT_5, BOUNTY_ACT_3}, //
            {BOUNTY_ACT_2, BOUNTY_ACT_1, BOUNTY_ACT_5, BOUNTY_ACT_3, BOUNTY_ACT_4}, //
            {BOUNTY_ACT_1, BOUNTY_ACT_5, BOUNTY_ACT_3, BOUNTY_ACT_4, BOUNTY_ACT_2}, //
            {BOUNTY_ACT_5, BOUNTY_ACT_3, BOUNTY_ACT_4, BOUNTY_ACT_1, BOUNTY_ACT_2}, //
            {BOUNTY_ACT_3, BOUNTY_ACT_4, BOUNTY_ACT_1, BOUNTY_ACT_2, BOUNTY_ACT_5}, //
            {BOUNTY_ACT_4, BOUNTY_ACT_1, BOUNTY_ACT_3, BOUNTY_ACT_2, BOUNTY_ACT_5}, //
            {BOUNTY_ACT_1, BOUNTY_ACT_3, BOUNTY_ACT_2, BOUNTY_ACT_5, BOUNTY_ACT_4}, //
            {BOUNTY_ACT_3, BOUNTY_ACT_2, BOUNTY_ACT_5, BOUNTY_ACT_4, BOUNTY_ACT_1}, //
            {BOUNTY_ACT_2, BOUNTY_ACT_5, BOUNTY_ACT_4, BOUNTY_ACT_3, BOUNTY_ACT_1}  //
    };

    public static final String BASE_TIME = "2016-04-03T00:00:00.000+0900";

    public static Bounty[] getCurrentBonusBountyOrder() {
        try {
            DateFormat baseTimeDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            long baseTimeMillis = baseTimeDateFormat.parse(BASE_TIME).getTime();
            long currentTimeMillis = System.currentTimeMillis();
            long diff = currentTimeMillis - baseTimeMillis;
            int index = (int) (diff / (1000 * 60 * 60)) % 20;
            return BONUS_BOUNTY_ORDERS[index];
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void showStatusBar(Context context) {
        Intent intent = new Intent(context, BonusBountyOrderActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new Builder(context);
        builder.setContentTitle(context.getString(R.string.bonus_bounty_order));
        builder.setContentText(String.format("%s > %s > %s > %s > %s", Bounty.getCurrentBonusBountyOrder()));
        builder.setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_notification_large));
        }
        builder.setSmallIcon(R.mipmap.ic_small);
        builder.setOngoing(true);
        builder.setTicker(context.getString(R.string.bonus_bounty_order));
        Notification notification = builder.build();
        NotificationManagerCompat.from(context).notify(0, notification);
    }

    public static void dismissStatusBar(Context context) {
        NotificationManagerCompat.from(context).cancel(0);
    }

    public static ArrayList<Bounty[]> getDailyBonusBountyOrderList(int date) {
        ArrayList<Bounty[]> result = new ArrayList<>();
        try {
            DateFormat baseTimeDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            long baseTimeMillis = baseTimeDateFormat.parse(BASE_TIME).getTime();
            long time = date * 3600 * 24 * 1000L - TimeZone.getDefault().getRawOffset();
            long diff = time - baseTimeMillis;
            int index = (int) (diff / (1000 * 60 * 60)) % 20;
            if (index < 0) {
                index = index + 20;
            }
            for (int i = 0; i < 24; i++) {
                result.add(BONUS_BOUNTY_ORDERS[(i + index) % 20]);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Bounty(int act) {
        mAct = act;
    }

    private int mAct;

    public int getAct() {
        return mAct;
    }

    public void setAct(int act) {
        mAct = act;
    }

    @Override
    public String toString() {
        return String.format(D3Application.getContext().getString(R.string.bounty_act), mAct);
    }
}