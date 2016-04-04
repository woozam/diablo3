package com.woozam.wdthelper.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.woozam.wdthelper.R;

/**
 * Created by woozam on 2016-02-06.
 */
public class Email {

    private static final String EMAIL_ADDRESS = "woozam2@gmail.com";

    public static void sendEmail(Context context) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", EMAIL_ADDRESS, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.diablo3_suggestion));
        context.startActivity(Intent.createChooser(emailIntent, context.getString(R.string.send_email)));
    }
}