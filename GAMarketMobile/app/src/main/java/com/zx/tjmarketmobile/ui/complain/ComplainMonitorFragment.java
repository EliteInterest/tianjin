package com.zx.tjmarketmobile.ui.complain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.TaskCountInfo;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2017/3/21
 * 功能：投诉举报监控fragment
 */
public class ComplainMonitorFragment extends BaseFragment {
    private SwipeRefreshLayout srlMonitor;
    private LinearLayout mLlContent;
    private List<TaskCountInfo> dataList = new ArrayList<>();

    private ApiData getMonitorNum = new ApiData(ApiData.HTTP_ID_compMonitor);

    public static ComplainMonitorFragment newInstance() {
        ComplainMonitorFragment fragment = new ComplainMonitorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        mLlContent = (LinearLayout) view.findViewById(R.id.llFmTask_content);
        srlMonitor = (SwipeRefreshLayout) view.findViewById(R.id.SRL_taskLayout);
        getMonitorNum.setLoadingListener(this);
        srlMonitor.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        loadData();
        return view;
    }


    public void updateView() {
        if (null != dataList) {
            mLlContent.removeAllViews();
            for (int i = 0; i < dataList.size(); i++) {
                TaskCountInfo task = dataList.get(i);
                View view = View.inflate(getActivity(), R.layout.item_task, null);
                view.setTag(task);
                mLlContent.addView(view);
                updateItemView(task);
            }
        }
    }

    private void updateItemView(final TaskCountInfo task) {
        View view = mLlContent.findViewWithTag(task);
        TextView tvName = (TextView) view.findViewById(R.id.tvItemTask_name);
        TextView tvAllNum = (TextView) view.findViewById(R.id.tvItemTask_allnum);
        TextView tvJjdqNum = (TextView) view.findViewById(R.id.tvItemTask_jjdqnum);
        TextView tvYqNum = (TextView) view.findViewById(R.id.tvItemTask_yqnum);
        switch (task.status) {
            case "0":
                tvName.setText("信息录入");
                break;
            case "10":
                tvName.setText("待受理");
                break;
            case "20":
                tvName.setText("待分流");
                break;
            case "30":
                tvName.setText("待指派");
                break;
            case "50":
                tvName.setText("待处置");
                break;
            case "60":
                tvName.setText("待审核");
                break;
            case "80":
                tvName.setText("待办结");
                break;
            case "81":
                tvName.setText("待办结");
                break;
            case "90":
                tvName.setText("已办结");
                break;
            default:
                break;
        }
        if (task.allCount > 0) {
            tvAllNum.setText(task.allCount + "");
            tvAllNum.setVisibility(View.VISIBLE);
        } else {
            tvAllNum.setVisibility(View.GONE);
        }
        if (task.soonCount > 0) {
            tvJjdqNum.setText(task.soonCount + "");
            tvJjdqNum.setVisibility(View.VISIBLE);
        } else {
            tvJjdqNum.setVisibility(View.GONE);
        }
        if (task.expireCount > 0) {
            tvYqNum.setText(task.expireCount + "");
            tvYqNum.setVisibility(View.VISIBLE);
        } else {
            tvYqNum.setVisibility(View.GONE);
        }
        view.findViewById(R.id.tvItemTask_alltxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSearchActivity(task, "");
            }
        });
        view.findViewById(R.id.tvItemTask_jjdqtxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSearchActivity(task, "jjyq");
            }
        });
        view.findViewById(R.id.tvItemTask_yqtxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSearchActivity(task, "yq");
            }
        });
    }

    private void toSearchActivity(TaskCountInfo task, String yqtype) {
        Intent intent = new Intent(getActivity(), ComplainMonitorActivity.class);
        intent.putExtra("type", task.status);
        intent.putExtra("yqtype", yqtype);
        startActivity(intent);
    }

    //数据加载
    private void loadData() {
        getMonitorNum.loadData();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_compMonitor:
                srlMonitor.setRefreshing(false);
                List<TaskCountInfo> list = (List<TaskCountInfo>) b.getEntry();
                dataList.clear();
                dataList.addAll(list);
                updateView();
                break;
            default:
                break;
        }
    }

}
