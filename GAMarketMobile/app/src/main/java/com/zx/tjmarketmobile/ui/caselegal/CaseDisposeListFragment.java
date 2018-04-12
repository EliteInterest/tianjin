package com.zx.tjmarketmobile.ui.caselegal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.CaseListAdapter;
import com.zx.tjmarketmobile.entity.CaseInfoEntity;
import com.zx.tjmarketmobile.entity.NormalListEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.LoadMoreListener;
import com.zx.tjmarketmobile.listener.MyItemClickListener;
import com.zx.tjmarketmobile.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2017/3/10
 * 功能：案件待办已办fragment
 */
public class CaseDisposeListFragment extends BaseFragment implements LoadMoreListener, MyItemClickListener {

    private RecyclerView rvTodo;
    private SwipeRefreshLayout srlTodo;
    private CaseListAdapter mAdapter;
    private int index;//0待办  1已办
    private List<CaseInfoEntity> dataList = new ArrayList<>();
    private int mPageSize = 10;
    public int mPageNo = 1;
    public int mTotalNo = 0;
    private CaseMyListFragment caseMyListFragment;
    private ApiData getToDoPage = new ApiData(ApiData.HTTP_ID_caseTaskPage);
    private ApiData getTaskHisPage = new ApiData(ApiData.HTTP_ID_caseTaskHisPage);

    public static CaseDisposeListFragment newInstance(CaseMyListFragment caseMyListFragment, int index) {
        CaseDisposeListFragment fragment = new CaseDisposeListFragment();
        fragment.caseMyListFragment = caseMyListFragment;
        fragment.index = index;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_to_do_list, container, false);
        rvTodo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        srlTodo = (SwipeRefreshLayout) view.findViewById(R.id.srl_normal_layout);
        rvTodo.setLayoutManager(mLinearLayoutManager);
        getToDoPage.setLoadingListener(this);
        getTaskHisPage.setLoadingListener(this);
        mAdapter = new CaseListAdapter(getActivity(), dataList, index == 0 ? true : false);
        rvTodo.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this);
        srlTodo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mPageNo > 1) {
                    mPageNo--;
                }
                loadData();
            }
        });
        return view;
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

    //item点击事件
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), CaseDetailActivity.class);
        intent.putExtra("entity", dataList.get(position));
        intent.putExtra("showExcute", index == 0 ? true : false);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    //数据加载
    private void loadData() {
        if (index == 0) {
            getToDoPage.loadData(mPageNo, mPageSize, "", "", "", "", "");
        } else {
            getTaskHisPage.loadData(mPageNo, mPageSize, "", "", "", "", "");
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        srlTodo.setRefreshing(false);
        switch (id) {
            case ApiData.HTTP_ID_caseTaskPage:
            case ApiData.HTTP_ID_caseTaskHisPage:
                NormalListEntity normalListEntity = (NormalListEntity) b.getEntry();
                mTotalNo = normalListEntity.getTotal();
                mAdapter.setStatus(0, mPageNo, mTotalNo);
                List<CaseInfoEntity> entityList = normalListEntity.getCaseInfoEntityList();
                dataList.clear();
                dataList.addAll(entityList);
                mAdapter.notifyDataSetChanged();
                if (index == 0) {
                    caseMyListFragment.setTabText(0, "待办任务(" + mTotalNo + ")");
                } else if (index == 1) {
                    caseMyListFragment.setTabText(1, "已办任务(" + mTotalNo + ")");
                }
                break;

            default:
                break;
        }
    }
}
