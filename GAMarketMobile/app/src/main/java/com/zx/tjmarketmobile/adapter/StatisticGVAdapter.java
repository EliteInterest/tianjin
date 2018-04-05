package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.StatisticsItemInfo;

import java.util.List;

/**
 * Create By Xiangb On 2016/9/23
 * 功能：统计分析列表适配器
 */
public class StatisticGVAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<StatisticsItemInfo> mStcItemList;

    public StatisticGVAdapter(Context context, List<StatisticsItemInfo> stcItemList) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mStcItemList = stcItemList;
    }

    @Override
    public int getCount() {
        return this.mStcItemList.size();
    }

    @Override
    public Object getItem(int pos) {
        return this.mStcItemList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    @Deprecated
    public View getView(int pos, View convertView, ViewGroup parent) {
        StatisticsItemInfo stcItem = mStcItemList.get(pos);
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.gv_statistic, null);
        }

        TextView tvIcon = (TextView) convertView.findViewById(R.id.tvItemStatistic_icon);
        Drawable drawable = mContext.getResources().getDrawable(stcItem.resId);
        if (null != drawable) {
            tvIcon.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        }
        tvIcon.setText(stcItem.name);

        return convertView;
    }

}
