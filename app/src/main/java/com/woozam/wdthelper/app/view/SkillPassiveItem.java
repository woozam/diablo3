package com.woozam.wdthelper.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.woozam.wdthelper.R;
import com.woozam.wdthelper.data.DataManager;
import com.woozam.wdthelper.data.Passive;
import com.woozam.wdthelper.data.Server;
import com.woozam.wdthelper.network.VolleySingleton;

/**
 * Created by woozam on 2016-02-11.
 */
public class SkillPassiveItem extends RelativeLayout implements View.OnClickListener {

    private Server mServer;
    private Passive mSkill;
    private NetworkImageView mImage;
    private TextView mName;

    public SkillPassiveItem(Context context) {
        super(context);
        initialize(null);
    }

    public SkillPassiveItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public SkillPassiveItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.item_skill_passive, this, true);
        mImage = (NetworkImageView) findViewById(R.id.skill_passive_image);
        mName = (TextView) findViewById(R.id.skill_passive_content_name);

        setOnClickListener(this);
    }

    public void setSkill(Server server, Passive skill) {
        mServer = server;
        mSkill = skill;
        if (mSkill != null && mSkill.getSkill() != null) {
            mImage.setImageUrl(String.format("%s%s.png", DataManager.ICON_SKILL, mSkill.getSkill().getIcon()), VolleySingleton.getInstance(getContext()).getImageLoader());
            mName.setText(mSkill.getSkill().getName());
        }
    }

    @Override
    public void onClick(View v) {
        if (v == this) {
            if (mSkill != null && mSkill.getSkill() != null) {
                new SkillTooltipDialog(getContext()).setItem(mServer, mSkill).show();
            }
        }
    }
}
