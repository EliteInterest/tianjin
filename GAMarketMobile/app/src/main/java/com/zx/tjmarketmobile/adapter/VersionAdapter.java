package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.VersionInfo;
import com.zx.tjmarketmobile.listener.MyItemClickListener;

import java.util.List;

/**
 * Created by Xiangb on 2016/9/19.
 * 功能：系统通知列表适配器
 */
public class VersionAdapter extends MyRecycleAdapter {

    private List<VersionInfo> mItemList;
    private Context mContext;
    private MyItemClickListener mListener;

    public VersionAdapter(Context mContext, List<VersionInfo> itemList) {
        mItemList = itemList;
        this.mContext = mContext;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_version, parent, false);
        return new Holder(view);
    }

    //从holder中获取控件并设置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder myHolder = (Holder) holder;
        VersionInfo mEntity = mItemList.get(position);
        myHolder.tvCode.setText("版本" + mEntity.versionCode + "主要更新");
        myHolder.tvDate.setText(mEntity.updateDate);
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mListener = listener;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvCode, tvDate;

        public Holder(View parent) {
            super(parent);
            tvCode = (TextView) parent.findViewById(R.id.tvItemVersion_code);
            tvDate = (TextView) parent.findViewById(R.id.tvItemVersion_date);
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
