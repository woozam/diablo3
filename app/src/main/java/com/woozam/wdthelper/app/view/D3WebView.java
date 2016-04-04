package com.woozam.wdthelper.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by woozam on 2016-02-06.
 */
public class D3WebView extends WebView {

    public D3WebView(Context context) {
        super(context);
    }

    public D3WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public D3WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getContentWidth() {
        return this.computeHorizontalScrollRange();
    }

    public int getContentHeight2() {
        return computeVerticalScrollRange();
    }
}