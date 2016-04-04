package com.woozam.wdthelper.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.woozam.wdthelper.R;
import com.woozam.wdthelper.data.BattleTag;
import com.woozam.wdthelper.data.DataManager;
import com.woozam.wdthelper.data.Follower;
import com.woozam.wdthelper.data.FollowerSkills;
import com.woozam.wdthelper.data.Hero;
import com.woozam.wdthelper.data.Item;
import com.woozam.wdthelper.data.Items;
import com.woozam.wdthelper.data.Stats;
import com.woozam.wdthelper.network.VolleySingleton;

/**
 * Created by woozam on 2016-02-13.
 */
public class FollowerLayout extends RelativeLayout implements View.OnClickListener {

    private BattleTag mBattleTag;
    private Hero mHero;
    private ItemBGView mFollowerItemSpecialBG;
    private NetworkImageView mFollowerItemSpecial;
    private ItemBGView mFollowerItemNeckBG;
    private NetworkImageView mFollowerItemNeck;
    private ItemBGView mFollowerItemMainHandBG;
    private NetworkImageView mFollowerItemMainHand;
    private ItemBGView mFollowerItemOffHandBG;
    private NetworkImageView mFollowerItemOffHand;
    private ItemBGView mFollowerItemLeftFingerBG;
    private NetworkImageView mFollowerItemLeftFinger;
    private ItemBGView mFollowerItemRightFingerBG;
    private NetworkImageView mFollowerItemRightFinger;
    private TextView mFollowerGoldFind;
    private TextView mFollowerMagicFind;
    private TextView mFollowerExperienceBonus;
    private SkillActiveItem mFollowerSkill1;
    private SkillActiveItem mFollowerSkill2;
    private SkillActiveItem mFollowerSkill3;
    private SkillActiveItem mFollowerSkill4;

    public FollowerLayout(Context context) {
        super(context);
        initialize();
    }

    public FollowerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public FollowerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.layout_follower, this, true);
        mFollowerItemSpecialBG = (ItemBGView) findViewById(R.id.follower_item_special_bg);
        mFollowerItemSpecial = (NetworkImageView) findViewById(R.id.follower_item_special);
        mFollowerItemNeckBG = (ItemBGView) findViewById(R.id.follower_item_neck_bg);
        mFollowerItemNeck = (NetworkImageView) findViewById(R.id.follower_item_neck);
        mFollowerItemMainHandBG = (ItemBGView) findViewById(R.id.follower_item_main_hand_bg);
        mFollowerItemMainHand = (NetworkImageView) findViewById(R.id.follower_item_main_hand);
        mFollowerItemOffHandBG = (ItemBGView) findViewById(R.id.follower_item_off_hand_bg);
        mFollowerItemOffHand = (NetworkImageView) findViewById(R.id.follower_item_off_hand);
        mFollowerItemLeftFingerBG = (ItemBGView) findViewById(R.id.follower_item_left_finger_bg);
        mFollowerItemLeftFinger = (NetworkImageView) findViewById(R.id.follower_item_left_finger);
        mFollowerItemRightFingerBG = (ItemBGView) findViewById(R.id.follower_item_right_finger_bg);
        mFollowerItemRightFinger = (NetworkImageView) findViewById(R.id.follower_item_right_finger);
        mFollowerGoldFind = (TextView) findViewById(R.id.follower_gold_find);
        mFollowerMagicFind = (TextView) findViewById(R.id.follower_magic_find);
        mFollowerExperienceBonus = (TextView) findViewById(R.id.follower_experience_bonus);
        mFollowerSkill1 = (SkillActiveItem) findViewById(R.id.follower_skill_1);
        mFollowerSkill2 = (SkillActiveItem) findViewById(R.id.follower_skill_2);
        mFollowerSkill3 = (SkillActiveItem) findViewById(R.id.follower_skill_3);
        mFollowerSkill4 = (SkillActiveItem) findViewById(R.id.follower_skill_4);
        mFollowerItemSpecialBG.setDisplayColor("brown");
        mFollowerItemNeckBG.setDisplayColor("brown");
        mFollowerItemMainHandBG.setDisplayColor("brown");
        mFollowerItemOffHandBG.setDisplayColor("brown");
        mFollowerItemLeftFingerBG.setDisplayColor("brown");
        mFollowerItemRightFingerBG.setDisplayColor("brown");
    }

    public void setFollower(BattleTag battleTag, Hero hero, Follower follower) {
        mBattleTag = battleTag;
        mHero = hero;
        if (follower != null) {
            setItems(follower.getItems());
            setSkills(follower.getSkills());
            setStats(follower.getStats());
        }
    }

    private void setItems(Items items) {
        setItem(items.getSpecial(), mFollowerItemSpecialBG, mFollowerItemSpecial);
        setItem(items.getNeck(), mFollowerItemNeckBG, mFollowerItemNeck);
        setItem(items.getMainHand(), mFollowerItemMainHandBG, mFollowerItemMainHand);
        setItem(items.getOffHand(), mFollowerItemOffHandBG, mFollowerItemOffHand);
        setItem(items.getLeftFinger(), mFollowerItemLeftFingerBG, mFollowerItemLeftFinger);
        setItem(items.getRightFinger(), mFollowerItemRightFingerBG, mFollowerItemRightFinger);
    }

    private void setItem(Item item, ItemBGView itemBGView, NetworkImageView imageView) {
        if (item == null) {
            imageView.setVisibility(View.INVISIBLE);
            itemBGView.setDisplayColor("brown");
            itemBGView.setTag(null);
            itemBGView.setOnClickListener(null);
        } else {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageUrl(String.format("%s%s.png", DataManager.ICON_ITEM, item.getIcon()), VolleySingleton.getInstance(getContext()).getImageLoader());
            itemBGView.setDisplayColor(item.getDisplayColor());
            itemBGView.setTag(item);
            itemBGView.setOnClickListener(this);
        }
    }

    private void setSkills(FollowerSkills[] skills) {
        mFollowerSkill1.setSkill(mBattleTag.getServer(), skills[0]);
        mFollowerSkill2.setSkill(mBattleTag.getServer(), skills[1]);
        mFollowerSkill3.setSkill(mBattleTag.getServer(), skills[2]);
        mFollowerSkill4.setSkill(mBattleTag.getServer(), skills[3]);
    }

    private void setStats(Stats stats) {
        mFollowerGoldFind.setText(String.format("%.1f%%", stats.getGoldFind()));
        mFollowerMagicFind.setText(String.format("%.1f%%", stats.getMagicFind()));
        mFollowerExperienceBonus.setText(String.format("+%d", stats.getExperienceBonus()));
    }

    @Override
    public void onClick(View v) {
        if (v instanceof ItemBGView) {
            if (v.getTag() != null) {
                Item item = (Item) v.getTag();
                new TooltipDialog(getContext()).setItem(mBattleTag, mHero, item).show();
            }
        }
    }
}