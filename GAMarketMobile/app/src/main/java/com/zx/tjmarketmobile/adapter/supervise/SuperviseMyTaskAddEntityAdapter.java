package com.zx.tjmarketmobile.adapter.supervise;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.MyRecycleAdapter;
import com.zx.tjmarketmobile.entity.supervise.MyTaskInfoEntity;

import java.util.List;

/**
 * Created by zhouzq on 2017/4/1.
 */

public class SuperviseMyTaskAddEntityAdapter extends MyRecycleAdapter {

    private List<MyTaskInfoEntity.RowsBean> mItemList;
    public Holder myHolder;

    public SuperviseMyTaskAddEntityAdapter(List<MyTaskInfoEntity.RowsBean> itemList) {
        mItemList = itemList;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_addentity, parent, false);
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
            MyTaskInfoEntity.RowsBean entity = mItemList.get(position);
            myHolder.tvName.setText(entity.getFEntityName());
            myHolder.tvAddress.setText(entity.getF_ADDRESS());
        } else {
            footerViewHolder = (FooterViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName, tvAddress;

        public Holder(View parent) {
            super(parent);
            tvName = (TextView) parent.findViewById(R.id.tv_add_name);
            tvAddress = (TextView) parent.findViewById(R.id.tv_add_address);
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
