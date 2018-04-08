package com.zx.tjmarketmobile.ui.caselegal;

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
 * Create By Xiangb On 2017/3/10
 * 功能：案件监控fragment
 */
public class CaseMonitorFragment extends BaseFragment {
    private SwipeRefreshLayout srlMonitor;
    private LinearLayout mLlContent;
    private List<TaskCountInfo> dataList = new ArrayList<>();

    private ApiData getMonitorNum = new ApiData(ApiData.HTTP_ID_caseGetMonitor);

    public static CaseMonitorFragment newInstance() {
        CaseMonitorFragment fragment = new CaseMonitorFragment();
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
            case "01":
                tvName.setText("线索核查");
                break;
            case "N01":
                tvName.setText("不立案反馈");
                break;
            case "Y06":
                tvName.setText("初审");
                break;
            case "Y10":
                tvName.setText("听证");
                break;
            case "Y14":
                tvName.setText("行政处罚");
                break;
            case "02":
                tvName.setText("结案");
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
                toSearchActivity(task, "2");
            }
        });
        view.findViewById(R.id.tvItemTask_jjdqtxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSearchActivity(task, "1");
            }
        });
        view.findViewById(R.id.tvItemTask_yqtxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSearchActivity(task, "0");
            }
        });
    }

    private void toSearchActivity(TaskCountInfo task, String yqtype) {
        Intent intent = new Intent(getActivity(), CaseMonitorActivity.class);
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
            case ApiData.HTTP_ID_caseGetMonitor:
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