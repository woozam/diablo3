package com.woozam.wdthelper.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.woozam.wdthelper.D3Application;
import com.woozam.wdthelper.data.BattleTag;
import com.woozam.wdthelper.data.Server;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by woozam on 2016-01-31.
 */
public class D3DBManager {

    public static final int VERSION = 3;
    public static final String DB_NAME = "diablo3.db";

    public static final String TABLE_BATTLE_TAG = "table_battle_tag";
    public static final String COLUMN_BATTLE_TAG = "battle_tag";
    public static final String COLUMN_SERVER = "server";

    public static final String TABLE_HERO = "table_hero";
    public static final String COLUMN_HERO_BATTLE_TAG = "hero_battle_tag";
    public static final String COLUMN_HERO_ID = "hero_id";
    public static final String COLUMN_HERO_NAME = "hero_name";
    public static final String COLUMN_HERO_CLASS = "hero_class";
    public static final String COLUMN_HERO_GENDER = "hero_gender";
    public static final String COLUMN_HERO_SERVER = "hero_server";

    public static final String TABLE_ITEM = "table_item";
    public static final String COLUMN_ITEM_TOOLTIP_PARAMS = "item_tooltip_params";
    public static final String COLUMN_ITEM_ICON = "item_icon";
    public static final String COLUMN_ITEM_NAME = "item_name";
    public static final String COLUMN_ITEM_COLOR = "item_color";
    public static final String COLUMN_ITEM_BATTLE_TAG = "item_battle_tag";
    public static final String COLUMN_ITEM_HERO_ID = "item_hero_id";
    public static final String COLUMN_ITEM_HERO_NAME = "item_hero_name";
    public static final String COLUMN_ITEM_SERVER = "item_server";

    private static final String CREATE_TABLE_BATTLE_TAG = "create table " + TABLE_BATTLE_TAG
            + " (_id integer primary key autoincrement, "
            + COLUMN_BATTLE_TAG + " text, "
            + COLUMN_SERVER + " text)";

    private static final String CREATE_TABLE_HERO = "create table " + TABLE_HERO
            + " (_id integer primary key autoincrement, "
            + COLUMN_HERO_BATTLE_TAG + " text, "
            + COLUMN_HERO_ID + " long, "
            + COLUMN_HERO_NAME + " text, "
            + COLUMN_HERO_CLASS + " text, "
            + COLUMN_HERO_GENDER + " integer, "
            + COLUMN_HERO_SERVER + " text)";

    private static final String CREATE_TABLE_ITEM = "create table " + TABLE_ITEM
            + " (_id integer primary key autoincrement, "
            + COLUMN_ITEM_TOOLTIP_PARAMS + " text, "
            + COLUMN_ITEM_ICON + " text, "
            + COLUMN_ITEM_NAME + " text, "
            + COLUMN_ITEM_COLOR + " text, "
            + COLUMN_ITEM_BATTLE_TAG + " text, "
            + COLUMN_ITEM_HERO_ID + " long, "
            + COLUMN_ITEM_HERO_NAME + " text, "
            + COLUMN_ITEM_SERVER + " text)";

    private static D3DBManager sInstance;

    public static D3DBManager getInstance() {
        if (sInstance == null) {
            sInstance = new D3DBManager(D3Application.getContext());
        }
        return sInstance;
    }

    private SQLiteDatabase mDB;
    private LinkedBlockingDeque<D3DBJob> mJobQueue;

    private D3DBManager(Context context) {
        OpenHelper opener = new OpenHelper(context, DB_NAME, null, VERSION);
        mDB = opener.getWritableDatabase();
        mJobQueue = new LinkedBlockingDeque<>();
        Thread thread = new Thread("D3DBManager") {
            @Override
            public void run() {
                super.run();
                while (true) {
                    try {
                        D3DBJob job = mJobQueue.take();
                        handleJob(job);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    public synchronized Object handleJob(D3DBJob job) {
        int type = job.getType();
        switch (type) {
            case D3DBJob.TYPE_INSERT:
                return mDB.insert(job.getTableName(), job.getNullColumnHack(), job.getContentValues());
            case D3DBJob.TYPE_UPDATE:
                return mDB.update(job.getTableName(), job.getContentValues(), job.getWhereClause(), job.getWhereArgs());
            case D3DBJob.TYPE_DELETE:
                return mDB.delete(job.getTableName(), job.getWhereClause(), job.getWhereArgs());
            case D3DBJob.TYPE_QUERY:
                return mDB.query(job.getTableName(), job.getColumns(), job.getWhereClause(), job.getWhereArgs(), job.getGroupBy(), job.getHaving(), job.getOrderBy(), job.getLimit());
        }
        return null;
    }

    private class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_BATTLE_TAG);
            db.execSQL(CREATE_TABLE_HERO);
            db.execSQL(CREATE_TABLE_ITEM);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion < newVersion) {
                if (oldVersion == 1) {
                    db.execSQL(CREATE_TABLE_HERO);
                } else if (oldVersion == 2) {
                    db.execSQL(CREATE_TABLE_ITEM);
                }
                oldVersion++;
                onUpgrade(db, oldVersion, newVersion);
            }
        }
    }

    public void insertBattleTag(BattleTag battleTag, String requestId) {
        D3DBJob job = new BattleTagJob(battleTag, D3DBJob.TYPE_INSERT, requestId);
        mJobQueue.add(job);
    }

    public void deleteBattleTag(BattleTag battleTag, String requestId) {
        D3DBJob job = new BattleTagJob(battleTag, D3DBJob.TYPE_DELETE, requestId);
        mJobQueue.add(job);
    }

    public ArrayList<BattleTag> getBattleTagList() {
        ArrayList<BattleTag> battleTagList = new ArrayList<>();
        D3DBJob job = new BattleTagJob(null, D3DBJob.TYPE_QUERY, null);
        Cursor cursor = (Cursor) handleJob(job);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String battleTagString = cursor.getString(cursor.getColumnIndex(COLUMN_BATTLE_TAG));
                String serverString = cursor.getString(cursor.getColumnIndex(COLUMN_SERVER));
                BattleTag battleTag = new BattleTag();
                battleTag.setBattleTag(battleTagString);
                battleTag.setServer(Server.valueOf(serverString));
                battleTagList.add(battleTag);
            } while (cursor.moveToNext());
        }
        return battleTagList;
    }

    public void insertHero(BattleTag battleTag, long heroId, String heroName, String heroClass, int heroGender, String requestId) {
        D3DBJob job = new HeroJob(battleTag, heroId, heroName, heroClass, heroGender, D3DBJob.TYPE_INSERT, requestId);
        mJobQueue.add(job);
    }

    public void deleteHero(BattleTag battleTag, long heroId, String requestId) {
        D3DBJob job = new HeroJob(battleTag, heroId, null, null, -1, D3DBJob.TYPE_DELETE, requestId);
        mJobQueue.add(job);
    }

    public ArrayList<DBHero> getHeroList() {
        ArrayList<DBHero> heroList = new ArrayList<>();
        D3DBJob job = new HeroJob(null, -1, null, null, -1, D3DBJob.TYPE_QUERY, null);
        Cursor cursor = (Cursor) handleJob(job);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String battleTagString = cursor.getString(cursor.getColumnIndex(COLUMN_HERO_BATTLE_TAG));
                long heroId = cursor.getLong(cursor.getColumnIndex(COLUMN_HERO_ID));
                String heroNameString = cursor.getString(cursor.getColumnIndex(COLUMN_HERO_NAME));
                String heroClassString = cursor.getString(cursor.getColumnIndex(COLUMN_HERO_CLASS));
                int heroGender = cursor.getInt(cursor.getColumnIndex(COLUMN_HERO_GENDER));
                String serverString = cursor.getString(cursor.getColumnIndex(COLUMN_HERO_SERVER));
                BattleTag battleTag = new BattleTag();
                battleTag.setBattleTag(battleTagString);
                battleTag.setServer(Server.valueOf(serverString));
                DBHero hero = new DBHero();
                hero.setBattleTag(battleTag);
                hero.setHeroId(heroId);
                hero.setHeroName(heroNameString);
                hero.setHeroClass(heroClassString);
                hero.setHeroGender(heroGender);
                heroList.add(hero);
            } while (cursor.moveToNext());
        }
        return heroList;
    }

    public void insertItem(String tooltipParams, String icon, String name, String color, BattleTag battleTag, long heroId, String heroName, String requestId) {
        D3DBJob job = new ItemJob(tooltipParams, icon, name, color, battleTag, heroId, heroName, D3DBJob.TYPE_INSERT, requestId);
        mJobQueue.add(job);
    }

    public void deleteItem(String tooltipParams, BattleTag battleTag, String requestId) {
        D3DBJob job = new ItemJob(tooltipParams, null, null, null, battleTag, -1, null, D3DBJob.TYPE_DELETE, requestId);
        mJobQueue.add(job);
    }

    public ArrayList<DBItem> getItemList() {
        ArrayList<DBItem> itemList = new ArrayList<>();
        D3DBJob job = new ItemJob(null, null, null, null, null, -1, null, D3DBJob.TYPE_QUERY, null);
        Cursor cursor = (Cursor) handleJob(job);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String tooltipParamsString = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_TOOLTIP_PARAMS));
                String iconString = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_ICON));
                String nameString = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME));
                String colorString = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_COLOR));
                String battleTagString = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_BATTLE_TAG));
                long heroId = cursor.getLong(cursor.getColumnIndex(COLUMN_ITEM_HERO_ID));
                String heroNameString = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_HERO_NAME));
                String serverString = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_SERVER));
                BattleTag battleTag = new BattleTag();
                battleTag.setBattleTag(battleTagString);
                battleTag.setServer(Server.valueOf(serverString));
                DBItem item = new DBItem();
                item.setBattleTag(battleTag);
                item.setTooltipParams(tooltipParamsString);
                item.setIcon(iconString);
                item.setName(nameString);
                item.setColor(colorString);
                item.setHeroId(heroId);
                item.setHeroName(heroNameString);
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        return itemList;
    }
}