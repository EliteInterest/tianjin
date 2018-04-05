package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.SuperviseInfo;

import java.util.List;

/**
 * Created by Xiangb on 2016/9/20.
 * 功能：监管列表适配器
 */
public class SuperviseListAdapter extends MyRecycleAdapter {

    private List<SuperviseInfo> mItemList;
    private Context mContext;
    public Holder myHolder;

    public SuperviseListAdapter(Context mContext, List<SuperviseInfo> itemList) {
        mItemList = itemList;
        this.mContext = mContext;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_task_list, parent, false);
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
            SuperviseInfo mEntity = mItemList.get(position);
            myHolder.tvTaskType.setText(R.string.jgtask);
            myHolder.tvTaskName.setText(mEntity.taskName);
            myHolder.tvEntityName.setText(mEntity.entityName);
            myHolder.tvTaskAddress.setText(mEntity.address);
            try {
                myHolder.tvTaskTime.setText(mEntity.dispatchTime.split(" ")[0]);
            } catch (Exception e) {
                myHolder.tvTaskTime.setText("");
                e.printStackTrace();
            }
            try {
                int id = mContext.getResources().getIdentifier("supervise_status" + mEntity.timeType, "mipmap", mContext.getPackageName());
                Drawable drawable = ContextCompat.getDrawable(mContext, id);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                myHolder.imgTimeType.setBackground(drawable);
            } catch (Exception e) {
                myHolder.imgTimeType.setBackground(null);
                e.printStackTrace();
            }
        } else {
            footerViewHolder = (FooterViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTaskType, tvTaskName, tvEntityName, tvTaskAddress, tvTaskTime;
        private ImageView imgTimeType;

        public Holder(View parent) {
            super(parent);
            imgTimeType = (ImageView) parent.findViewById(R.id.iv_task_timetype);
            tvTaskType = (TextView) parent.findViewById(R.id.tv_task_tasktype);
            tvTaskName = (TextView) parent.findViewById(R.id.tv_task_name);
            tvEntityName = (TextView) parent.findViewById(R.id.tv_task_entityname);
            tvTaskAddress = (TextView) parent.findViewById(R.id.tv_task_address);
            tvTaskTime = (TextView) parent.findViewById(R.id.tv_task_time);
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
