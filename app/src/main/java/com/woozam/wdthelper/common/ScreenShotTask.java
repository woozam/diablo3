package com.woozam.wdthelper.common;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.v4.content.PermissionChecker;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.Toast;

import com.woozam.wdthelper.D3Application;
import com.woozam.wdthelper.R;
import com.woozam.wdthelper.analystics.Analystics;
import com.woozam.wdthelper.app.view.D3WebView;

/**
 * Created by woozam on 2016-02-06.
 */
public class ScreenShotTask extends AsyncTask<Void, Void, Void> {

    private View mView;
    private Bitmap mBitmap;
    private int mWidth;
    private int mHeight;
    private Picture mPicture;
    private String mTitle;
    private String mDescription;
    private Drawable mFooter;

    public ScreenShotTask(View view, String title, String description) {
        if (VERSION.SDK_INT >= 23) {
            if (PermissionChecker.checkSelfPermission(D3Application.getContext(), permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Activity activity = null;
                Context context = view.getContext();
                if (context instanceof Activity) {
                    activity = (Activity) context;
                } else {
                    activity = (Activity) ((ContextWrapper) context).getBaseContext();
                }
                activity.requestPermissions(new String[]{permission.WRITE_EXTERNAL_STORAGE}, 0);
                cancel(true);
                Toast.makeText(view.getContext(), R.string.need_permission, Toast.LENGTH_SHORT).show();
                return;
            }
        }

        mView = view;
        if (mView instanceof D3WebView) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mWidth = mView.getMeasuredWidth();
                mHeight = ((D3WebView) mView).getContentHeight2();
            } else {
                mPicture = ((D3WebView) mView).capturePicture();
                mWidth = mPicture.getWidth();
                mHeight = mPicture.getHeight();
            }
        } else {
            mWidth = mView.getMeasuredWidth();
            mHeight = mView.getMeasuredHeight();
        }
        mTitle = title;
        mDescription = description;
        mFooter = ResourcesCompat.getDrawable(mView.getResources(), R.mipmap.footer_screenshot, null);
        mFooter.setBounds(0, 0, mFooter.getIntrinsicWidth(), mFooter.getIntrinsicHeight());
        Analystics.event("Screen Shot", description);
    }

    @Override
    protected Void doInBackground(Void... params) {
        int width = mWidth;
        int height = mHeight + (int) (mFooter.getIntrinsicHeight() * (float) mWidth / (float) mFooter.getIntrinsicWidth());
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Canvas canvas = new Canvas(mBitmap);
        if (mPicture != null) {
            mPicture.draw(canvas);
        } else {
            mView.draw(canvas);
        }
        canvas.translate(0, mHeight);
        float r = (float) mWidth / (float) mFooter.getIntrinsicWidth();
        canvas.scale(r, r);
        mFooter.draw(canvas);
        new InsertImageTask(mTitle, mDescription, mBitmap).execute();
    }
}