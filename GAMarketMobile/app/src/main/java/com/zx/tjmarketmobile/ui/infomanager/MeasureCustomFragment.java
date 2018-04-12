package com.zx.tjmarketmobile.ui.infomanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.infomanager.MeasureCustomAdapter;
import com.zx.tjmarketmobile.entity.KeyValueInfo;
import com.zx.tjmarketmobile.entity.infomanager.InfoManagerMeasureCustom;
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

public class MeasureCustomFragment extends BaseFragment implements LoadMoreListener, MyItemClickListener {
    private static final String TAG = "StandardMessageSelectFragment";
    private RecyclerView rvTodo;
    private SwipeRefreshLayout srlTodo;
    private MeasureCustomAdapter mAdapter;
    private List<KeyValueInfo> dataList = new ArrayList<>();
    private int mPageSize = 10;
    private String msg = "";
    public int mPageNo = 1;
    public int mTotalNo = 0;
    private ApiData getInfoStandar = new ApiData(ApiData.HTTP_ID_info_manager_measuring_instruments_custom);

    public static MeasureCustomFragment newInstance() {
        MeasureCustomFragment fragment = new MeasureCustomFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_to_do_list, container, false);

        rvTodo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        srlTodo = (SwipeRefreshLayout) view.findViewById(R.id.srl_normal_layout);
        rvTodo.setLayoutManager(mLinearLayoutManager);
        getInfoStandar.setLoadingListener(this);
        mAdapter = new MeasureCustomAdapter(getActivity(), dataList, true);
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

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    //数据加载
    @SuppressLint("LongLogTag")
    private void loadData() {
        if (msg.length() != 0) {
            getInfoStandar.loadData(msg);
        } else
            getInfoStandar.loadData("t_trading_tools");
    }

    public void load(final String msg) {
        Log.i("wws", "searchText is " + msg);
        this.msg = msg;
        getInfoStandar.loadData(msg);
    }

    @SuppressLint("LongLogTag")
    @SuppressWarnings("unchecked")
    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        srlTodo.setRefreshing(false);
        switch (id) {
            case ApiData.HTTP_ID_info_manager_measuring_instruments_custom:
                List<KeyValueInfo> myTaskListEntity = (List<KeyValueInfo>) b.getEntry();
                mTotalNo = myTaskListEntity.size();
                mAdapter.setStatus(0, mPageNo, mTotalNo);
//                List<InfoManagerMeasureCustom.RowsBean> entityList = myTaskListEntity.getList();
                dataList.clear();
                if (myTaskListEntity != null) {
                    dataList.addAll(myTaskListEntity);
                }
                mAdapter.notifyDataSetChanged();
//                mAdapter.notifyDataSetChanged();
//
//                InfoManagerMeasureCustom.RowsBean bean = null;
//                for (int j = 0; j < dataList.size(); j++) {
//                    bean = dataList.get(j);
//                    Log.i(TAG, "bean is " + bean.getName());
//                }

                break;

            default:
                break;
        }
    }

    private void getDataList(List<InfoManagerMeasureCustom> myTaskBaseInfos) {
//        dataList.clear();
//        KeyValueInfo info = new KeyValueInfo("name: ", myTaskBaseInfo.getName());
//        dataList.add(info);
//        info = new KeyValueInfo("type: ", String.valueOf(myTaskBaseInfo.getType()));
//        dataList.add(info);
//        info = new KeyValueInfo("col: ", myTaskBaseInfo.getCol());
//        dataList.add(info);
    }
}
