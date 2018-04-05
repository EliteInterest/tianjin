package com.zx.tjmarketmobile.ui.mainbase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.supervise.MonitorPrecessCountEntity;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.ui.supervise.statemonitor.TaskStateMonitorActivity;

/**
 * Create By Stanny On 2016/9/19
 * 功能：投诉举报、监管任务的Fragment
 */
public class TaskNumFragment extends BaseFragment {
    private static String TAG = "TaskNumFragment";
    private LinearLayout mLlContent;
    private SwipeRefreshLayout SRL_taskLayout;
    private HomeActivity mHomectivity;
    private int mTaskType = 0;// 0代表投诉举报，1代表监管任务

    private ApiData countMonitorTask = new ApiData(ApiData.HTTP_ID_supervise_countMonitorTask);

    public static TaskNumFragment newInstance(int index) {
        TaskNumFragment details = new TaskNumFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        details.setArguments(args);
//		details.mTaskType = index;
        return details;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        mTaskType = getArguments().getInt("index");
        mHomectivity = (HomeActivity) getActivity();
        mLlContent = (LinearLayout) view.findViewById(R.id.llFmTask_content);
        SRL_taskLayout = (SwipeRefreshLayout) view.findViewById(R.id.SRL_taskLayout);
        SRL_taskLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                countMonitorTask.loadData(userInfo.getId());
            }
        });

        countMonitorTask.setLoadingListener(this);
//		countMonitorTask.loadData(userInfo.getId());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "enter onResume...");
//        countMonitorTask.loadData(userInfo.getId());
    }

    public void updateView(MonitorPrecessCountEntity task) {
        if (SRL_taskLayout != null) {
            SRL_taskLayout.setRefreshing(false);
        }
        updateItemView(task);
    }

    private void updateItemView(final MonitorPrecessCountEntity task) {
        View view = View.inflate(getActivity(), R.layout.item_task, null);
        TextView tvName = (TextView) view.findViewById(R.id.tvItemTask_name);
        TextView tvAllNum = (TextView) view.findViewById(R.id.tvItemTask_allnum);
        TextView tvJjdqNum = (TextView) view.findViewById(R.id.tvItemTask_jjdqnum);
        TextView tvYqNum = (TextView) view.findViewById(R.id.tvItemTask_yqnum);
        tvName.setText("任务监控");
        mLlContent.removeAllViews();
        mLlContent.addView(view);
        if (task.getTotal() > 0) {
            tvAllNum.setText(task.getTotal() + "");
            tvAllNum.setVisibility(View.VISIBLE);
        } else {
            tvAllNum.setVisibility(View.GONE);
        }
        if (task.getWillOver() > 0) {
            tvJjdqNum.setText(task.getWillOver() + "");
            tvJjdqNum.setVisibility(View.VISIBLE);
        } else {
            tvJjdqNum.setVisibility(View.GONE);
        }
        if (task.getOver() > 0) {
            tvYqNum.setText(task.getOver() + "");
            tvYqNum.setVisibility(View.VISIBLE);
        } else {
            tvYqNum.setVisibility(View.GONE);
        }
        view.findViewById(R.id.tvItemTask_alltxt).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toSearchActivity(task, 0);
            }
        });
        view.findViewById(R.id.tvItemTask_jjdqtxt).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toSearchActivity(task, 1);
            }
        });
        view.findViewById(R.id.tvItemTask_yqtxt).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toSearchActivity(task, 2);
            }
        });
    }

    /**
     * @param task
     * @param type 时间标识，1代表即将过期，2代表逾期，0代表全部
     *             mTaskType 0 投诉举报   1 监管任务
     */
    private void toSearchActivity(MonitorPrecessCountEntity task, int type) {
        Intent intent = new Intent();
        intent.putExtra("task", task);
        intent.putExtra("type", type);
        switch (mTaskType) {
            case 0:
                break;
            case 1:
                intent.setClass(getActivity(), TaskStateMonitorActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        SRL_taskLayout.setRefreshing(false);
        if (b.getEntry() != null) {
            MonitorPrecessCountEntity task = (MonitorPrecessCountEntity) b.getEntry();
            if (task != null) {
                updateView(task);
            }
        }
    }

    @Override
    public void onLoadError(int id, boolean isAPIError, String error_message) {
        super.onLoadError(id, isAPIError, error_message);
        showToast(error_message);
    }
}
