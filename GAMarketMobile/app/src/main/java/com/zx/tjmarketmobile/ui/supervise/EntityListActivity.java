package com.zx.tjmarketmobile.ui.supervise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.R.id;
import com.zx.tjmarketmobile.adapter.EntityAdapter;
import com.zx.tjmarketmobile.entity.EntityListInfo;
import com.zx.tjmarketmobile.entity.EntitySimpleInfo;
import com.zx.tjmarketmobile.entity.SuperviseCountInfo;
import com.zx.tjmarketmobile.entity.SuperviseDetailInfo;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.LoadMoreListener;
import com.zx.tjmarketmobile.listener.MyItemClickListener;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.Util;

import java.util.ArrayList;
import java.util.List;

public class EntityListActivity extends BaseActivity implements OnClickListener, MyItemClickListener, LoadMoreListener {

    private String mStatus = null;
    private String mSearchType = null;
    private String mTaskId = null;
    private int mPageNo = 1;
    private int mPageSize = 15;
    private int mTotalNo = 0;
    private RecyclerView mRvDetail;
    private SwipeRefreshLayout mRefreshLayout;
    private List<EntitySimpleInfo> mEntityList = new ArrayList<>();
    private EntityAdapter mEntityAdapter;
    private SuperviseCountInfo mSuperviseInfo;
    private ApiData taskEntityData = new ApiData(ApiData.HTTP_ID_entity_list);
    private ApiData taskQueryData = new ApiData(ApiData.HTTP_ID_supervisetask_queryallentity);
    private ApiData taskUserQueryData = new ApiData(ApiData.HTTP_ID_supervisetask_queryuserentity);
    private ApiData taskStatusQueryData = new ApiData(ApiData.HTTP_ID_supervisetask_querystatusentity);
    private ApiData taskSuperviseDetailData = new ApiData(ApiData.HTTP_ID_supervisetask_detail);
    private int mQueryEntityType = 0;//调用查询方式0：HTTP_ID_entity_list 1：HTTP_ID_supervisetask_queryallentity
    //2:HTTP_ID_supervisetask_queryuserentity 4：HTTP_ID_supervisetask_querystatusentity
//    private String mType = "全部";//即将逾期、逾期
//    private String mRow = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entitylist);

        addToolBar(true);
        hideRightImg();
        setMidText("主体列表");

        taskSuperviseDetailData.setLoadingListener(this);
        mRvDetail = (RecyclerView) findViewById(id.rv_normal_view);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(id.srl_normal_layout);

        taskEntityData.setLoadingListener(this);
        taskQueryData.setLoadingListener(this);
        taskUserQueryData.setLoadingListener(this);
        taskStatusQueryData.setLoadingListener(this);
        initListView();
        mSuperviseInfo = (SuperviseCountInfo) getIntent().getSerializableExtra("superviseInfo");
        mTaskId = getIntent().getStringExtra("taskId");
        mStatus = getIntent().getStringExtra("status");
        mSearchType = getIntent().getStringExtra("searchType");
//        mType = getIntent().getStringExtra("type");
//        mRow = getIntent().getStringExtra("row");
        if (getIntent().hasExtra("QueryEntityType")) {
            mQueryEntityType = getIntent().getIntExtra("QueryEntityType", 1);
        } else {
            mQueryEntityType = 0;
        }
        loadData();
    }

    private void initListView() {
        //设置适配器及监听
        mRvDetail.setLayoutManager(mLinearLayoutManager);
        mEntityAdapter = new EntityAdapter(this, mEntityList);
        mRvDetail.setAdapter(mEntityAdapter);
        mEntityAdapter.setOnItemClickListener(this);
        mEntityAdapter.setOnLoadMoreListener(this);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mPageNo > 1) {
                    mPageNo--;
                }
                loadData();
            }
        });
    }

    @Override
    public void LoadMore() {
        if (mPageNo * mPageSize < mTotalNo) {
            mPageNo++;
            mEntityAdapter.setStatus(1, mPageNo, mTotalNo);
            loadData();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        EntitySimpleInfo info = mEntityList.get(position);
        boolean mIsDetail = true;
        if (mIsDetail) {
            taskSuperviseDetailData.loadData(mTaskId, info.getF_Guid(), userInfo.getId());
        } else {
            Intent intent = new Intent(this, SuperviseOperateActivity.class);
            intent.putExtra("guid", info.getF_Guid());
            startActivity(intent);
        }
    }

    private void loadData() {
        if (mQueryEntityType == 0) {
            if (mSuperviseInfo != null) {
                String value = mSuperviseInfo.getF_GROUP_ID();
                if ("任务监控".equals(mStatus)){
                    taskEntityData.loadData(mPageNo, mPageSize, mTaskId, "", mSearchType, value, userInfo.getId());
                }else{
                    taskEntityData.loadData(mPageNo, mPageSize, mTaskId, mStatus, mSearchType, value, userInfo.getId());
                }
            }
        } else if (mQueryEntityType == 1) {
            taskQueryData.loadData(userInfo.getId(), mStatus, mTaskId, mPageNo, mPageSize);
        } else if (mQueryEntityType == 2) {
            taskUserQueryData.loadData(userInfo.getId(), mStatus, mTaskId, mPageNo, mPageSize);
        } else {//mQueryEntityType == 3
//            taskStatusQueryData.loadData(userinfo.getId(), mPageNo + "", mPageSize + "", mTaskId, mSearchType, mType, mRow);
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        mRefreshLayout.setRefreshing(false);
        if (b.isSuccess()) {
            switch (id) {
                case ApiData.HTTP_ID_entity_list:
                    EntityListInfo entityLisiInfo = (EntityListInfo) b.getEntry();
                    mTotalNo = entityLisiInfo.getTotal();
                    mEntityList.clear();
                    mEntityList.addAll(entityLisiInfo.getEntityList());
                    mEntityAdapter.notifyDataSetChanged();
                    mEntityAdapter.setStatus(0, mPageNo, mTotalNo);
                    break;
                case ApiData.HTTP_ID_supervisetask_queryuserentity:
                    EntityListInfo userentityList = (EntityListInfo) b.getEntry();
                    mTotalNo = userentityList.getTotal();
                    mEntityList.clear();
                    mEntityList.addAll(userentityList.getEntityList());
                    mEntityAdapter.notifyDataSetChanged();
                    mEntityAdapter.setStatus(0, mPageNo, mTotalNo);
                    break;

                case ApiData.HTTP_ID_supervisetask_querystatusentity:
                    EntityListInfo statusentityList = (EntityListInfo) b.getEntry();
                    mTotalNo = statusentityList.getTotal();
                    mEntityList.clear();
                    mEntityList.addAll(statusentityList.getEntityList());
                    mEntityAdapter.notifyDataSetChanged();
                    mEntityAdapter.setStatus(0, mPageNo, mTotalNo);
                    break;

                case ApiData.HTTP_ID_supervisetask_queryallentity:
                    EntityListInfo entityList = (EntityListInfo) b.getEntry();
                    mTotalNo = entityList.getTotal();
                    mEntityList.clear();
                    mEntityList.addAll(entityList.getEntityList());
                    mEntityAdapter.notifyDataSetChanged();
                    mEntityAdapter.setStatus(0, mPageNo, mTotalNo);
                    break;

                case ApiData.HTTP_ID_supervisetask_detail:
                    SuperviseDetailInfo superviseDetailInfo = (SuperviseDetailInfo) b.getEntry();
                    Intent intent = new Intent(this, SuperviseDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("task", superviseDetailInfo);
                    bundle.putSerializable("status", mStatus);
                    bundle.putSerializable("type", "showMap");
                    intent.putExtras(bundle);
                    startActivityForResult(intent, ConstStrings.Request_Success);
                    Util.activity_In(this);
                    break;

                default:
                    break;
            }

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ConstStrings.Request_Success == resultCode) {
            loadData();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

}
