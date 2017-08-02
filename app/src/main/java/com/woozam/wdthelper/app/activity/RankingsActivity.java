package com.woozam.wdthelper.app.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.woozam.wdthelper.R;
import com.woozam.wdthelper.analystics.Analystics;
import com.woozam.wdthelper.app.view.ProgressDialog;
import com.woozam.wdthelper.app.view.RankingFilterDialog;
import com.woozam.wdthelper.common.ADUtils;
import com.woozam.wdthelper.data.BattleTag;
import com.woozam.wdthelper.data.DataManager;
import com.woozam.wdthelper.data.RankItem;
import com.woozam.wdthelper.data.RankingParameter;
import com.woozam.wdthelper.data.Server;
import com.woozam.wdthelper.network.VolleySingleton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by woozam on 2016-02-13.
 */
public class RankingsActivity extends AppCompatActivity implements Response.ErrorListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    private static final String TAG = RankingsActivity.class.getSimpleName();

    public static void createInstance(Context context) {
        Intent intent = new Intent(context, RankingsActivity.class);
        context.startActivity(intent);
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<RankItem> mRankItemList;
    private RankingsAdapter mRankingsAdapter;
    private ListView mListView;
    private RankingParameter mParams;
    private ProgressDialog mProgressDialog;
    private AdView mAdView;

    private Object mRequestTag = new Object();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.rankings_swipe_refresh_layout);
        mRankItemList = new ArrayList<>();
        mRankingsAdapter = new RankingsAdapter(this, mRankItemList);
        mListView = (ListView) findViewById(R.id.rankings_list_view);
        mProgressDialog = new ProgressDialog(this);
        mAdView = new AdView(this);
        mAdView.setAdUnitId(getString(R.string.ad_unit_id_rankings));
        mAdView.setAdSize(AdSize.SMART_BANNER);

        if (ADUtils.USE_AD) {
            mListView.addHeaderView(mAdView);
        }
        mListView.setAdapter(mRankingsAdapter);
        mListView.setOnItemClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mParams = DataManager.getInstance().getLastRankingParameter();
        loadData();
        mProgressDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdView.destroy();
        VolleySingleton.getInstance(this).getRequestQueue().cancelAll(mRequestTag);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Analystics.screen(TAG);
        mAdView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mAdView.pause();
    }

    @Override
    public void setTitle(final CharSequence title) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ActionBar ab = getSupportActionBar();
                if (ab != null) {
                    ab.setDisplayShowTitleEnabled(false);
                    RankingsActivity.super.setTitle(title);
                    ab.setDisplayShowTitleEnabled(true);
                } else {
                    RankingsActivity.super.setTitle(title);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ranking, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                filter();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void filter() {
        new RankingFilterDialog(this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    mParams = ((RankingFilterDialog) dialog).getParameter();
                    loadData();
                    mProgressDialog.show();
                }
            }
        }).setParameter(mParams).show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        Toast.makeText(this, R.string.load_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        position -= mListView.getHeaderViewsCount();
        if (position >= 0) {
            RankItem rankItem = mRankingsAdapter.getItem(position);
            ProfileActivity.createInstance(this, rankItem.getBattleTag());
            HeroActivity.createInstance(this, rankItem.getBattleTag(), rankItem.getHeroId(), rankItem.getName());
        }
    }

    private void loadData() {
        final Server server = mParams.server;
        DataManager.getInstance().getRanking(this, mParams, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new ParsingTask(server, response).execute();
            }
        }, this, mRequestTag);
        setTitle(mParams.type.getName());
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mRankingsAdapter.getFilter().filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mRankingsAdapter.getFilter().filter(newText);
        return true;
    }

    private class ParsingTask extends AsyncTask<Void, ArrayList<RankItem>, ArrayList<RankItem>> {

        private Server mServer;
        private String mHtml;

        public ParsingTask(Server server, String html) {
            mServer = server;
            mHtml = html;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRankItemList.clear();
            mRankingsAdapter.notifyDataSetChanged();
        }

        @Override
        protected ArrayList<RankItem> doInBackground(Void... params) {
            try {
                ArrayList<RankItem> rankItemList = new ArrayList<>();
                int i = 0;
                Document document = Jsoup.parse(mHtml);
                Elements elements = document.getElementById("ladders-table").getElementsByTag("tbody").get(0).children();
                String lastRank = "";
                for (Element element : elements) {
                    try {
                        Elements data = element.children();
                        Element data0 = data.get(0);
                        Element data1 = data.get(1);
                        Element data2 = data.get(2);
                        Element data3 = data.get(3);
                        Element data4 = data.get(4);
                        String rank = data0.text();
                        if (TextUtils.isEmpty(rank)) {
                            rank = lastRank;
                        } else {
                            lastRank = rank;
                        }
                        String name = data1.text();
                        String battleTag = data1.getElementsByAttribute("href").get(0).attr("href");
                        String icon = data1.getElementsByAttribute("src").get(0).attr("src").replace("/21/", "/64/");
                        int riftLevel = Integer.parseInt(data2.text());
                        String riftTime = data3.text();
                        String completedTime = data4.text();
                        RankItem rankItem = new RankItem();
                        rankItem.setRank(rank);
                        rankItem.setName(name);
                        rankItem.setBattleTag(new BattleTag(battleTag.split("/")[4], mServer));
                        rankItem.setHeroId(Long.parseLong(battleTag.split("/")[6]));
                        rankItem.setIcon(icon);
                        rankItem.setRiftLevel(riftLevel);
                        rankItem.setRiftTime(riftTime);
                        rankItem.setCompletedTime(completedTime);
                        rankItemList.add(rankItem);
                        if (i >= 99) {
                            i = 0;
                            publishProgress(rankItemList);
                            rankItemList = new ArrayList<>();
                        } else {
                            i++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return rankItemList;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @SafeVarargs
        @Override
        protected final void onProgressUpdate(ArrayList<RankItem>... values) {
            super.onProgressUpdate(values);
            mRankItemList.addAll(values[0]);
            mRankingsAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(ArrayList<RankItem> rankItemList) {
            super.onPostExecute(rankItemList);
            if (rankItemList != null) {
                mRankItemList.addAll(rankItemList);
                mRankingsAdapter.notifyDataSetChanged();
            }
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            if (mRankItemList.size() == 0) {
                Toast.makeText(RankingsActivity.this, R.string.load_error, Toast.LENGTH_LONG).show();
            }
            ADUtils.requestAD(mAdView);
        }
    }
}