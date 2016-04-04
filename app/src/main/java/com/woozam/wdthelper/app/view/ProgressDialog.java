package com.woozam.wdthelper.app.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.widget.ProgressBar;

import com.woozam.wdthelper.R;

/**
 * Created by woozam on 2016-02-13.
 */
public class ProgressDialog extends Dialog {

    public ProgressDialog(Context context) {
        super(context, R.style.ProgressDialog);
        setContentView(R.layout.dialog_progress);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.post(new Runnable() {
            @Override
            public void run() {
                ((Animatable) progressBar.getIndeterminateDrawable()).start();
            }
        });
    }
}