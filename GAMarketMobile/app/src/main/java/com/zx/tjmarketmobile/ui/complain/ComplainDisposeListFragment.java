package com.zx.tjmarketmobile.ui.complain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.ComplainListAdapter;
import com.zx.tjmarketmobile.entity.ComplainInfoEntity;
import com.zx.tjmarketmobile.entity.NormalListEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.LoadMoreListener;
import com.zx.tjmarketmobile.listener.MyItemClickListener;
import com.zx.tjmarketmobile.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2017/3/22
 * 功能：投诉举报待办已办fragment
 */
public class ComplainDisposeListFragment extends BaseFragment implements LoadMoreListener, MyItemClickListener {

    private RecyclerView rvTodo;
    private SwipeRefreshLayout srlTodo;
    private ComplainListAdapter mAdapter;
    private int index;//0待办  1已办
    private String fType;
    private List<ComplainInfoEntity> dataList = new ArrayList<>();
    private int mPageSize = 10;
    public int mPageNo = 1;
    public int mTotalNo = 0;
    private ComplainMyListFragment complainMyListFragment;
    private ApiData getTaskPage = new ApiData(ApiData.HTTP_ID_compTaskPage);

    public static ComplainDisposeListFragment newInstance(ComplainMyListFragment complainMyListFragment, int index) {
        ComplainDisposeListFragment fragment = new ComplainDisposeListFragment();
        fragment.complainMyListFragment = complainMyListFragment;
        fragment.index = index;
        if (index == 0) {
            fragment.fType = "待办";
        } else {
            fragment.fType = "已办";
        }
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
        getTaskPage.setLoadingListener(this);
        mAdapter = new ComplainListAdapter(getActivity(), dataList, true);
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

        /**
         * 这里还没确定
         */

        Intent intent = new Intent(getActivity(), ComplainDetailActivity.class);
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
        getTaskPage.loadData(mPageNo, mPageSize, "", "desc");
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        srlTodo.setRefreshing(false);
        switch (id) {
            case ApiData.HTTP_ID_compTaskPage:
                NormalListEntity entity = (NormalListEntity) b.getEntry();
                mTotalNo = entity.getTotal();
                mAdapter.setStatus(0, mPageNo, mTotalNo);
                List<ComplainInfoEntity> entityList = entity.getComplainInfoList();
                dataList.clear();
                dataList.addAll(entityList);
                mAdapter.notifyDataSetChanged();
                if (index == 0) {
                    complainMyListFragment.setTabText(0, "待办任务(" + mTotalNo + ")");
                } else if (index == 1) {
                    complainMyListFragment.setTabText(1, "已办任务(" + mTotalNo + ")");
                }
                if (entityList.size() > 0) {
                    rvTodo.scrollToPosition(0);
                }
                break;

            default:
                break;
        }
    }
}
