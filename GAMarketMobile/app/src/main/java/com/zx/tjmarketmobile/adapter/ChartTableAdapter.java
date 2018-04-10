package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.KeyValueInfo;
import com.zx.tjmarketmobile.entity.StatisticsItemInfo;
import com.zx.tjmarketmobile.listener.IChartListener;
import com.zx.tjmarketmobile.ui.statistics.ChartActivity;
import com.zx.tjmarketmobile.util.DigitUtil;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Xiangb on 2017/5/17.
 * 功能：统计图表适配器
 */

public class ChartTableAdapter extends MyRecycleAdapter {
    private List<KeyValueInfo> mDataList;
    private Context mContext;
    private double sum = 0;
    private IChartListener mListener;
    private DecimalFormat df = new DecimalFormat("######0.00");
    private StatisticsItemInfo statisticsItemInfo;

    public ChartTableAdapter(Context c, StatisticsItemInfo mItemInfo, List<KeyValueInfo> keyList) {
        this.mDataList = keyList;
        this.mContext = c;
        this.statisticsItemInfo = mItemInfo;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table, parent, false);
        return new Holder(view);
    }

    //从holder中获取控件并设置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder myHolder = (Holder) holder;
        KeyValueInfo mEntity = mDataList.get(position);

        sum = 0;
        for (KeyValueInfo keyValueInfo : mDataList) {
            if (!"合计".equals(keyValueInfo.key) && !"总数".equals(keyValueInfo.key) && !"总计".equals(keyValueInfo.key)) {
                double value = DigitUtil.StringToDouble(keyValueInfo.value);
                sum += value;
            }
        }
        if (sum == 0) {
            sum = 1;
        }

        if (statisticsItemInfo.chartType == 1) {//折线图
            myHolder.tvKey.setText(mEntity.key);
            myHolder.tvValue.setText(mEntity.value);
            myHolder.tvPercent.setVisibility(View.GONE);
            myHolder.tvOther.setVisibility(View.GONE);
        } else if (mEntity.value1 != null || mEntity.value2 != null) {//此时不是简单的名称-数量-比重的类型
            myHolder.tvKey.setText(mEntity.key);
            myHolder.tvValue.setText(mEntity.value);
            myHolder.tvPercent.setText(mEntity.value1);
            myHolder.tvOther.setText(mEntity.value2);
            myHolder.tvOther.setVisibility(mEntity.value2 == null ? View.GONE : View.VISIBLE);
        } else {
            double value = DigitUtil.StringToDouble(mEntity.value);
            myHolder.tvKey.setText(mEntity.key);
            myHolder.tvValue.setText(mEntity.value);
            if (value == 0 || sum == 0) {
                myHolder.tvPercent.setText(0 + "%");
            } else if ("合计".equals(mEntity.key) || "总数".equals(mEntity.key) || "总计".equals(mEntity.key)) {
                myHolder.tvPercent.setText(100.00 + "%");
            } else {
                myHolder.tvPercent.setText(df.format(value * 100 / sum) + "%");
            }
        }
    }

    public void setChartListener(IChartListener chartListener) {
        mListener = chartListener;
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView tvKey, tvValue, tvPercent, tvOther;

        public Holder(View parent) {
            super(parent);
            tvKey = (TextView) parent.findViewById(R.id.tvItemTable_name);
            tvValue = (TextView) parent.findViewById(R.id.tvItemTable_first);
            tvPercent = (TextView) parent.findViewById(R.id.tvItemTable_second);
            tvOther = (TextView) parent.findViewById(R.id.tvItemTable_three);

            tvKey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.OnKeyClick(getAdapterPosition());
                    }
                }
            });
            tvValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null && !mDataList.get(getAdapterPosition()).key.equals("总计")
                            && !mDataList.get(getAdapterPosition()).key.equals("总数") && !mDataList.get(getAdapterPosition()).key.equals("合计")) {
                        if (mDataList.get(getAdapterPosition()).value == null) {
                            return;
                        }
                        if (mDataList.get(getAdapterPosition()).value.equals("0")) {
                            ((ChartActivity) mContext).showToast("该项暂无数据");
                            return;
                        }
                        String line = ((ChartActivity) mContext).tvValue.getText().toString();
                        if (line.equals("数量")) {
                            line = "";
                        }
                        mListener.onValueClick(mDataList.get(getAdapterPosition()).key, line);
                    }
                }
            });
            tvPercent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null && !mDataList.get(getAdapterPosition()).key.equals("总计")
                            && !mDataList.get(getAdapterPosition()).key.equals("总数") && !mDataList.get(getAdapterPosition()).key.equals("合计")) {
                        if (mDataList.get(getAdapterPosition()).value1 == null) {
                            return;
                        }
                        if (mDataList.get(getAdapterPosition()).value1.equals("0")) {
                            ((ChartActivity) mContext).showToast("该项暂无数据");
                            return;
                        }
                        String line = ((ChartActivity) mContext).tvPercent.getText().toString();
                        if (!line.equals("所占比重")) {
                            mListener.onValueClick(mDataList.get(getAdapterPosition()).key, line);
                        }
                    }
                }
            });
            tvOther.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null && !mDataList.get(getAdapterPosition()).key.equals("总计")
                            && !mDataList.get(getAdapterPosition()).key.equals("总数") && !mDataList.get(getAdapterPosition()).key.equals("合计")) {
                        if (mDataList.get(getAdapterPosition()).value2 == null) {
                            return;
                        }
                        if (mDataList.get(getAdapterPosition()).value2.equals("0")) {
                            ((ChartActivity) mContext).showToast("该项暂无数据");
                            return;
                        }
                        String line = ((ChartActivity) mContext).tvOther.getText().toString();
                        if (((ChartActivity) mContext).tvOther.getVisibility() == View.VISIBLE) {
                            mListener.onValueClick(mDataList.get(getAdapterPosition()).key, line);
                        }
                    }
                }
            });
        }
    }
}
