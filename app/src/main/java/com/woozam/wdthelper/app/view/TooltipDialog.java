package com.woozam.wdthelper.app.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.woozam.wdthelper.R;
import com.woozam.wdthelper.common.ScreenShotTask;
import com.woozam.wdthelper.data.BattleTag;
import com.woozam.wdthelper.data.DataManager;
import com.woozam.wdthelper.data.Hero;
import com.woozam.wdthelper.data.Item;
import com.woozam.wdthelper.database.DBItem;
import com.woozam.wdthelper.network.VolleySingleton;

/**
 * Created by woozam on 2016-02-05.
 */
public class TooltipDialog extends Dialog implements Response.ErrorListener, Response.Listener<String>, View.OnClickListener, DataManager.OnFavoriteItemChangedListener {

    public static final String TAG = TooltipDialog.class.getSimpleName();

    private BattleTag mBattleTag;
    private Hero mHero;
    private Item mItem;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private View mSaveToGalleryLayout;
    private View mSaveToGallery;
    private Button mFavorite;
    private Button mEnchant;
    private Object mRequestTag = new Object();

    public TooltipDialog(Context context) {
        super(context, R.style.TooltipDialog);
        setContentView(R.layout.dialog_tooltip);
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
        mFavorite = (Button) findViewById(R.id.tooltip_favorite);
        mEnchant = (Button) findViewById(R.id.tooltip_enchant);

        mSaveToGalleryLayout.setVisibility(View.INVISIBLE);
        mSaveToGallery.setOnClickListener(this);
        mFavorite.setOnClickListener(this);
        mEnchant.setOnClickListener(this);
        mEnchant.setVisibility(View.GONE);
    }

    public TooltipDialog setItem(BattleTag battleTag, Hero hero, Item item) {
        mBattleTag = battleTag;
        mHero = hero;
        mItem = item;
        if (mItem.isDescriptionLoaded()) {
            DataManager.getInstance().getItem(getContext(), mBattleTag.getServer(), item.getTooltipParams(), mItemListener, mItemErrorListener, mRequestTag);
        }
        return this;
    }

    public void show() {
        super.show();
        mProgressBar.setVisibility(View.VISIBLE);
        DataManager.getInstance().getTooltip(getContext(), mBattleTag.getServer(), mItem.getTooltipParams(), this, this, mRequestTag);
        DataManager.getInstance().addOnFavoriteItemListener(this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        DataManager.getInstance().removeOnFavoriteItemListener(this);
        VolleySingleton.getInstance(getContext()).getRequestQueue().cancelAll(mRequestTag);
    }

    @Override
    public void onResponse(String response) {
        response = response.replace("d3-tooltip-item-wrapper-AncientLegendary", "");
        response = response.replace("d3-tooltip-item-wrapper-PrimalAncientLegendary", "");
        response = response.replaceAll("class=\"tooltip-head +tooltip-head-.*\"", "style=\"height: 40px; padding: 0 15px; text-align: center; font-size: 18px;\"");
        String html = "<script src=\"http://" + mBattleTag.getServer().toString() + ".battle.net/d3/static/js/tooltips.js\"></script>" + response;
        mWebView.loadData(html, "text/html; charset=UTF-8", null);
        setFavoriteButton();
        setEnchantButton();
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mProgressBar.setVisibility(View.INVISIBLE);
        mSaveToGalleryLayout.setVisibility(View.VISIBLE);
        mSaveToGallery.setVisibility(View.GONE);
        mEnchant.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v == mSaveToGallery) {
            new ScreenShotTask(mWebView, mBattleTag.getBattleTag() + "_" + mHero.getName() + "_" + mItem.getName() + "_" + System.currentTimeMillis(), "item").execute();
        } else if (v == mFavorite) {
            if (DataManager.getInstance().containsFavoriteItem(mBattleTag, mItem.getTooltipParams())) {
                DataManager.getInstance().removeFavoriteItem(mBattleTag, mItem.getTooltipParams());
            } else {
                DataManager.getInstance().addFavoriteItem(mItem.getTooltipParams(), mItem.getIcon(), mItem.getName(), mItem.getDisplayColor(), mBattleTag, mHero.getId(), mHero.getName());
            }
            setFavoriteButton();
        } else if (v == mEnchant) {
//            EnchantActivity.createInstance(getContext(), mItem);
        }
    }

    private void setFavoriteButton() {
        if (DataManager.getInstance().containsFavoriteItem(mBattleTag, mItem.getTooltipParams())) {
            mFavorite.setText(R.string.remove_favorite);
        } else {
            mFavorite.setText(R.string.add_favorite);
        }
    }

    private void setEnchantButton() {
//        if (!mItem.isDescriptionLoaded()) {
            mEnchant.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onAddFavoriteItem(DBItem hero) {
        Toast.makeText(getContext(), R.string.favorite_added, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRemoveFavoriteItem(DBItem hero) {
        Toast.makeText(getContext(), R.string.favorite_removed, Toast.LENGTH_LONG).show();
    }

    private Response.Listener<Item> mItemListener = new Response.Listener<Item>() {
        @Override
        public void onResponse(Item response) {
            mItem = response;
            mItem.setDescriptionLoaded(true);
            setEnchantButton();
        }
    };

    private Response.ErrorListener mItemErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
        }
    };
}