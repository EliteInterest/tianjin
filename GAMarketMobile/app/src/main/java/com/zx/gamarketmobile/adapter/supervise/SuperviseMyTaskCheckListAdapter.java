package com.zx.gamarketmobile.adapter.supervise;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.adapter.MyRecycleAdapter;
import com.zx.gamarketmobile.entity.supervise.MyTaskCheckEntity;

import java.util.List;

/**
 * Created by zhouzq on 2017/4/1.
 */

public class SuperviseMyTaskCheckListAdapter extends MyRecycleAdapter {

    private List<MyTaskCheckEntity.RowsBean> mItemList;
    public Holder myHolder;

    public SuperviseMyTaskCheckListAdapter(List<MyTaskCheckEntity.RowsBean> itemList) {
        mItemList = itemList;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervise_mytask_check_item, parent, false);
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
            myHolder = (Holder) holder;
            MyTaskCheckEntity.RowsBean entity = mItemList.get(position);
            myHolder.tvField.setText(entity.getFEntityName());
            myHolder.tvAddress.setText(entity.getfAddress());
            myHolder.tvState.setText(entity.getFSTATUS());
        } else {
            footerViewHolder = (FooterViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvField, tvAddress, tvState;

        public Holder(View parent) {
            super(parent);
            tvField = (TextView) parent.findViewById(R.id.tv_supervise_name);
            tvAddress = (TextView) parent.findViewById(R.id.tv_supervise_address);
            tvState = (TextView)  parent.findViewById(R.id.tv_supervise_state);
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
