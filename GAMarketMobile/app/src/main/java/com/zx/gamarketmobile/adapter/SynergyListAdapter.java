package com.zx.gamarketmobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.entity.SynergyInfoBean;

import java.util.List;

/**
 * Created by Xiangb on 2017/12/25.
 * 功能：
 */

public class SynergyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SynergyInfoBean> dataList;
    public final static int PARENT_TYPE = 0;
    public final static int CHILD_TYPE = 1;

    public SynergyListAdapter(List<SynergyInfoBean> itemList) {
        dataList = itemList;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == PARENT_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_synergy_list, parent, false);
            return new ParentHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_synergy_entity, parent, false);
            return new ChildHolder(view);
        }
    }

    //从holder中获取控件并设置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SynergyInfoBean mEntity = dataList.get(position);
        if (holder instanceof ParentHolder) {
            ParentHolder mHolder = (ParentHolder) holder;
            mHolder.tvName.setText(mEntity.getFEntityName());
            mHolder.tvNum.setText(mEntity.getNum());
        } else {
            ChildHolder mHolder = (ChildHolder) holder;
            mHolder.tvName.setText(mEntity.getFEntityName());
            mHolder.tvStatus.setText(mEntity.getfStatusDes());
            mHolder.tvData.setText(mEntity.getfInsertDate());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ParentHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvNum;

        public ParentHolder(View parent) {
            super(parent);
            tvName = parent.findViewById(R.id.tv_synergy_list_name);
            tvNum = parent.findViewById(R.id.tv_synergy_list_num);
        }
    }

    class ChildHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvStatus, tvData;

        public ChildHolder(View parent) {
            super(parent);
            tvName = parent.findViewById(R.id.tv_synergy_entity_name);
            tvStatus = parent.findViewById(R.id.tv_synergy_entity_status);
            tvData = parent.findViewById(R.id.tv_synergy_entity_date);
        }
    }
}
