package com.woozam.wdthelper.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.woozam.wdthelper.R;
import com.woozam.wdthelper.common.CommonUtils;

/**
 * Created by woozam on 2016-02-02.
 */
public class ItemBGView extends TextView {


    private BitmapDrawable mMaskBitmap;
    private GradientDrawable mFrame = null;

    public ItemBGView(Context context) {
        super(context);
        initialize(context);
    }

    public ItemBGView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public ItemBGView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);
        mMaskBitmap = (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.mipmap.bg_item_corner, null);
        mMaskBitmap.setBounds(0, 0, mMaskBitmap.getBitmap().getWidth(), mMaskBitmap.getBitmap().getHeight());
        mMaskBitmap.getPaint().setAntiAlias(true);
        mMaskBitmap.getPaint().setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mFrame = (GradientDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.bg_orange, null);
        setTextColor(0xffffffff);
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 8.0f);
        setTextScaleX(0.95f);
        setLineSpacing(CommonUtils.convertDipToPx(getContext(), 2), 1f);
        int padding2 = CommonUtils.convertDipToPx(getContext(), 2);
        int padding3 = CommonUtils.convertDipToPx(getContext(), 3);
        setPadding(padding3, padding2, padding3, padding2);
        setMinHeight(CommonUtils.convertDipToPx(getContext(), 80));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mFrame.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        int saveCount = canvas.save();
        mMaskBitmap.draw(canvas);
        canvas.scale(1.0f, -1.0f, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        mMaskBitmap.draw(canvas);
        canvas.scale(-1.0f, 1.0f, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        mMaskBitmap.draw(canvas);
        canvas.scale(1.0f, -1.0f, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        mMaskBitmap.draw(canvas);
        canvas.restoreToCount(saveCount);
        if (mFrame != null) {
            mFrame.draw(canvas);
        }
    }

    private void setFrame(GradientDrawable frame) {
        mFrame = frame;
        mFrame.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        invalidate();
    }

    public void setDisplayColor(String displayColor) {
        if (displayColor == null || displayColor.equals("brown")) {
            setFrame((GradientDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.bg_brown, null));
            setBackgroundResource(R.mipmap.bg_brown);
        } else if (displayColor.equals("blue")) {
            setFrame((GradientDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.bg_blue, null));
            setBackgroundResource(R.mipmap.bg_blue);
        } else if (displayColor.equals("yellow")) {
            setFrame((GradientDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.bg_yellow, null));
            setBackgroundResource(R.mipmap.bg_yellow);
        } else if (displayColor.equals("orange")) {
            setFrame((GradientDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.bg_orange, null));
            setBackgroundResource(R.mipmap.bg_orange);
        } else if (displayColor.equals("green")) {
            setFrame((GradientDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.bg_green, null));
            setBackgroundResource(R.mipmap.bg_green);
        } else {
            setFrame((GradientDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.bg_brown, null));
            setBackgroundResource(R.mipmap.bg_brown);
        }
    }
}