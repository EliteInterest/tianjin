package com.zx.tjmarketmobile.ui.complain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.ComplainListAdapter;
import com.zx.tjmarketmobile.entity.ComplainInfoEntity;
import com.zx.tjmarketmobile.entity.NormalListEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.LoadMoreListener;
import com.zx.tjmarketmobile.listener.MyItemClickListener;
import com.zx.tjmarketmobile.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2017/3/21
 * 功能：投诉举报列表
 */
public class ComplainMonitorActivity extends BaseActivity implements LoadMoreListener, MyItemClickListener, View.OnClickListener {

    private RecyclerView rvSearch;
    private SwipeRefreshLayout srlSearch;
    private ComplainListAdapter mAdapter;
    private List<ComplainInfoEntity> dataList = new ArrayList<>();
    private int mPageSize = 10;
    private int mPageNo = 1;
    private int mTotalNo = 0;
    private String type = "", yqtype = "";
    private ApiData compQuery = new ApiData(ApiData.HTTP_ID_compLcjk);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_swipe_recycler_view);
        initView();
    }

    private void initView() {
        addToolBar(true);
        setMidText("投诉举报列表");
        hideRightImg();

        type = getIntent().getStringExtra("type");
        yqtype = getIntent().getStringExtra("yqtype");
        rvSearch = (RecyclerView) findViewById(R.id.rv_normal_view);
        srlSearch = (SwipeRefreshLayout) findViewById(R.id.srl_normal_layout);
        rvSearch.setLayoutManager(mLinearLayoutManager);
        mAdapter = new ComplainListAdapter(this, dataList, false);
        rvSearch.setAdapter(mAdapter);
        compQuery.setLoadingListener(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this);
        srlSearch.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mPageNo > 1) {
                    mPageNo--;
                }
                loadData();
            }
        });
    }

    //加载更多
    @Override
    public void LoadMore() {
        if (mPageNo * mPageSize < mTotalNo) {
            mPageNo++;
            mAdapter.setStatus(1, mPageNo, mTotalNo);
            loadData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    //item点击事件
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, ComplainDetailActivity.class);
        intent.putExtra("entity", dataList.get(position));
        intent.putExtra("monitor", true);
        startActivity(intent);
    }

    //数据加载
    private void loadData() {
        compQuery.loadData(mPageNo, mPageSize, type);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        srlSearch.setRefreshing(false);
        switch (id) {
            case ApiData.HTTP_ID_compLcjk:
                NormalListEntity bean = (NormalListEntity) b.getEntry();
                mTotalNo = bean.getTotal();
                mAdapter.setStatus(0, mPageNo, mTotalNo);
                List<ComplainInfoEntity> entityList = bean.getComplainInfoList();
                dataList.clear();
                dataList.addAll(entityList);
                mAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }
    }

}
