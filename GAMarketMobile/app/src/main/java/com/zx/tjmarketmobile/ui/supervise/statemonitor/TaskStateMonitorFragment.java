package com.zx.tjmarketmobile.ui.supervise.statemonitor;

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
import com.zx.tjmarketmobile.ui.supervise.mytask.SuperviseMyTaskDetailActivity;
import com.zx.tjmarketmobile.util.ConstStrings;

import java.util.ArrayList;
import java.util.List;

import static com.zx.tjmarketmobile.http.ApiData.HTTP_ID_supervisetask_getMonitorTask;

/**
 * Created by zhouzq on 2017/4/7.
 */

public class TaskStateMonitorFragment extends BaseFragment implements MyItemClickListener, LoadMoreListener {

    public SuperviseMyTaskListAdapter mTaskAdapter;
    public List<MyTaskListEntity> dataList = new ArrayList<>();
    private int mTaskStatus;// 筛选状态标识，0代表全部，1代表即将到期，2代表逾期
    private SwipeRefreshLayout SRL_supercise;
    private RecyclerView RV_supercise;
    private int mPageSize = 10;
    public int mPageNo = 1;
    public int mTotalNo = 0;
    private TaskStateMonitorActivity mActivity;
    private String curState = ConstStrings.SUPERVISESTATES[0];
    private ApiData getMonitorTask = new ApiData(HTTP_ID_supervisetask_getMonitorTask);


    public static TaskStateMonitorFragment newInstance(TaskStateMonitorActivity activity,int index) {
        TaskStateMonitorFragment details = new TaskStateMonitorFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        details.setArguments(args);
        details.mTaskStatus = index;
        details.mActivity = activity;
        return details;
    }

    public void loadData(String state) {
        curState = state;
        loadData(false);
    }

    public void loadData(boolean isFirst) {
        if (isFirst) {
            mPageNo = 1;
        }
        String type = "";
        switch (mTaskStatus) {
            case 0: //全部
                type = "";
                break;
            case 1://即将逾期
                type = "0";
                break;
            case 2://逾期
                type = "1";
                break;
            default:
                break;
        }
        if (curState.equals(ConstStrings.SUPERVISESTATES[0])){
            getMonitorTask.loadData(mPageNo, mPageSize, 1,userInfo.getId(),type,"");
        }else{
            getMonitorTask.loadData(mPageNo, mPageSize, 1,userInfo.getId(),type,curState);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mPageNo = 1;
        loadData(ConstStrings.SUPERVISESTATES[0]);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.normal_swipe_recycler_view, container, false);
        getMonitorTask.setLoadingListener(this);
        SRL_supercise = (SwipeRefreshLayout) view.findViewById(R.id.srl_normal_layout);
        RV_supercise = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        mTaskAdapter = new SuperviseMyTaskListAdapter(getActivity(), dataList, true);
        RV_supercise.setLayoutManager(mLinearLayoutManager);
        RV_supercise.setAdapter(mTaskAdapter);
        mTaskAdapter.setOnItemClickListener(this);
        mTaskAdapter.setOnLoadMoreListener(this);
        SRL_supercise.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mPageNo > 1) {
                    mPageNo--;
                }
                loadData(curState);
            }
        });
        return view;
    }


    @Override
    public void LoadMore() {
        if (mPageNo * mPageSize < mTotalNo) {
            mPageNo++;
            mTaskAdapter.setStatus(1, mPageNo, mTotalNo);
            loadData(curState);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), SuperviseMyTaskDetailActivity.class);
        intent.putExtra("entity", dataList.get(position));
        intent.putExtra("index", 1);
        intent.putExtra("type",1);
        startActivity(intent);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        SRL_supercise.setRefreshing(false);
        if (b.isSuccess()) {
            switch (id) {
                case ApiData.HTTP_ID_supervisetask_getMonitorTask:
                NormalListEntity myTaskListEntity = (NormalListEntity) b.getEntry();
                if (myTaskListEntity!=null&&myTaskListEntity.getMyTaskListEntities()!=null&&myTaskListEntity.getMyTaskListEntities().size()>=0){
                    mTotalNo = myTaskListEntity.getTotal();
                    mActivity.setText(mTaskStatus,mTotalNo);
                    if (myTaskListEntity.getMyTaskListEntities().size()>0){
                        mTaskAdapter.setStatus(0, mPageNo, mTotalNo);
                        List<MyTaskListEntity> entityList = myTaskListEntity.getMyTaskListEntities();
                        dataList.clear();
                        dataList.addAll(entityList);
                        mTaskAdapter.notifyDataSetChanged();
                    }else{
                        mTaskAdapter.setStatus(0, mPageNo, 0);
                        dataList.clear();
                        mTaskAdapter.notifyDataSetChanged();
                    }

                }
                default:
                    break;
            }
        }
    }




}
