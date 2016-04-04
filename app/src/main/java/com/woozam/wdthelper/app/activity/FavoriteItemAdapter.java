package com.woozam.wdthelper.app.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.woozam.wdthelper.R;
import com.woozam.wdthelper.app.view.ItemBGView;
import com.woozam.wdthelper.data.DataManager;
import com.woozam.wdthelper.database.DBItem;
import com.woozam.wdthelper.network.VolleySingleton;

import java.util.LinkedList;

/**
 * Created by woozam on 2016-02-06.
 */
public class FavoriteItemAdapter extends D3BaseAdapter {

    private LinkedList<DBItem> mItemList;
    private View.OnClickListener mOnClickListener;

    public FavoriteItemAdapter(Context context, LinkedList<DBItem> itemList, View.OnClickListener onClickListener) {
        super(context);
        mItemList = itemList;
        mOnClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public DBItem getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            ItemViewHolder viewHolder = new ItemViewHolder(mInflater);
            convertView = viewHolder.mRoot;
            convertView.setTag(viewHolder);
        }

        DBItem item = getItem(position);
        ItemViewHolder viewHolder = (ItemViewHolder) convertView.getTag();
        viewHolder.setItem(item);
        viewHolder.mRoot.setBackgroundColor(position % 2 == 1 ? 0xff12110f : 0xff171614);

        return convertView;
    }

    private class ItemViewHolder extends BaseViewHolder {

        private NetworkImageView mItemImage;
        private ItemBGView mItemImageBG;
        private TextView mItemName;
        private TextView mHeroName;
        private TextView mBattleTag;
        private View mMore;

        public ItemViewHolder(LayoutInflater inflater) {
            super(inflater, R.layout.row_favorite_item);
            mItemImage = (NetworkImageView) findViewById(R.id.favorite_item_image);
            mItemImageBG = (ItemBGView) findViewById(R.id.favorite_item_image_bg);
            mItemName = (TextView) findViewById(R.id.favorite_item_name);
            mHeroName = (TextView) findViewById(R.id.favorite_item_hero_name);
            mBattleTag = (TextView) findViewById(R.id.favorite_item_battle_tag);
            mMore = findViewById(R.id.favorite_item_more);
        }

        public void setItem(DBItem item) {
            mItemImage.setImageUrl(String.format("%s%s.png", DataManager.ICON_ITEM, item.getIcon()), VolleySingleton.getInstance(mContext).getImageLoader());
            mItemImageBG.setDisplayColor(item.getColor());
            mItemName.setText(item.getName());
            mHeroName.setText(item.getHeroName());
            mBattleTag.setText(item.getBattleTag().toString());
            mMore.setOnClickListener(mOnClickListener);
            mMore.setTag(item);
            mMore.setFocusable(false);
        }
    }
}