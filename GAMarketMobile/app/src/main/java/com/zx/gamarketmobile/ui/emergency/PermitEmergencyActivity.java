package com.zx.gamarketmobile.ui.emergency;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.ui.base.BaseActivity;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhouzq on 2016/11/29.
 */
public class PermitEmergencyActivity extends BaseActivity implements View.OnClickListener, MyItemClickListener, LoadMoreListener {

    private EmergencyAdapter mTaskAdapter;
    private List<EmergencyListInfo> mTaskList = new ArrayList<>();
    ;
    private int mTaskStatus = 3;// 0代表特种设备预警,1维保合同预警,2安全附件,3许可预警
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private int mPageSize = 10;
    public int mPageNo = 1;
    public int mTotalNo = 0;
    private PermitEmergencyActivity mActivity;
    private Spinner typeSpinner;
    private TextView tvNumText;
    private HashMap<String, String> typeNameHash;
    private ApiData licienceEmergencyData = new ApiData(ApiData.HTTP_ID_getLicYjEmergencyListInfos);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permit_emergency);

        addToolBar(true);
        getRightImg().setOnClickListener(this);
        setMidText("许可预警信息");


        initiView();
    }


    private void initiView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_normal_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_normal_view);
        typeSpinner = (Spinner) findViewById(R.id.permit_type_spinner);
        tvNumText = (TextView) findViewById(R.id.tv_num);
        String[] typeValueArr = new String[]{"cateringService", "gmp", "gsp", "drugProduce", "drugSale",
                "drugWholeSale", "equipment", "foodCirculate", "foodSale", "industryProduce", "medicalDeciceProduce", "medicalDeviceSale"};
        typeNameHash = new HashMap<>();
        String[] typeNameArr = new String[]{"餐饮服务", "药品GMP", "药品GSP", "药品生产", "药品销售",
                "药品批发", "特种设备", "食品流通", "食品零售", "工业生产", "医疗器械生产", "医疗器械销售"};
        for (int i = 0; i < typeNameArr.length; i++) {
            typeNameHash.put(typeNameArr[i], typeValueArr[i]);
        }
        ArrayAdapter<String> queryTypeAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_layout, Arrays.asList(typeNameArr));
        typeSpinner.setAdapter(queryTypeAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                tv.setTextColor(ContextCompat.getColor(PermitEmergencyActivity.this, R.color.white));    //设置颜色
                tv.setTextSize(14.0f);    //设置大小
                tv.setGravity(Gravity.CENTER);   //设置居中
                loadData(typeNameHash.get(typeSpinner.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        licienceEmergencyData.setLoadingListener(this);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mTaskAdapter = new EmergencyAdapter(this, mTaskList, mTaskStatus);
        mRecyclerView.setAdapter(mTaskAdapter);
        mTaskAdapter.setOnLoadMoreListener(this);
        mTaskAdapter.setOnItemClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mPageNo > 1) {
                    mPageNo--;
                }
                loadData(typeNameHash.get(typeSpinner.getSelectedItem().toString()));
            }
        });

    }

    public void loadData(String type) {
        loadData(false, type);
    }

    public void loadData(boolean isFirst, String type) {
        if (isFirst) {
            mPageNo = 1;
        }
        licienceEmergencyData.loadData(mPageNo, mPageSize, type);
    }

    @Override
    public void LoadMore() {
        if (mPageNo * mPageSize < mTotalNo) {
            mPageNo++;
            mTaskAdapter.setStatus(1, mPageNo, mTotalNo);
            loadData(typeNameHash.get(typeSpinner.getSelectedItem().toString()));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mPageNo = 1;
        loadData(typeNameHash.get(typeSpinner.getSelectedItem().toString()));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_right:
                goToMapActivity();
                break;
            default:
                break;
        }
    }

    public void onItemClick(View view, int position) {
        EmergencyListInfo emergencyInfo = mTaskList.get(position);
        Intent intent = new Intent(this, PermitEmergencyDetialActivity.class);
        intent.putExtra("entity", emergencyInfo);
        intent.putExtra("mTaskProcedure", "许可预警");
        intent.putExtra("type", typeNameHash.get(typeSpinner.getSelectedItem().toString()));
        startActivity(intent);
        Util.activity_In(this);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        mSwipeRefreshLayout.setRefreshing(false);
        if (b.isSuccess()) {
            switch (id) {
                case ApiData.HTTP_ID_getLicYjEmergencyListInfos:
                    EmergencyInfo emergencyInfo = (EmergencyInfo) b.getEntry();
                    if (null != emergencyInfo && null != emergencyInfo.emergencyList) {
                        mTotalNo = emergencyInfo.total;
                        tvNumText.setText("共（" + mTotalNo + "）条");
                        mTaskList.clear();
                        mTaskList.addAll(emergencyInfo.emergencyList);
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
            Intent intent = new Intent(this, WorkInMapShowActivity.class);
            intent.putExtra("type", ConstStrings.MapType_Task);
            intent.putExtra("entity", (Serializable) list);
            startActivity(intent);
        } else {
            showToast("无查询结果，不可在地图上查看任务！");
        }
    }

}
