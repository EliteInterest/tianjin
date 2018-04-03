package com.zx.gamarketmobile.ui.supervise.mytask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.adapter.supervise.SuperviseMyTaskCheckListAdapter;
import com.zx.gamarketmobile.entity.supervise.MyTaskCheckEntity;
import com.zx.gamarketmobile.entity.supervise.MyTaskListEntity;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.http.BaseHttpResult;
import com.zx.gamarketmobile.listener.LoadMoreListener;
import com.zx.gamarketmobile.listener.MyItemClickListener;
import com.zx.gamarketmobile.ui.base.BaseFragment;
import com.zx.gamarketmobile.view.MyTaskAddEntityView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzq on 2017/3/23.
 */

public class SuperviseMyTaskCheckFragment extends BaseFragment implements LoadMoreListener, MyItemClickListener {


    private RecyclerView rvTodo;
    private SwipeRefreshLayout srlTodo;
    private Button addEntity;
    private SuperviseMyTaskCheckListAdapter mAdapter;
    private int index;//0待办  1已办
    private int type;//0我的任务 1任务监控
    private List<MyTaskCheckEntity.RowsBean> dataList = new ArrayList<>();
    private int mPageSize = 10;
    public int mPageNo = 1;
    public int mTotalNo = 0;
    private boolean isResult = false;
    private MyTaskListEntity.RowsBean mEntity;
    public MyTaskAddEntityView addEntityView;

    private ApiData getTaskCheckEmtity = new ApiData(ApiData.HTTP_ID_SuperviseTaskCheckEntity);
    private ApiData getMyTaskCheckEmtity = new ApiData(ApiData.HTTP_ID_SuperviseMyTaskCheckEntity);

    public static SuperviseMyTaskCheckFragment newInstance(MyTaskListEntity.RowsBean rowsBean, int index, int type) {
        SuperviseMyTaskCheckFragment fragment = new SuperviseMyTaskCheckFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        args.putInt("type", type);
        args.putSerializable("mEntity", rowsBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case_to_do_list, container, false);

        mEntity = (MyTaskListEntity.RowsBean) getArguments().getSerializable("mEntity");
        index = getArguments().getInt("index");
        type = getArguments().getInt("type");

        rvTodo = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        addEntity = (Button) view.findViewById(R.id.btn_myTask_addEntity);
        srlTodo = (SwipeRefreshLayout) view.findViewById(R.id.srl_normal_layout);
        rvTodo.setLayoutManager(mLinearLayoutManager);
        getTaskCheckEmtity.setLoadingListener(this);
        getMyTaskCheckEmtity.setLoadingListener(this);
        mAdapter = new SuperviseMyTaskCheckListAdapter(dataList);
        rvTodo.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this);
        addEntityView = new MyTaskAddEntityView(this, mEntity);
        srlTodo.setOnRefreshListener(() -> {
            if (mPageNo > 1) {
                mPageNo--;
            }
            loadData();
        });
        addEntity.setOnClickListener(view1 -> addEntityView.show());
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
        Intent intent = new Intent(getActivity(), SuperviseMyTaskCheckActivity.class);
        intent.putExtra("entity", dataList.get(position));
        intent.putExtra("myToDo", mEntity);
        intent.putExtra("index", index);
        intent.putExtra("type", type);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            isResult = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    //数据加载
    public void loadData() {
//        if (index==1){
//            getTaskCheckEmtity.loadData(mEntity.getFTaskId(),mPageSize, 1,mPageNo, userInfo.getId());
//        }else if (index==0){
//            getMyTaskCheckEmtity.loadData(mEntity.getFTaskId(),userInfo.getId());
//        }
        if (mEntity != null && mEntity.getStatus() == 3/*待处置*/) {
            addEntity.setVisibility(View.VISIBLE);
            getMyTaskCheckEmtity.loadData(mEntity.getId(), userInfo.getId());
        } else {
//            getTaskCheckEmtity.loadData(mEntity.getFTaskId(), mPageSize, 1, mPageNo, userInfo.getId());
            getTaskCheckEmtity.loadData(mEntity.getId(), mPageSize, 1, mPageNo, userInfo.getId());
        }

    }

    @Override
    public void onLoadStart(int id) {
        if (id != ApiData.HTTP_ID_searchzt) {
            super.onLoadStart(id);
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_SuperviseTaskCheckEntity:
                srlTodo.setRefreshing(false);
                MyTaskCheckEntity myTaskListEntity = (MyTaskCheckEntity) b.getEntry();
                mTotalNo = myTaskListEntity.getTotal();
                mAdapter.setStatus(0, mPageNo, mTotalNo);
                List<MyTaskCheckEntity.RowsBean> entityList = myTaskListEntity.getRows();
                dataList.clear();
                dataList.addAll(entityList);
                mAdapter.notifyDataSetChanged();
                break;
            case ApiData.HTTP_ID_SuperviseMyTaskCheckEntity:
                srlTodo.setRefreshing(false);
                MyTaskCheckEntity myToDoTaskListEntity = (MyTaskCheckEntity) b.getEntry();
                List<MyTaskCheckEntity.RowsBean> mEntityList = myToDoTaskListEntity.getRows();
                mAdapter.setStatus(-1, 0, 1);
                dataList.clear();
                dataList.addAll(mEntityList);
                mAdapter.notifyDataSetChanged();
                if (mEntityList.size() == 0 && isResult) {
                    addEntity.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

}
