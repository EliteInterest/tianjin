package com.zx.gamarketmobile.ui.supervise.mytask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.entity.HttpTaskEntity;
import com.zx.gamarketmobile.entity.KeyValueInfo;
import com.zx.gamarketmobile.entity.supervise.SuperviseEquimentEntity;
import com.zx.gamarketmobile.ui.base.BaseActivity;
import com.zx.gamarketmobile.ui.map.WorkInMapShowActivity;
import com.zx.gamarketmobile.util.ConstStrings;
import com.zx.gamarketmobile.util.Util;

import java.util.ArrayList;
import java.util.List;

public class SuperviseEquipmentDetailActivity extends BaseActivity {

    private SuperviseEquimentEntity mEntity;
    private RecyclerView rvDetail;
    private List<KeyValueInfo> keyValueInfoList = new ArrayList<>();
    private DetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervise_equipment_detail);
        initView();
    }

    public static void startAction(Activity activity, SuperviseEquimentEntity superviseEquimentEntity) {
        Intent intent = new Intent(activity, SuperviseEquipmentDetailActivity.class);
        intent.putExtra("entity", superviseEquimentEntity);
        activity.startActivity(intent);
    }

    private void initView() {
        addToolBar(true);
        setMidText("设备详情");
        mEntity = (SuperviseEquimentEntity) getIntent().getSerializableExtra("entity");
        getRightImg().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuperviseEquipmentDetailActivity.this, WorkInMapShowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type", ConstStrings.MapType_TaskDetail);
                HttpTaskEntity task = generateTaskEntity(mEntity);
                bundle.putSerializable("entity", task);
                intent.putExtras(bundle);
                startActivity(intent);
                Util.activity_In(SuperviseEquipmentDetailActivity.this);
            }
        });

        if (mEntity == null) {
            finish();
            return;
        }
        initMap();

        rvDetail = findViewById(R.id.rv_euqipment_detail);
        rvDetail.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DetailAdapter();
        rvDetail.setAdapter(mAdapter);
    }

    private HttpTaskEntity generateTaskEntity(SuperviseEquimentEntity event) {
        HttpTaskEntity task = new HttpTaskEntity();
        task.setGuid(event.getId());
        task.setAddress(event.getEaddress());
        task.setEntityName(event.getEtype());
        task.setEntityGuid(event.getId());
        task.setTaskName(event.getCategory());
        task.setTaskTime(event.getEbuilddate());
        task.setTaskType(2);
        task.setWkid(4490);
        task.setTimeType(0);
        task.setLatitude(event.getGy());
        task.setLongitude(event.getGx());
        task.setTaskType(2);
        return task;
    }

    private void initMap() {
        keyValueInfoList.add(new KeyValueInfo("设备类型", mEntity.getEtype()));
        keyValueInfoList.add(new KeyValueInfo("设备名称", mEntity.getElet()));
        if ("电梯".equals(mEntity.getCategory())) {
            keyValueInfoList.add(new KeyValueInfo("电梯编号", mEntity.getEusenum()));
        }
        keyValueInfoList.add(new KeyValueInfo("出厂编号", mEntity.getEnums()));
        keyValueInfoList.add(new KeyValueInfo("注册代码", mEntity.getEcode()));
        keyValueInfoList.add(new KeyValueInfo("注册状态", ""));
        keyValueInfoList.add(new KeyValueInfo("注册登记日期", mEntity.getEusedate()));
        keyValueInfoList.add(new KeyValueInfo("行政区域", mEntity.getAreaname()));
        keyValueInfoList.add(new KeyValueInfo("使用地点", mEntity.getEaddress()));
        keyValueInfoList.add(new KeyValueInfo("使用单位", mEntity.getEuser()));
        keyValueInfoList.add(new KeyValueInfo("联系人", mEntity.getUseowner()));
        keyValueInfoList.add(new KeyValueInfo("单位地址", mEntity.getUseaddress()));
        keyValueInfoList.add(new KeyValueInfo("使用单位联系电话", mEntity.getUseownertel()));
        keyValueInfoList.add(new KeyValueInfo("投用日期", mEntity.getEbuilddate()));
        keyValueInfoList.add(new KeyValueInfo("使用状态", mEntity.getUsestate()));
        keyValueInfoList.add(new KeyValueInfo("制造单位", mEntity.getEmadingcom()));
        keyValueInfoList.add(new KeyValueInfo("检验日期", mEntity.getNewchecktime()));
        keyValueInfoList.add(new KeyValueInfo("下次检验日期", mEntity.getNextchecktime()));
        keyValueInfoList.add(new KeyValueInfo("维保单位", mEntity.getRepaircom()));
        keyValueInfoList.add(new KeyValueInfo("维保合同有效期", mEntity.getRepcanuse()));
        keyValueInfoList.add(new KeyValueInfo("维保合同编号", mEntity.getRepton()));
        keyValueInfoList.add(new KeyValueInfo("维保负责人", mEntity.getEtype()));
        keyValueInfoList.add(new KeyValueInfo("负责人联系方式", mEntity.getEtype()));
        if (mEntity.getRepmana() == null || mEntity.getRepmana().length() == 0) {
            keyValueInfoList.add(new KeyValueInfo("维保人员", mEntity.getRepmana()));
        } else {
            keyValueInfoList.add(new KeyValueInfo("维保人员", mEntity.getRepmanb()));
        }
        if (mEntity.getRepmanacall() == null || mEntity.getRepmanacall().length() == 0) {
            keyValueInfoList.add(new KeyValueInfo("维保人员联系方式", mEntity.getRepmanacall()));
        } else {
            keyValueInfoList.add(new KeyValueInfo("维保人员联系方式", mEntity.getRepmanbcall()));
        }
    }

    class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eventinfo, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.tvKey.setText(keyValueInfoList.get(position).key);
            holder.tvValue.setText(keyValueInfoList.get(position).value);
        }

        @Override
        public int getItemCount() {
            return keyValueInfoList.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {

            private TextView tvKey, tvValue;

            public MyHolder(View itemView) {
                super(itemView);
                tvKey = itemView.findViewById(R.id.eventKey);
                tvValue = itemView.findViewById(R.id.eventValue);
            }
        }
    }
}
