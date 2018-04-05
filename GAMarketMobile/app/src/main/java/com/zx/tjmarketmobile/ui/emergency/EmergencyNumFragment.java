package com.zx.tjmarketmobile.ui.emergency;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.ui.mainbase.HomeActivity;
import com.zx.tjmarketmobile.entity.TaskCountInfo;
import com.zx.tjmarketmobile.ui.base.BaseFragment;

import java.util.List;

/**
 * Create By Stanny On 2016/9/19
 * 功能：预警信息Fragment
 */
public class EmergencyNumFragment extends BaseFragment implements OnClickListener {

    private TextView tvElevatorNum;
    private TextView tvFoodNum;
    private TextView tvDeviceNum;
    private SwipeRefreshLayout mRefreshLayout;
    private List<TaskCountInfo> mTaskCountInfo;

    public void setTaskCountInfo(List<TaskCountInfo> mTaskCountInfo) {
        this.mTaskCountInfo = mTaskCountInfo;
    }

    public static EmergencyNumFragment newInstance(int index) {
        EmergencyNumFragment details = new EmergencyNumFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        details.setArguments(args);
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emergency, container, false);
        tvElevatorNum = (TextView) view.findViewById(R.id.tvFmEmergency_ElevatorNum);
        tvFoodNum = (TextView) view.findViewById(R.id.tvFmEmergency_FoodNum);
        tvDeviceNum = (TextView) view.findViewById(R.id.tvFmEmergency_devicenum);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SRL_emergencyLayout);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((HomeActivity) getActivity()).loadData();
            }
        });

        view.findViewById(R.id.tvFmEmergency_devicetxt).setOnClickListener(this);
        view.findViewById(R.id.tvFmEmergency_permit).setOnClickListener(this);
        view.findViewById(R.id.tvFmEmergency_devicesafetxt).setOnClickListener(this);
        return view;
    }

    public void updateView() {
        mRefreshLayout.setRefreshing(false);
        tvElevatorNum.setText(mTaskCountInfo.get(0).getEmergencyNum()+"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvFmEmergency_devicetxt:
                goToActivity(0);
                break;
            case R.id.tvFmEmergency_permit:
                goToActivity(1);
                break;
            case R.id.tvFmEmergency_devicesafetxt:
                goToActivity(2);
                break;
            default:
                break;
        }
    }

    private void goToActivity(int flag) {
        if (flag==0){
            Intent intent = new Intent(getActivity(), DeviceEmergencyActivity.class);
            intent.putExtra("type", flag);
            startActivity(intent);
        }else if (flag==1){
            Intent intent = new Intent(getActivity(), PermitEmergencyActivity.class);
            intent.putExtra("type", flag);
            startActivity(intent);
        }else if (flag==2){

        }

    }

}
