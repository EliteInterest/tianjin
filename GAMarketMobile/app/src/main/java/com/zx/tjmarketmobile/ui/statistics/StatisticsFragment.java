package com.zx.tjmarketmobile.ui.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.ui.mainbase.HomeActivity;
import com.zx.tjmarketmobile.adapter.StatisticGVAdapter;
import com.zx.tjmarketmobile.entity.StatisticsInfo;
import com.zx.tjmarketmobile.entity.StatisticsItemInfo;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.view.FullGridView;

/**
 * Create By Stanny On 2016/9/19
 * 功能：统计分析Fragment
 */
public class StatisticsFragment extends BaseFragment {

    private LinearLayout mLlContent;
    private HomeActivity mHomectivity;
    private StatisticsInfo info;
    private SwipeRefreshLayout SRL_taskLayout;

    public static StatisticsFragment newInstance(StatisticsInfo info) {
        StatisticsFragment details = new StatisticsFragment();
        details.info = info;
        return details;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        mHomectivity = (HomeActivity) getActivity();
        mLlContent = (LinearLayout) view.findViewById(R.id.llFmTask_content);
        SRL_taskLayout = (SwipeRefreshLayout) view.findViewById(R.id.SRL_taskLayout);
        SRL_taskLayout.setEnabled(false);
        updateView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mHomectivity.loadData();
    }

    private void updateView() {
        if (null != info) {
            View view = View.inflate(getActivity(), R.layout.item_statistic, null);
            view.setTag(info);
            mLlContent.addView(view);
        }
        updateItemView(info);
    }

    private void updateItemView(StatisticsInfo task) {
        View view = mLlContent.findViewWithTag(task);

        TextView tvName = (TextView) view.findViewById(R.id.tvItemStatistic_name);
        if (TextUtils.isEmpty(task.labelName)) {
            view.findViewById(R.id.llItemTask_name).setVisibility(View.GONE);
        } else {
            tvName.setText(task.labelName);
        }

        final FullGridView gvStatistic = (FullGridView) view.findViewById(R.id.gv_itemStatistic);
        StatisticGVAdapter gvAdapter = new StatisticGVAdapter(this.getActivity(), task.itemList);
        gvStatistic.setAdapter(gvAdapter);
        gvStatistic.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p, View view, int pos, long id) {
                StatisticsItemInfo stcItem = (StatisticsItemInfo) gvStatistic.getItemAtPosition(pos);
                toStatisticsActivity(stcItem);
            }
        });
    }

    private void toStatisticsActivity(StatisticsItemInfo itemInfo) {
//            Intent intent = new Intent(getActivity(), StatisticsActivity.class);
        Intent intent = new Intent(getActivity(), ChartActivity.class);
        intent.putExtra("task", itemInfo);
        startActivity(intent);
    }

}
