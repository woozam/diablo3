package com.woozam.wdthelper.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.woozam.wdthelper.R;
import com.woozam.wdthelper.analystics.Analystics;
import com.woozam.wdthelper.app.view.ItemBGView;
import com.woozam.wdthelper.app.view.TooltipDialog;
import com.woozam.wdthelper.common.ScreenShotTask;
import com.woozam.wdthelper.data.Armor;
import com.woozam.wdthelper.data.Attribute;
import com.woozam.wdthelper.data.Attributes;
import com.woozam.wdthelper.data.DPS;
import com.woozam.wdthelper.data.DataManager;
import com.woozam.wdthelper.data.Gem;
import com.woozam.wdthelper.data.Hero;
import com.woozam.wdthelper.data.Item;
import com.woozam.wdthelper.data.Items;
import com.woozam.wdthelper.network.VolleySingleton;

/**
 * Created by woozam on 2016-02-01.
 */
public class HeroItemFragment extends AbsHeroFragment implements View.OnClickListener, Response.Listener<Item>, Response.ErrorListener, CompoundButton.OnCheckedChangeListener {

    private View mRoot;
    private View mItemLayout;
    private View mItemsLayout;
    private ItemBGView mHeadBG;
    private NetworkImageView mHead;
    private ItemBGView mTorsoBG;
    private NetworkImageView mTorso;
    private ItemBGView mWaistBG;
    private NetworkImageView mWaist;
    private ItemBGView mLegsBG;
    private NetworkImageView mLegs;
    private ItemBGView mFeetBG;
    private NetworkImageView mFeet;
    private ItemBGView mShouldersBG;
    private NetworkImageView mShoulders;
    private ItemBGView mHandsBG;
    private NetworkImageView mHands;
    private ItemBGView mLeftFingerBG;
    private NetworkImageView mLeftFinger;
    private ItemBGView mMainHandBG;
    private NetworkImageView mMainHand;
    private ItemBGView mNeckBG;
    private NetworkImageView mNeck;
    private ItemBGView mBracersBG;
    private NetworkImageView mBracers;
    private ItemBGView mRightFingerBG;
    private NetworkImageView mRightFinger;
    private ItemBGView mOffHandBG;
    private NetworkImageView mOffHand;

    private View mItemsDescriptionLayout;
    private ItemBGView mHeadDescription;
    private ItemBGView mTorsoDescription;
    private ItemBGView mWaistDescription;
    private ItemBGView mLegsDescription;
    private ItemBGView mFeetDescription;
    private ItemBGView mShouldersDescription;
    private ItemBGView mHandsDescription;
    private ItemBGView mLeftFingerDescription;
    private ItemBGView mMainHandDescription;
    private ItemBGView mNeckDescription;
    private ItemBGView mBracersDescription;
    private ItemBGView mRightFingerDescription;
    private ItemBGView mOffHandDescription;

    private CheckBox mShowDescription;

    private ImageView mKanaiArmorBg;
    private NetworkImageView mKanaiArmor;
    private ImageView mKanaiWeaponBg;
    private NetworkImageView mKanaiWeapon;
    private ImageView mKanaiJewelryBg;
    private NetworkImageView mKanaiJewelry;

    private NetworkImageView mJewel1;
    private TextView mJewel1Rank;
    private NetworkImageView mJewel2;
    private TextView mJewel2Rank;
    private NetworkImageView mJewel3;
    private TextView mJewel3Rank;

    private Object mRequestTag = new Object();

    public HeroItemFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hero_item, container, false);
        mItemLayout = view.findViewById(R.id.hero_item_layout);
        mItemsLayout = view.findViewById(R.id.hero_item_items_layout);
        mHeadBG = (ItemBGView) view.findViewById(R.id.hero_item_head_bg);
        mHead = (NetworkImageView) view.findViewById(R.id.hero_item_head);
        mTorsoBG = (ItemBGView) view.findViewById(R.id.hero_item_torso_bg);
        mTorso = (NetworkImageView) view.findViewById(R.id.hero_item_torso);
        mWaistBG = (ItemBGView) view.findViewById(R.id.hero_item_waist_bg);
        mWaist = (NetworkImageView) view.findViewById(R.id.hero_item_waist);
        mLegsBG = (ItemBGView) view.findViewById(R.id.hero_item_legs_bg);
        mLegs = (NetworkImageView) view.findViewById(R.id.hero_item_legs);
        mFeetBG = (ItemBGView) view.findViewById(R.id.hero_item_feet_bg);
        mFeet = (NetworkImageView) view.findViewById(R.id.hero_item_feet);
        mShouldersBG = (ItemBGView) view.findViewById(R.id.hero_item_shoulders_bg);
        mShoulders = (NetworkImageView) view.findViewById(R.id.hero_item_shoulders);
        mHandsBG = (ItemBGView) view.findViewById(R.id.hero_item_hands_bg);
        mHands = (NetworkImageView) view.findViewById(R.id.hero_item_hands);
        mLeftFingerBG = (ItemBGView) view.findViewById(R.id.hero_item_left_finger_bg);
        mLeftFinger = (NetworkImageView) view.findViewById(R.id.hero_item_left_finger);
        mMainHandBG = (ItemBGView) view.findViewById(R.id.hero_item_main_hand_bg);
        mMainHand = (NetworkImageView) view.findViewById(R.id.hero_item_main_hand);
        mNeckBG = (ItemBGView) view.findViewById(R.id.hero_item_neck_bg);
        mNeck = (NetworkImageView) view.findViewById(R.id.hero_item_neck);
        mBracersBG = (ItemBGView) view.findViewById(R.id.hero_item_bracers_bg);
        mBracers = (NetworkImageView) view.findViewById(R.id.hero_item_bracers);
        mRightFingerBG = (ItemBGView) view.findViewById(R.id.hero_item_right_finger_bg);
        mRightFinger = (NetworkImageView) view.findViewById(R.id.hero_item_right_finger);
        mOffHandBG = (ItemBGView) view.findViewById(R.id.hero_item_off_hand_bg);
        mOffHand = (NetworkImageView) view.findViewById(R.id.hero_item_off_hand);

        mItemsDescriptionLayout = view.findViewById(R.id.hero_item_items_description_layout);
        mHeadDescription = (ItemBGView) view.findViewById(R.id.hero_item_head_description);
        mTorsoDescription = (ItemBGView) view.findViewById(R.id.hero_item_torso_description);
        mWaistDescription = (ItemBGView) view.findViewById(R.id.hero_item_waist_description);
        mLegsDescription = (ItemBGView) view.findViewById(R.id.hero_item_legs_description);
        mFeetDescription = (ItemBGView) view.findViewById(R.id.hero_item_feet_description);
        mShouldersDescription = (ItemBGView) view.findViewById(R.id.hero_item_shoulders_description);
        mHandsDescription = (ItemBGView) view.findViewById(R.id.hero_item_hands_description);
        mLeftFingerDescription = (ItemBGView) view.findViewById(R.id.hero_item_left_finger_description);
        mMainHandDescription = (ItemBGView) view.findViewById(R.id.hero_item_main_hand_description);
        mNeckDescription = (ItemBGView) view.findViewById(R.id.hero_item_neck_description);
        mBracersDescription = (ItemBGView) view.findViewById(R.id.hero_item_bracers_description);
        mRightFingerDescription = (ItemBGView) view.findViewById(R.id.hero_item_right_finger_description);
        mOffHandDescription = (ItemBGView) view.findViewById(R.id.hero_item_off_hand_description);

        mShowDescription = (CheckBox) view.findViewById(R.id.hero_item_show_description);

        mKanaiArmorBg = (ImageView) view.findViewById(R.id.hero_item_kanai_armor_bg);
        mKanaiArmor = (NetworkImageView) view.findViewById(R.id.hero_item_kanai_armor);
        mKanaiWeaponBg = (ImageView) view.findViewById(R.id.hero_item_kanai_weapon_bg);
        mKanaiWeapon = (NetworkImageView) view.findViewById(R.id.hero_item_kanai_weapon);
        mKanaiJewelryBg = (ImageView) view.findViewById(R.id.hero_item_kanai_jewelry_bg);
        mKanaiJewelry = (NetworkImageView) view.findViewById(R.id.hero_item_kanai_jewelry);

        mJewel1 = (NetworkImageView) view.findViewById(R.id.hero_item_jewel_1);
        mJewel1Rank = (TextView) view.findViewById(R.id.hero_item_jewel_1_rank);
        mJewel2 = (NetworkImageView) view.findViewById(R.id.hero_item_jewel_2);
        mJewel2Rank = (TextView) view.findViewById(R.id.hero_item_jewel_2_rank);
        mJewel3 = (NetworkImageView) view.findViewById(R.id.hero_item_jewel_3);
        mJewel3Rank = (TextView) view.findViewById(R.id.hero_item_jewel_3_rank);

        mShowDescription.setOnCheckedChangeListener(this);
        mRoot = view;

        setHero();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        VolleySingleton.getInstance(getContext()).getRequestQueue().cancelAll(mRequestTag);
    }

    @Override
    public void setHero(Hero hero) {
        super.setHero(hero);
        setHero();
    }

    private void setHero() {
        if (mRoot != null && mHero != null) {
            setItems(mHero.getItems());
            setKanaiItems(mHero.getLegendaryPowers());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_hero_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_capture: {
                saveScreenShot();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setItems(Items items) {
        try {
            setItem(items.getNeck(), mNeckBG, mNeck);
            setItem(items.getLeftFinger(), mLeftFingerBG, mLeftFinger);
            setItem(items.getRightFinger(), mRightFingerBG, mRightFinger);
            setItem(items.getHead(), mHeadBG, mHead);
            setItem(items.getTorso(), mTorsoBG, mTorso);
            setItem(items.getWaist(), mWaistBG, mWaist);
            setItem(items.getLegs(), mLegsBG, mLegs);
            setItem(items.getFeet(), mFeetBG, mFeet);
            setItem(items.getShoulders(), mShouldersBG, mShoulders);
            setItem(items.getHands(), mHandsBG, mHands);
            setItem(items.getMainHand(), mMainHandBG, mMainHand);
            setItem(items.getBracers(), mBracersBG, mBracers);
            setItem(items.getOffHand(), mOffHandBG, mOffHand);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            DataManager.getInstance().getItem(getContext(), mBattleTag.getServer(), item.getTooltipParams(), this, this, mRequestTag);
        }
    }

    private void setItemDescriptions() {
        try {
            Items items = mHero.getItems();
            setItemDescription(items.getNeck(), mNeckDescription);
            setItemDescription(items.getLeftFinger(), mLeftFingerDescription);
            setItemDescription(items.getRightFinger(), mRightFingerDescription);
            setItemDescription(items.getHead(), mHeadDescription);
            setItemDescription(items.getTorso(), mTorsoDescription);
            setItemDescription(items.getWaist(), mWaistDescription);
            setItemDescription(items.getLegs(), mLegsDescription);
            setItemDescription(items.getFeet(), mFeetDescription);
            setItemDescription(items.getShoulders(), mShouldersDescription);
            setItemDescription(items.getHands(), mHandsDescription);
            setItemDescription(items.getMainHand(), mMainHandDescription);
            setItemDescription(items.getBracers(), mBracersDescription);
            setItemDescription(items.getOffHand(), mOffHandDescription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setItemDescription(Item item, ItemBGView itemBGView) {
        if (!isAdded()) {
            return;
        }
        if (item == null) {
            itemBGView.setDisplayColor("brown");
            itemBGView.setText(null);
            itemBGView.setOnClickListener(null);
        } else if (itemBGView.getTag() != item) {
            itemBGView.setTag(item);
            itemBGView.setOnClickListener(this);
            itemBGView.setDisplayColor(item.getDisplayColor());
            StringBuilder sb = new StringBuilder();
            int size = (int) (itemBGView.getTextSize() * 1.8);
            sb.append("<u><b><font size='").append(size).append("px'>");
            sb.append(item.getName());
            sb.append("</font></b></u>");
            DPS dps = item.getDps();
            if (dps != null) {
                sb.append("<br>");
                sb.append(getContext().getString(R.string.damage_per_second)).append(" ");
                sb.append(String.format("%.1f", (Math.round((dps.getMax() + dps.getMin()) / 2 * 10) / 10f)));
            }
            Armor armor = item.getArmor();
            if (armor != null) {
                sb.append("<br>");
                sb.append(getContext().getString(R.string.armor)).append(" ");
                sb.append(String.valueOf((int) ((armor.getMax() + armor.getMin()) / 2)));
            }
            Attributes attributes = item.getAttributes();
            if (attributes != null) {
                if (attributes.getPrimary() != null) {
                    for (Attribute attribute : attributes.getPrimary()) {
                        sb.append("<br>");
                        if (attribute.getAffixType().equals("enchant")) {
                            sb.append("<font color='#80b7f4'>");
                        }
                        sb.append(attribute.getText());
                        if (attribute.getAffixType().equals("enchant")) {
                            sb.append("</font>");
                        }
                    }
                }
                if (!TextUtils.isEmpty(item.getAugmentation())) {
                    sb.append("<br>");
                    sb.append("<font color='#BEA7DB'>");
                    sb.append(item.getAugmentation());
                    sb.append("</font>");
                }
                if (attributes.getSecondary() != null) {
                    for (Attribute attribute : attributes.getSecondary()) {
                        sb.append("<br>");
                        if (attribute.getAffixType().equals("enchant")) {
                            sb.append("<font color='#80b7f4'>");
                        }
                        sb.append(attribute.getText());
                        if (attribute.getAffixType().equals("enchant")) {
                            sb.append("</font>");
                        }
                    }
                }
                if (attributes.getPassive() != null) {
                    for (Attribute attribute : attributes.getPassive()) {
                        sb.append("<br>");
                        sb.append("<font color='#febc1e'>");
                        sb.append(attribute.getText());
                        sb.append("</font>");
                    }
                }
            }
            Gem[] gems = item.getGems();
            if (gems != null) {
                for (Gem gem : gems) {
                    if (gem.isGem()) {
                        Attributes gemAttributes = gem.getAttributes();
                        if (gemAttributes != null) {
                            if (gemAttributes.getPrimary() != null) {
                                for (Attribute attribute : gemAttributes.getPrimary()) {
                                    sb.append("<br>");
                                    sb.append(attribute.getText());
                                }
                            }
                        }
                    } else if (gem.isJewel()) {
                        sb.append("<br>");
                        sb.append(gem.getItem().getName());
                        sb.append(" ");
                        sb.append(gem.getJewelRank());
                    }
                }
            }
            itemBGView.setText(Html.fromHtml(sb.toString()));
        }
    }

    private void setKanaiItems(Item[] items) {
        if (items == null) {
            return;
        }
        if (items.length > 0) {
            setKanaiItem(items[0], mKanaiWeaponBg, mKanaiWeapon);
        }
        if (items.length > 1) {
            setKanaiItem(items[1], mKanaiArmorBg, mKanaiArmor);
        }
        if (items.length > 2) {
            setKanaiItem(items[2], mKanaiJewelryBg, mKanaiJewelry);
        }
    }

    private void setKanaiItem(Item item, ImageView itemBGView, NetworkImageView imageView) {
        if (item == null) {
            itemBGView.setTag(null);
            itemBGView.setOnClickListener(null);
            itemBGView.setImageResource(0);
        } else {
            itemBGView.setTag(item);
            itemBGView.setOnClickListener(this);
            itemBGView.setImageResource(R.drawable.bg_kanai_item_selector);
            imageView.setImageUrl(String.format("%s%s.png", DataManager.ICON_ITEM, item.getIcon()), VolleySingleton.getInstance(getContext()).getImageLoader());
        }
    }

    private void setGem(Gem[] gems, String itemType) {
        if (gems == null || gems.length == 0 || !gems[0].isJewel()) {
            return;
        }
        NetworkImageView imageView = null;
        TextView textView = null;
        switch (itemType) {
            case "neck":
                imageView = mJewel1;
                textView = mJewel1Rank;
                break;
            case "leftFinger":
                imageView = mJewel2;
                textView = mJewel2Rank;
                break;
            case "rightFinger":
                imageView = mJewel3;
                textView = mJewel3Rank;
                break;
        }
        if (imageView != null) {
            imageView.setTag(gems[0].getItem());
            imageView.setOnClickListener(this);
            imageView.setImageUrl(String.format("%s%s.png", DataManager.ICON_ITEM, gems[0].getItem().getIcon()), VolleySingleton.getInstance(getContext()).getImageLoader());
            textView.setText(String.valueOf(gems[0].getJewelRank()));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() != null) {
            Item item = (Item) v.getTag();
            new TooltipDialog(getContext()).setItem(mBattleTag, mHero, item).show();
        }
    }

    @Override
    public void onResponse(Item response) {
        response.setDescriptionLoaded(true);
        if (mHero != null && mHero.getItems() != null) {
            String itemType = mHero.getItems().setItem(response);
            if (!TextUtils.isEmpty(itemType)) {
                if (itemType.equals("neck") || itemType.equals("rightFinger") || itemType.equals("leftFinger")) {
                    setGem(response.getGems(), itemType);
                }
                setItemDescriptions();
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == mShowDescription) {
            mItemsLayout.setVisibility(isChecked ? View.GONE : View.VISIBLE);
            mItemsDescriptionLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            Analystics.event("Button", "Item Description " + isChecked);
        }
    }

    public void saveScreenShot() {
        new ScreenShotTask(mItemLayout, mBattleTag.getBattleTag() + "_" + mHero.getName() + "_" + "items_" + System.currentTimeMillis(), "hero items").execute();
    }
}