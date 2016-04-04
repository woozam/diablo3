package com.woozam.wdthelper.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.woozam.wdthelper.R;
import com.woozam.wdthelper.data.AbsSkill;
import com.woozam.wdthelper.data.Active;
import com.woozam.wdthelper.data.DataManager;
import com.woozam.wdthelper.data.Server;
import com.woozam.wdthelper.network.VolleySingleton;

/**
 * Created by woozam on 2016-02-11.
 */
public class SkillActiveItem extends RelativeLayout implements View.OnClickListener {

    public static final String POSITION_LEFT = "left";
    public static final String POSITION_RIGHT = "right";
    public static final String POSITION_1 = "1";
    public static final String POSITION_2 = "2";
    public static final String POSITION_3 = "3";
    public static final String POSITION_4 = "4";

    private Server mServer;
    private AbsSkill mSkill;
    private ImageView mOverlay;
    private NetworkImageView mImage;
    private TextView mName;
    private TextView mRune;

    public SkillActiveItem(Context context) {
        super(context);
        initialize(null);
    }

    public SkillActiveItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public SkillActiveItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.item_skill_active, this, true);
        mOverlay = (ImageView) findViewById(R.id.skill_active_overlay);
        mImage = (NetworkImageView) findViewById(R.id.skill_active_image);
        mName = (TextView) findViewById(R.id.skill_active_content_name);
        mRune = (TextView) findViewById(R.id.skill_active_content_rune);

        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.SkillActiveItem);
            String position = ta.getString(R.styleable.SkillActiveItem_position);
            boolean rune = ta.getBoolean(R.styleable.SkillActiveItem_rune, true);
            setPosition(position);
            mRune.setVisibility(rune ? VISIBLE : GONE);
            ta.recycle();
        }

        setOnClickListener(this);
    }

    private void setPosition(String position) {
        if (position != null) {
            switch (position) {
                case POSITION_LEFT:
                    mOverlay.setImageResource(R.mipmap.ic_skill_active_mouse_left);
                    break;
                case POSITION_RIGHT:
                    mOverlay.setImageResource(R.mipmap.ic_skill_active_mouse_right);
                    break;
                case POSITION_1:
                    mOverlay.setImageResource(R.mipmap.ic_skill_active_1);
                    break;
                case POSITION_2:
                    mOverlay.setImageResource(R.mipmap.ic_skill_active_2);
                    break;
                case POSITION_3:
                    mOverlay.setImageResource(R.mipmap.ic_skill_active_3);
                    break;
                case POSITION_4:
                    mOverlay.setImageResource(R.mipmap.ic_skill_active_4);
                    break;
            }
        }
    }

    public void setSkill(Server server, AbsSkill skill) {
        mServer = server;
        mSkill = skill;
        if (mSkill != null && mSkill.getSkill() != null) {
            mImage.setImageUrl(String.format("%s%s.png", DataManager.ICON_SKILL, mSkill.getSkill().getIcon()), VolleySingleton.getInstance(getContext()).getImageLoader());
            mName.setText(mSkill.getSkill().getName());
            if (mSkill instanceof Active && ((Active) mSkill).getRune() != null) {
                mRune.setText(((Active) mSkill).getRune().getName());
            }
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