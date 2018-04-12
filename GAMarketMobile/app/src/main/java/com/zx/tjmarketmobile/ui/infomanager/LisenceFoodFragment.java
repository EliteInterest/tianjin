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
import com.zx.tjmarketmobile.adapter.infomanager.InfoLisenceFoodAdapter;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerLicenseFood;
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

public class LisenceFoodFragment extends BaseFragment implements LoadMoreListener, MyItemClickListener {
    private static final String TAG = "StandardMessageSelectFragment";
    private RecyclerView rvTodo;
    private SwipeRefreshLayout srlTodo;
    private InfoLisenceFoodAdapter mAdapter;
    private List<InfoManagerLicenseFood.RowsBean> dataList = new ArrayList<>();
    private int mPageSize = 10;
    private String msg = "";
    public int mPageNo = 1;
    public int mTotalNo = 0;
    private ApiData getInfoStandar = new ApiData(ApiData.HTTP_ID_info_manager_license_food);

    public static LisenceFoodFragment newInstance() {
        LisenceFoodFragment fragment = new LisenceFoodFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_to_do_list, container, false);

        rvTodo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        srlTodo = (SwipeRefreshLayout) view.findViewById(R.id.srl_normal_layout);
        rvTodo.setLayoutManager(mLinearLayoutManager);
        getInfoStandar.setLoadingListener(this);
        mAdapter = new InfoLisenceFoodAdapter(getActivity(), dataList, true);
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
        Intent intent = new Intent(getActivity(), LisenceDetailActivity.class);
        intent.putExtra("entity", dataList.get(position));
        intent.putExtra("type", 0);
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
            getInfoStandar.loadData(mPageNo, mPageSize, msg);
        else
            getInfoStandar.loadData(mPageNo, mPageSize);
    }

    public void load(final String msg) {
        Log.i("wws", "searchText is " + msg);
        this.msg = msg;
        getInfoStandar.loadData(mPageNo, mPageSize, msg);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        srlTodo.setRefreshing(false);
        switch (id) {
            case ApiData.HTTP_ID_info_manager_license_food:
                InfoManagerLicenseFood myTaskListEntity = (InfoManagerLicenseFood) b.getEntry();
                mTotalNo = myTaskListEntity.getTotal();
                mAdapter.setStatus(0, mPageNo, mTotalNo);
                List<InfoManagerLicenseFood.RowsBean> entityList = myTaskListEntity.getList();
                dataList.clear();
                if (entityList != null) {
                    dataList.addAll(entityList);
                }
                mAdapter.notifyDataSetChanged();

                InfoManagerLicenseFood.RowsBean bean = null;
                for (int j = 0; j < dataList.size(); j++) {
                    bean = dataList.get(j);
                    Log.i(TAG, "bean is " + bean.getEnterpriseName());
                }

                break;

            default:
                break;
        }
    }
}
