package com.zx.tjmarketmobile.ui.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.R.id;
import com.zx.tjmarketmobile.adapter.PaginationAdapter;
import com.zx.tjmarketmobile.entity.EntityDetail;
import com.zx.tjmarketmobile.entity.HttpSearchZtEntity;
import com.zx.tjmarketmobile.entity.HttpZtEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.LoadMoreListener;
import com.zx.tjmarketmobile.listener.MyItemClickListener;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2016/9/22
 * 功能：主体搜索列表
 */
@SuppressWarnings("deprecation")
public class SearchZtListShowActivity extends BaseActivity implements MyItemClickListener, LoadMoreListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private TextView mTextViewCount;
    private PaginationAdapter mAdapter;
    private String mKeyword = "";
    public int mPageNo = 1;
    private boolean mIsCreated = false;
    public int mTotalNo = 0;
    private int currentIndex = -1;
    private ApiData ztsearchData = new ApiData(ApiData.HTTP_ID_searchzt);

    private ApiData taskData = new ApiData(ApiData.HTTP_ID_entity_detail);
    private HttpSearchZtEntity mSearchZtEntity = null;
    private List<HttpZtEntity> mTaskList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchzt_list_show);

        addToolBar(true);
        setMidText("查询结果");
        getRightImg().setOnClickListener(this);

        mIsCreated = true;
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(id.srl_normal_layout);
        mRecyclerView = (RecyclerView) findViewById(id.rv_normal_view);
        mTextViewCount = (TextView) findViewById(id.tv_searchzt_count);

        ztsearchData.setLoadingListener(this);
        taskData.setLoadingListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mKeyword = bundle.getString("keyword");
                mSearchZtEntity = (HttpSearchZtEntity) bundle.getSerializable("entity");
            }
        }
        //设置适配器及监听
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mTaskList = mSearchZtEntity.getZtList();
        mAdapter = new PaginationAdapter(this, mTaskList);
        mRecyclerView.setAdapter(mAdapter);
        mTotalNo = mSearchZtEntity.getTotal();
        mTextViewCount.setText(mSearchZtEntity.getTotal() + "");
        mTextViewCount.setTextColor(getResources().getColor(R.color.white));
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //监听mRecyclerView是否滑动到最后一个
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //当RecyclerView不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        mAdapter.setStatus(0, mPageNo, mTotalNo);
                    }
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mPageNo > 1) {
                    mPageNo--;
                }
                loadData();
            }
        });

        loadData(true);

    }

    @Override
    public void LoadMore() {
        if (mPageNo * 10 <= mTotalNo) {
            mPageNo++;
            mAdapter.setStatus(1, mPageNo, mTotalNo);
            loadData();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        currentIndex = position;
        HttpZtEntity zt = mTaskList.get(position);
        taskData.loadData(zt.getId());
//        taskData.loadData(userInfo.getId(), zt.getGuid(), zt.getCreditLevel(), zt.getfEntityType());

    }

    @Override
    protected void onResume() {
        super.onResume();
//        mPageNo = 1;
//        loadData();
    }

    public void loadData() {
        if (mIsCreated) {
            loadData(false);
        }
    }

    public void loadData(boolean isFirst) {
        if (isFirst) {
            mPageNo = 1;
        }
        ztsearchData.loadData(mPageNo, "10", mKeyword+"*");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.toolbar_right:
                if (mSearchZtEntity.getZtList().size() > 0) {
                    Intent intent = new Intent(SearchZtListShowActivity.this, WorkInMapShowActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", ConstStrings.MapType_SearchZt);
                    bundle.putSerializable("entity", mSearchZtEntity);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Util.activity_In(this);
                } else {
                    showToast("无查询结果，不可在地图上查看主体！");
                }
                break;

            default:
                break;
        }

    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        mSwipeRefreshLayout.setRefreshing(false);
        switch (id) {
            case ApiData.HTTP_ID_searchzt:
                mSearchZtEntity = (HttpSearchZtEntity) b.getEntry();
                mTotalNo = mSearchZtEntity.getTotal();
                mTextViewCount.setText(mTotalNo + "");
                mTaskList.clear();
                mTaskList.addAll(mSearchZtEntity.getZtList());
                mAdapter.notifyDataSetChanged();
                mRecyclerView.smoothScrollToPosition(0);
                mAdapter.setStatus(0, mPageNo, mTotalNo);
                break;

            case ApiData.HTTP_ID_entity_detail:
                HttpZtEntity zt = mTaskList.get(currentIndex);
                EntityDetail mEntityDetail = (EntityDetail) b.getEntry();
                Intent intent = new Intent(SearchZtListShowActivity.this, EntityDetailActivity.class);
                intent.putExtra("type", 2);
                intent.putExtra("ztEntity", zt);
                intent.putExtra("entity", mEntityDetail);
//                if (zt != null) {
//                    String fEntityType = zt.getfEntityType();
//                    String fEntityGuid = zt.getGuid();
//                    intent.putExtra("fEntityType", fEntityType);
//                    intent.putExtra("fEntityGuid", fEntityGuid);
//                }
                startActivity(intent);
                Util.activity_In(this);
                break;

            default:
                break;
        }

    }


}
