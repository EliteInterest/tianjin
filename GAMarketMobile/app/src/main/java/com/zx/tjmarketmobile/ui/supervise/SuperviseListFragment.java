package com.zx.tjmarketmobile.ui.supervise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.supervise.SuperviseListAdapter;
import com.zx.tjmarketmobile.entity.HttpMonitor;
import com.zx.tjmarketmobile.entity.HttpMonitorEntityList;
import com.zx.tjmarketmobile.entity.HttpTaskEntity;
import com.zx.tjmarketmobile.entity.HttpZtEntity;
import com.zx.tjmarketmobile.entity.SuperviseDetailInfo;
import com.zx.tjmarketmobile.entity.SuperviseEntity;
import com.zx.tjmarketmobile.entity.SuperviseInfo;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.LoadMoreListener;
import com.zx.tjmarketmobile.listener.MyItemClickListener;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.ui.map.WorkInMapShowActivity;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2016/9/22
 * 功能：监管列表
 */
public class SuperviseListFragment extends BaseFragment implements MyItemClickListener, LoadMoreListener {

    private SuperviseListAdapter mTaskAdapter;
    private List<SuperviseInfo> mTaskList = new ArrayList<SuperviseInfo>();
    ;
    private int mTaskStatus;// 筛选状态标识，0代表全部，1代表即将到期，2代表逾期
    private String mTaskProcedure;// 任务流程
    private SwipeRefreshLayout SRL_supercise;
    private RecyclerView RV_supercise;
    private int mPageSize = 10;
    public int mPageNo = 1;
    public int mTotalNo = 0;
    private Activity mActivity;
    private ApiData taskData = new ApiData(ApiData.HTTP_ID_supervisetask_list);
    public ApiData taskMonitorData = new ApiData(ApiData.HTTP_ID_supervisetask_monitorlist);
    private ApiData taskSuperviseDetailData = new ApiData(ApiData.HTTP_ID_supervisetask_detail);

    public static SuperviseListFragment newInstance(int index,String procedure) {
        SuperviseListFragment details = new SuperviseListFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        details.setArguments(args);
        details.mTaskStatus = index;
        details.mTaskProcedure = procedure;
        return details;
    }

    public void loadData() {
        loadData(false);
    }

    public void loadData(boolean isFirst) {
        if (isFirst) {
            mPageNo = 1;
        }
        if ("任务监控".equals(mTaskProcedure)) {
            String type = "";
            switch (mTaskStatus) {
                case 0:
                    type = "全部";
                    break;
                case 1:
                    type = "即将逾期";
                    break;
                case 2:
                    type = "逾期";
                    break;
            }
            taskMonitorData.loadData(userInfo.getId(), "", mPageNo, mPageSize, type);
        } else {
            taskData.loadData(userInfo.getId(), mPageNo, mPageSize, mTaskStatus, userInfo.getDepartment(),
                    mTaskProcedure, userInfo.getDuty());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPageNo = 1;
        loadData();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.normal_swipe_recycler_view, container, false);
        mActivity = (Activity) getActivity();
        taskData.setLoadingListener(this);
        taskMonitorData.setLoadingListener(this);
        taskSuperviseDetailData.setLoadingListener(this);
        SRL_supercise = (SwipeRefreshLayout) view.findViewById(R.id.srl_normal_layout);
        RV_supercise = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        mTaskAdapter = new SuperviseListAdapter(getActivity(), mTaskList);
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
                loadData();
            }
        });
        return view;
    }


    @Override
    public void LoadMore() {
        if (mPageNo * mPageSize < mTotalNo) {
            mPageNo++;
            mTaskAdapter.setStatus(1, mPageNo, mTotalNo);
            loadData();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        SuperviseInfo superviseInfo = mTaskList.get(position);
//        taskSuperviseDetailData.loadData(superviseInfo.taskId, superviseInfo.guid);

//        Intent intent = new Intent(getActivity(), SuperviseMyTaskDetailActivity.class);
//        intent.putExtra("entity", dataList.get(position));
//        intent.putExtra("index", 1);
//        startActivity(intent);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        SRL_supercise.setRefreshing(false);
        if (b.isSuccess()) {
            switch (id) {
                case ApiData.HTTP_ID_supervisetask_list:
                    SuperviseEntity superviseEntity = (SuperviseEntity) b.getEntry();
                    mTotalNo = superviseEntity.total;
                    if (mActivity instanceof SuperviseListActivity) {
                        SuperviseListActivity slistActivity = (SuperviseListActivity) mActivity;
                        slistActivity.setText(mTaskStatus, mTotalNo);
                    } else if (mActivity instanceof SuperviseSearchActivity) {
                        SuperviseSearchActivity ssearchActivity = (SuperviseSearchActivity) mActivity;
                        ssearchActivity.setText(mTaskStatus, mTotalNo);
                    }
                    mTaskList.clear();
                    mTaskList.addAll(superviseEntity.superviseInfoList);
                    mTaskAdapter.notifyDataSetChanged();
                    RV_supercise.smoothScrollToPosition(0);
                    mTaskAdapter.setStatus(0, mPageNo, mTotalNo);
                    break;
                case ApiData.HTTP_ID_supervisetask_monitorlist:
                    HttpMonitor monitor = (HttpMonitor) b.getEntry();
                    HttpMonitorEntityList entityList = monitor.getMonitorEntityList();
                    mTotalNo = entityList.getTotal();
                    if (mActivity instanceof SuperviseListActivity) {
                        SuperviseListActivity slistActivity = (SuperviseListActivity) mActivity;
                        slistActivity.setText(mTaskStatus, mTotalNo);
                    } else if (mActivity instanceof SuperviseSearchActivity) {
                        SuperviseSearchActivity ssearchActivity = (SuperviseSearchActivity) mActivity;
                        ssearchActivity.setText(mTaskStatus, mTotalNo);
                    }
                    mTaskList.clear();
                    mTaskList.addAll(entityList.getEntityList());
                    mTaskAdapter.notifyDataSetChanged();
                    RV_supercise.smoothScrollToPosition(0);
                    mTaskAdapter.setStatus(0, mPageNo, mTotalNo);
                    break;

                case ApiData.HTTP_ID_supervisetask_detail:
                    SuperviseDetailInfo superviseDetailInfo = (SuperviseDetailInfo) b.getEntry();
                    Intent intent = new Intent(getActivity(), SuperviseDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("task", superviseDetailInfo);
                    bundle.putSerializable("status", mTaskProcedure);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Util.activity_In(mActivity);
                    break;

                default:
                    break;
            }
        }
    }

    public void goToMapActivity() {
        if (mTaskList != null && !mTaskList.isEmpty()) {
            List<HttpTaskEntity> list = new ArrayList<HttpTaskEntity>();
            for (SuperviseInfo superviseInfo : mTaskList) {
                HttpTaskEntity task = new HttpTaskEntity();
                task.setGuid(superviseInfo.guid);
                task.setAddress(superviseInfo.address);
                task.setTaskId(superviseInfo.taskId);
                task.setEntityName(superviseInfo.entityName);
                task.setEntityGuid(superviseInfo.entityGuid);
                task.setTaskName(superviseInfo.taskName);
                task.setTaskType(0);
                task.setWkid(4490);
                task.setTimeType(superviseInfo.timeType);
                task.setLatitude(superviseInfo.latitude);
                task.setLongitude(superviseInfo.longitude);
                HttpZtEntity zt = new HttpZtEntity();
                zt.setCreditCode(superviseInfo.fCreditLevel);
                task.setZtEntity(zt);
                list.add(task);
            }
            Intent intent = new Intent(getActivity(), WorkInMapShowActivity.class);
            intent.putExtra("type", ConstStrings.MapType_Task);
            intent.putExtra("entity", (Serializable) list);
            intent.putExtra("status", mTaskProcedure);
            startActivity(intent);
        } else {
            showToast("无查询结果，不可在地图上查看任务！");
        }
    }
}
