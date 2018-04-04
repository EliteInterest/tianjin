package com.zx.gamarketmobile.ui.emergency;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.ui.base.BaseFragment;
import com.zx.gamarketmobile.ui.map.WorkInMapShowActivity;
import com.zx.gamarketmobile.adapter.EmergencyAdapter;
import com.zx.gamarketmobile.entity.EmergencyInfo;
import com.zx.gamarketmobile.entity.EmergencyListInfo;
import com.zx.gamarketmobile.entity.HttpTaskEntity;
import com.zx.gamarketmobile.http.ApiData;
import com.zx.gamarketmobile.http.BaseHttpResult;
import com.zx.gamarketmobile.listener.LoadMoreListener;
import com.zx.gamarketmobile.listener.MyItemClickListener;
import com.zx.gamarketmobile.util.ConstStrings;
import com.zx.gamarketmobile.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2016/9/23
 * 功能：预警信息列表
 */
public class DeviceEmergencyListFragment extends BaseFragment implements MyItemClickListener,LoadMoreListener{

    private EmergencyAdapter mTaskAdapter;
    private List<EmergencyListInfo> mTaskList = new ArrayList<>();;
    private int mTaskStatus;// 0代表特种设备预警,1维保合同预警,2安全附件,3许可预警
    private String mTaskProcedure;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private int mPageSize = 10;
    public int mPageNo = 1;
    public int mTotalNo = 0;
    private DeviceEmergencyActivity mActivity;
    private ApiData deviceEmergencyData = new ApiData(ApiData.HTTP_ID_getDeviceEmergencyListInfos);
    private ApiData maintenanceContractData = new ApiData(ApiData.HTTP_ID_getMaintenanceContractDataListInfos);
    private ApiData saftFileData = new ApiData(ApiData.HTTP_ID_getSafeFileListInfos);

    public static DeviceEmergencyListFragment newInstance(int index, String procedure) {
        DeviceEmergencyListFragment details = new DeviceEmergencyListFragment();
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
        if (mTaskStatus==0){
            deviceEmergencyData.loadData(mPageNo, mPageSize, 1);
        }else if (mTaskStatus==1){
            maintenanceContractData.loadData(mPageNo, mPageSize, 1);
        }else if (mTaskStatus==2){
            saftFileData.loadData(mPageNo, mPageSize, 1);
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
        mActivity = (DeviceEmergencyActivity) getActivity();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_normal_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_normal_view);
        deviceEmergencyData.setLoadingListener(this);
        maintenanceContractData.setLoadingListener(this);
        saftFileData.setLoadingListener(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mTaskAdapter = new EmergencyAdapter(getActivity(), mTaskList, mTaskStatus);
        mRecyclerView.setAdapter(mTaskAdapter);
        mTaskAdapter.setOnLoadMoreListener(this);
        mTaskAdapter.setOnItemClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        EmergencyListInfo emergencyInfo = mTaskList.get(position);
        Intent intent = new Intent(getActivity(), DeviceEmergencyDetialActivity.class);
        intent.putExtra("entity", emergencyInfo);
        intent.putExtra("mTaskProcedure", mTaskProcedure);
        startActivity(intent);
        Util.activity_In(getActivity());
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        mSwipeRefreshLayout.setRefreshing(false);
        if (b.isSuccess()) {
            switch (id) {
                case ApiData.HTTP_ID_getDeviceEmergencyListInfos:
                    EmergencyInfo deviceEmergencyInfo = (EmergencyInfo) b.getEntry();
                    if (null != deviceEmergencyInfo && null != deviceEmergencyInfo.emergencyList) {
                        mTotalNo = deviceEmergencyInfo.total;
                        mActivity.mTabLayout.getTabAt(0).setText("特种设备预警（" + mTotalNo + "）");
                        mTaskList.clear();
                        mTaskList.addAll(deviceEmergencyInfo.emergencyList);
                        mTaskAdapter.notifyDataSetChanged();
                        mRecyclerView.smoothScrollToPosition(0);
                    }
                    mTaskAdapter.setStatus(0, mPageNo, mTotalNo);
                    break;

                case ApiData.HTTP_ID_getMaintenanceContractDataListInfos:
                    EmergencyInfo contractEmergencyInfo = (EmergencyInfo) b.getEntry();
                    if (null != contractEmergencyInfo && null != contractEmergencyInfo.emergencyList) {
                        mTotalNo = contractEmergencyInfo.total;
                        mActivity.mTabLayout.getTabAt(1).setText("维保合同预警（" + mTotalNo + "）");
                        mTaskList.clear();
                        mTaskList.addAll(contractEmergencyInfo.emergencyList);
                        mTaskAdapter.notifyDataSetChanged();
                        mRecyclerView.smoothScrollToPosition(0);
                    }
                    mTaskAdapter.setStatus(0, mPageNo, mTotalNo);
                    break;
                case ApiData.HTTP_ID_getSafeFileListInfos:
                    EmergencyInfo saftFileEmergencyInfo = (EmergencyInfo) b.getEntry();
                    if (null != saftFileEmergencyInfo && null != saftFileEmergencyInfo.emergencyList) {
                        mTotalNo = saftFileEmergencyInfo.total;
                        mActivity.mTabLayout.getTabAt(2).setText("安全附件预警（" + mTotalNo + "）");
                        mTaskList.clear();
                        mTaskList.addAll(saftFileEmergencyInfo.emergencyList);
                        mTaskAdapter.notifyDataSetChanged();
                        mRecyclerView.smoothScrollToPosition(0);
                    }
                    mTaskAdapter.setStatus(0, mPageNo, mTotalNo);
                    break;
                default:
                    break;
            }
        }
    }

    public void goToMapActivity() {
        if (mTaskList != null && !mTaskList.isEmpty()) {
            List<HttpTaskEntity> list = new ArrayList<HttpTaskEntity>();
            for (EmergencyListInfo superviseInfo : mTaskList) {
                HttpTaskEntity task = new HttpTaskEntity();
                task.setGuid(superviseInfo.getfGuid());
                task.setAddress(superviseInfo.getfAddress());
                task.setEntityName(superviseInfo.getfEntityName());
                task.setEntityGuid(superviseInfo.getfEntityGuid());
                task.setTaskName(superviseInfo.getfCategory());
                task.setTaskTime(superviseInfo.getfInsertDate());
                task.setTaskType(2);
                task.setWkid(4490);
                task.setTimeType(0);
                task.setLatitude(superviseInfo.getfLatitude());
                task.setLongitude(superviseInfo.getfLongitude());
                list.add(task);
            }
            Intent intent = new Intent(getActivity(), WorkInMapShowActivity.class);
            intent.putExtra("type", ConstStrings.MapType_Task);
            intent.putExtra("entity", (Serializable) list);
            startActivity(intent);
        } else {
            showToast("无查询结果，不可在地图上查看任务！");
        }
    }

}
