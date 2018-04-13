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
import com.zx.tjmarketmobile.entity.supervise.MyTaskCheckEntity;
import com.zx.tjmarketmobile.util.DateUtil;

import java.util.List;

/**
 * Created by zhouzq on 2017/4/1.
 */

public class SuperviseMyTaskCheckListAdapter extends MyRecycleAdapter {

    private List<MyTaskCheckEntity> mItemList;
    public Holder myHolder;
    private boolean showOverDue;
    private Context context;

    public SuperviseMyTaskCheckListAdapter(List<MyTaskCheckEntity> itemList, boolean showOverDue) {
        mItemList = itemList;
        this.showOverDue = showOverDue;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
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
            MyTaskCheckEntity entity = mItemList.get(position);
            myHolder.tvField.setText(entity.getEnterpriseName());
            myHolder.tvAddress.setText(entity.getLegalPerson());
            myHolder.tvState.setText(DateUtil.getDateFromMillis(entity.getCheckDate()));
            if (entity.getStatus() == 0 && showOverDue == true) {
                myHolder.ivOverDue.setVisibility(View.VISIBLE);
                myHolder.ivOverDue.setBackground(ContextCompat.getDrawable(context, R.mipmap.not_excute));
            } else {
                myHolder.ivOverDue.setVisibility(View.GONE);
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
        private TextView tvField, tvAddress, tvState;
        private ImageView ivOverDue;

        public Holder(View parent) {
            super(parent);
            tvField = (TextView) parent.findViewById(R.id.tv_supervise_name);
            tvAddress = (TextView) parent.findViewById(R.id.tv_supervise_address);
            tvState = (TextView) parent.findViewById(R.id.tv_supervise_state);
            ivOverDue = parent.findViewById(R.id.iv_supervise_overdue);
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