package com.zx.tjmarketmobile.ui.infomanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.infomanager.InfoManagerStandardAdapter;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerBiaozhun;
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

public class StandardMessageSelectFragment extends BaseFragment implements LoadMoreListener, MyItemClickListener {
    private static final String TAG = "StandardMessageSelectFragment";
    private RecyclerView rvTodo;
    private SwipeRefreshLayout srlTodo;
    private InfoManagerStandardAdapter mAdapter;
    private List<InfoManagerBiaozhun.RowsBean> dataList = new ArrayList<>();
    private int mPageSize = 10;
    private String msg = "";
    public int mPageNo = 1;
    public int mTotalNo = 0;
    private ApiData getInfoStandar = new ApiData(ApiData.HTTP_ID_info_manager_biaozhun);

    public static StandardMessageSelectFragment newInstance() {
        StandardMessageSelectFragment fragment = new StandardMessageSelectFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_to_do_list, container, false);

        rvTodo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        srlTodo = (SwipeRefreshLayout) view.findViewById(R.id.srl_normal_layout);
        rvTodo.setLayoutManager(mLinearLayoutManager);
        getInfoStandar.setLoadingListener(this);
        mAdapter = new InfoManagerStandardAdapter(getActivity(), dataList, true);
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
        Intent intent = new Intent(getActivity(), DeviceListDetailActivity.class);
        intent.putExtra("standard", dataList.get(position));
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    //数据加载
    @SuppressLint("LongLogTag")
    private void loadData() {
        if (msg.length() != 0)
            getInfoStandar.loadData(msg, mPageNo, mPageSize);
        else
            getInfoStandar.loadData("", mPageNo, mPageSize);
    }

    public void load(final String msg) {
        this.msg = msg;
        getInfoStandar.loadData(msg, mPageNo, mPageSize);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        srlTodo.setRefreshing(false);
        switch (id) {
            case ApiData.HTTP_ID_info_manager_biaozhun:
                InfoManagerBiaozhun myTaskListEntity = (InfoManagerBiaozhun) b.getEntry();
                mTotalNo = myTaskListEntity.getTotal();
                mAdapter.setStatus(0, mPageNo, mTotalNo);
                List<InfoManagerBiaozhun.RowsBean> entityList = myTaskListEntity.getList();
                dataList.clear();
                if (entityList != null) {
                    dataList.addAll(entityList);
                }
                mAdapter.notifyDataSetChanged();

                InfoManagerBiaozhun.RowsBean bean = null;
                for (int j = 0; j < dataList.size(); j++) {
                    bean = dataList.get(j);
                    Log.i(TAG, "bean is " + bean.getEnterpriseName());
                }

//                if (bean != null)
//                    new ApiData(ApiData.HTTP_ID_superviseTaskBaseInfo).loadData(bean.getId());


//                MyTaskListEntity myTaskListEntity = (MyTaskListEntity) b.getEntry();
//                mTotalNo = myTaskListEntity.getTotal();
//                Log.i(TAG, "mTotalNo is " + mTotalNo);
//                mAdapter.setStatus(0, mPageNo, mTotalNo);
//                List<MyTaskListEntity.RowsBean> entityList = myTaskListEntity.getList();
//                List<MyTaskListEntity.RowsBean> dataList1 = new ArrayList<>();
//                dataList1.clear();
//                if (entityList != null) {
//                    dataList1.addAll(entityList);
//                }

//                mAdapter.setStatus(0, mPageNo, mTotalNo);
//                mAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }
    }
}
