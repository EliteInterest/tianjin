package com.zx.tjmarketmobile.adapter.supervise;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.MyRecycleAdapter;
import com.zx.tjmarketmobile.entity.supervise.MyTaskListEntity;
import com.zx.tjmarketmobile.util.DateUtil;

import java.util.List;


/**
 * Created by zhouzq on 2017/3/23.
 */

public class SuperviseMyTaskListAdapter extends MyRecycleAdapter {
    private static final String TAG = "SuperviseMyTaskListAdapter";
    private List<MyTaskListEntity> mItemList = null;
    //    private List<MyTaskListTJEntity.RowsBean> mTJItemList = null;
    private Context mContext;
    private boolean showOverdue = false;
    public Holder myHolder;

    public SuperviseMyTaskListAdapter(Context mContext, List<MyTaskListEntity> itemList, boolean showOverdue) {
        mItemList = itemList;
        this.mContext = mContext;
        this.showOverdue = showOverdue;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervise_mytask_list_item, parent, false);
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
//            if (mTJItemList != null) {
            MyTaskListEntity entity = mItemList.get(position);

            myHolder.tvField.setText(entity.getTypeString());
            myHolder.tvDate.setText(DateUtil.getDateFromMillis(entity.getStartDate()));
            myHolder.tvName.setText(entity.getTaskName());
            myHolder.tvPerson.setText(entity.getUserName());
            myHolder.tvStage.setText(entity.getDepartmentName());
            if (entity.getType() == 0) {
                myHolder.ivField.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.super_zh));
            } else if (entity.getType() == 1) {
                myHolder.ivField.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.super_zx));
            } else if (entity.getType() == 2) {
                myHolder.ivField.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.super_ls));
            }
//            } else {
//                MyTaskListEntity.RowsBean entity = mItemList.get(position);
//                myHolder.tvField.setText(entity.getFTaskStatus());
//                myHolder.tvDate.setText(entity.getFCreateDate());
//                myHolder.tvName.setText(entity.getFTaskName());
//                myHolder.tvPerson.setText(entity.getFCreateName());
//                myHolder.tvStage.setText(entity.getFCreateDepartment());
//            }
        } else {
            footerViewHolder = (FooterViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivField, ivOverdue;
        private TextView tvField, tvDate, tvName, tvPerson, tvStage;

        public Holder(View parent) {
            super(parent);
            ivField = (ImageView) parent.findViewById(R.id.iv_supervise_field);
            ivOverdue = (ImageView) parent.findViewById(R.id.iv_supervise_overdue);
            tvField = (TextView) parent.findViewById(R.id.tv_supervise_field);
            tvDate = (TextView) parent.findViewById(R.id.tv_supervise_date);
            tvName = (TextView) parent.findViewById(R.id.tv_supervise_name);
            tvPerson = (TextView) parent.findViewById(R.id.tv_supervise_person);
            tvStage = (TextView) parent.findViewById(R.id.tv_supervise_stage);
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
