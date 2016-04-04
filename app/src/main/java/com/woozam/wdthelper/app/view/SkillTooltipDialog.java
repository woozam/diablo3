package com.woozam.wdthelper.app.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.woozam.wdthelper.R;
import com.woozam.wdthelper.common.ScreenShotTask;
import com.woozam.wdthelper.data.AbsSkill;
import com.woozam.wdthelper.data.Active;
import com.woozam.wdthelper.data.DataManager;
import com.woozam.wdthelper.data.Server;
import com.woozam.wdthelper.network.VolleySingleton;

/**
 * Created by woozam on 2016-02-05.
 */
public class SkillTooltipDialog extends Dialog implements View.OnClickListener, Response.ErrorListener {

    private Server mServer;
    private AbsSkill mItem;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private View mSaveToGalleryLayout;
    private View mSaveToGallery;
    private String mHtml1;
    private String mHtml2;

    private Object mRequestTag = new Object();

    public SkillTooltipDialog(Context context) {
        super(context, R.style.TooltipDialog);
        setContentView(R.layout.dialog_skill_tooltip);
        mWebView = (WebView) findViewById(R.id.tooltip_web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setBackgroundColor(Color.parseColor("#ff000000"));
        mWebView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mWebView.getMeasuredHeight() > 50) {
                    mSaveToGalleryLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        mProgressBar = (ProgressBar) findViewById(R.id.tooltip_progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mSaveToGalleryLayout = findViewById(R.id.tooltip_save_screen_shot_layout);
        mSaveToGallery = findViewById(R.id.tooltip_save_screen_shot);

        mSaveToGalleryLayout.setVisibility(View.INVISIBLE);
        mSaveToGallery.setOnClickListener(this);
    }

    public SkillTooltipDialog setItem(Server server, AbsSkill item) {
        mServer = server;
        mItem = item;
        return this;
    }

    public void show() {
        super.show();
        mProgressBar.setVisibility(View.VISIBLE);
        DataManager.getInstance().getTooltip(getContext(), mServer, mItem.getSkill().getTooltipUrl(), mListener1, this, mRequestTag);
        if (mItem instanceof Active && ((Active) mItem).getRune() != null && ((Active) mItem).getRune().getTooltipParams() != null) {
            DataManager.getInstance().getTooltip(getContext(), mServer, ((Active) mItem).getRune().getTooltipParams(), mListener2, this, mRequestTag);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        VolleySingleton.getInstance(getContext()).getRequestQueue().cancelAll(mRequestTag);
    }

    private Response.Listener<String> mListener1 = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            response = response.replaceAll("class=\"tooltip-head", "style=\"height: 40px; padding: 0 15px; text-align: center; font-size: 18px;\"");
            mHtml1 = "<script src=\"http://" + mServer.toString() + ".battle.net/d3/static/js/tooltips.js\"></script>" + response;
            String html = (mHtml1 == null ? "" : mHtml1) + (mHtml2 == null ? "" : mHtml2);
            mWebView.loadData(html, "text/html; charset=UTF-8", null);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    };

    private Response.Listener<String> mListener2 = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            response = response.replaceAll("class=\"tooltip-head", "style=\"height: 40px; padding: 0 15px; text-align: center; font-size: 18px;\"");
            mHtml2 = response;
            String html = (mHtml1 == null ? "" : mHtml1) + (mHtml2 == null ? "" : mHtml2);
            mWebView.loadData(html, "text/html; charset=UTF-8", null);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    };

    @Override
    public void onErrorResponse(VolleyError error) {
        mProgressBar.setVisibility(View.INVISIBLE);
        mSaveToGalleryLayout.setVisibility(View.VISIBLE);
        mSaveToGallery.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v == mSaveToGallery) {
            new ScreenShotTask(mWebView, mItem.getSkill().getName() + "_" + System.currentTimeMillis(), "skill").execute();
        }
    }
}