package com.zx.tjmarketmobile.ui.supervise.mytask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.supervise.SuperviseMyTaskListAdapter;
import com.zx.tjmarketmobile.entity.NormalListEntity;
import com.zx.tjmarketmobile.entity.supervise.MyTaskListEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.LoadMoreListener;
import com.zx.tjmarketmobile.listener.MyItemClickListener;
import com.zx.tjmarketmobile.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzq on 2017/3/23.
 */

public class SuperviseMyTaskListFragment extends BaseFragment implements LoadMoreListener, MyItemClickListener {
    private static final String TAG = "SuperviseMyTaskListFragment";
    private RecyclerView rvTodo;
    private SwipeRefreshLayout srlTodo;
    private SuperviseMyTaskListAdapter mAdapter;
    private int index;//0待办  1已办
    private List<MyTaskListEntity> dataList = new ArrayList<>();
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
        mAdapter = new SuperviseMyTaskListAdapter(getActivity(), dataList, index == 0 ? true : false);
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
        loadData();
    }

    //数据加载
    private void loadData() {
        if (index == 0) {//我的待办
            getTaskPage.loadData(mPageNo, mPageSize, 3);
        } else {//我的已办
            getTaskPage.loadData(mPageNo, mPageSize, 4);

        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        srlTodo.setRefreshing(false);
        switch (id) {
            case ApiData.HTTP_ID_SuperviseTaskPage://协同监管-我的任务-我的待办
            case ApiData.HTTP_ID_SuperviseTaskHisPage://协同监管-我的任务-我的已办
                NormalListEntity myTaskListEntity = (NormalListEntity) b.getEntry();
                mTotalNo = myTaskListEntity.getTotal();
                mAdapter.setStatus(0, mPageNo, mTotalNo);
                List<MyTaskListEntity> entityList = myTaskListEntity.getMyTaskListEntities();
                dataList.clear();
                if (entityList != null) {
                    dataList.addAll(entityList);
                }
                mAdapter.notifyDataSetChanged();
                if (entityList.size() > 0) {
                    rvTodo.scrollToPosition(0);
                }

                break;

            default:
                break;
        }
    }
}