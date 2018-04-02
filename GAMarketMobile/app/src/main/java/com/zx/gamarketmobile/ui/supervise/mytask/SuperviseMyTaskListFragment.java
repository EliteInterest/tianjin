package com.zx.gamarketmobile.ui.supervise.mytask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.adapter.supervise.SuperviseMyTaskListAdapter;
import com.zx.gamarketmobile.entity.supervise.MyTaskListEntity;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.http.BaseHttpResult;
import com.zx.gamarketmobile.listener.LoadMoreListener;
import com.zx.gamarketmobile.listener.MyItemClickListener;
import com.zx.gamarketmobile.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzq on 2017/3/23.
 */

public class SuperviseMyTaskListFragment extends BaseFragment implements LoadMoreListener, MyItemClickListener {

    private RecyclerView rvTodo;
    private SwipeRefreshLayout srlTodo;
    private SuperviseMyTaskListAdapter mAdapter;
    private int index;//0待办  1已办
    private List<MyTaskListEntity.RowsBean> dataList = new ArrayList<>();
    private int mPageSize = 10;
    public int mPageNo = 1;
    public int mTotalNo = 0;
    private ApiData getTaskPage = new ApiData(ApiData.HTTP_ID_SuperviseTaskPage);
    private ApiData getTaskHisPage = new ApiData(ApiData.HTTP_ID_SuperviseTaskHisPage);

    public static SuperviseMyTaskListFragment newInstance(int index) {
        SuperviseMyTaskListFragment fragment = new SuperviseMyTaskListFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_to_do_list, container, false);

        index = getArguments().getInt("index");

        rvTodo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        srlTodo = (SwipeRefreshLayout) view.findViewById(R.id.srl_normal_layout);
        rvTodo.setLayoutManager(mLinearLayoutManager);
        getTaskPage.setLoadingListener(this);
        getTaskHisPage.setLoadingListener(this);
        mAdapter = new SuperviseMyTaskListAdapter(getActivity(), dataList, true);
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
        Intent intent = new Intent(getActivity(), SuperviseMyTaskDetailActivity.class);
        intent.putExtra("entity", dataList.get(position));
        intent.putExtra("index", index);
        intent.putExtra("type", 0);
        startActivity(intent);

    }

    @Override
    public void onResume() {
        super.onResume();
//        loadData();
    }

    //数据加载
    private void loadData() {
        if (index == 0) {
//            getTaskPage.loadData(mPageNo, mPageSize, 1, userInfo.getId());
            getTaskPage.loadData(mPageNo, mPageSize, 3);
        } else {
//            getTaskHisPage.loadData(mPageNo, mPageSize, 1, userInfo.getId());
            getTaskPage.loadData(mPageNo, mPageSize, 4);

        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        srlTodo.setRefreshing(false);
        switch (id) {
            case ApiData.HTTP_ID_SuperviseTaskPage:
            case ApiData.HTTP_ID_SuperviseTaskHisPage:
                MyTaskListEntity myTaskListEntity = (MyTaskListEntity) b.getEntry();
                mTotalNo = myTaskListEntity.getTotal();
                mAdapter.setStatus(0, mPageNo, mTotalNo);
                List<MyTaskListEntity.RowsBean> entityList = myTaskListEntity.getRows();
                dataList.clear();
                if (entityList != null) {
                    dataList.addAll(entityList);
                }
                mAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }
    }
}
