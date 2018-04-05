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
import com.zx.tjmarketmobile.entity.EntitySimpleInfo;

import java.util.List;

/**
 * Create By Xiangb On 2016/9/22
 * 功能：主体列表适配器
 */
public class EntityAdapter extends MyRecycleAdapter {

    private List<EntitySimpleInfo> mDataList;
    private Context mContext;
    private Holder myHolder;

    public EntityAdapter(Context c, List<EntitySimpleInfo> checkList) {
        this.mDataList = checkList;
        this.mContext = c;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entity, parent, false);
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
            EntitySimpleInfo mEntity = mDataList.get(position);
            myHolder.tvName.setText(mEntity.getF_Entity_Name());
            Drawable drawable = null;
            try {
                String credit = "credit_" + mEntity.getfCredtieLevel().toLowerCase();
                drawable = ContextCompat.getDrawable(mContext, mContext.getResources().getIdentifier(credit, "mipmap", mContext.getPackageName()));
            } catch (Exception e) {
                drawable = ContextCompat.getDrawable(mContext, R.mipmap.credit_c);
                e.printStackTrace();
            }
            myHolder.ivCredit.setBackground(drawable);
        } else {
            footerViewHolder = (FooterViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName;
        private ImageView ivCredit;

        public Holder(View parent) {
            super(parent);
            tvName = (TextView) parent.findViewById(R.id.tvItemEntity_name);
            ivCredit = (ImageView) parent.findViewById(R.id.ivItemEntity_credit);
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
