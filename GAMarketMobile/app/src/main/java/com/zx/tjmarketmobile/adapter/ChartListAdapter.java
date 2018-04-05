package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Xiangb on 2017/5/18.
 * 功能：统计钻取适配器
 */

public class ChartListAdapter extends MyRecycleAdapter {

    private List<JSONObject> mItemList;
    private Context mContext;
    public Holder myHolder;

    public ChartListAdapter(Context mContext, List<JSONObject> itemList) {
        mItemList = itemList;
        this.mContext = mContext;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chart_list, parent, false);
            return new Holder(view);
        } else {//footer
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_foot_view, parent, false);
            return new FooterViewHolder(view);
        }
    }

    //从holder中获取控件并设置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Holder) {
            JSONObject mEntity = mItemList.get(position);
            myHolder = (Holder) holder;
            String name = "";
            if (getDataInfo(mEntity, "fEntityName") != null) {
                name = getDataInfo(mEntity, "fEntityName");
            }
            myHolder.tvChartName.setText(name);
        } else {
            footerViewHolder = (FooterViewHolder) holder;
        }
    }

    private String getDataInfo(JSONObject jsonObject, String name) {
        if (jsonObject.has(name)) {
            try {
                return jsonObject.getString(name);
            } catch (JSONException e) {
                e.printStackTrace();
                return "";
            }
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvChartName;

        public Holder(View parent) {
            super(parent);
            tvChartName = (TextView) parent.findViewById(R.id.tvChartName);
            parent.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

}
