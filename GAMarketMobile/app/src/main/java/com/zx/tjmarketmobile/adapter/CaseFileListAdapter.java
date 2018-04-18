package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.FileInfoEntity;
import com.zx.tjmarketmobile.util.DateUtil;

import java.util.List;

/**
 * Created by Xiangb on 2017/3/10.
 * 功能：案件执法文档列表适配器
 */
public class CaseFileListAdapter extends MyRecycleAdapter {

    private List<FileInfoEntity> mItemList;
    private Context mContext;
    public Holder myHolder;
    private Context context;

    public CaseFileListAdapter(Context mContext, List<FileInfoEntity> itemList) {
        mItemList = itemList;
        this.mContext = mContext;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complain, parent, false);
        return new Holder(view);
    }

    //从holder中获取控件并设置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            myHolder = (Holder) holder;
            FileInfoEntity entity = mItemList.get(position);
            myHolder.tvName.setText(entity.getName());

        myHolder.tvName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        myHolder.tvName.setText(mItemList.get(position).getName());
        myHolder.tvValue.setText(DateUtil.getDateFromMillis(mItemList.get(position).getUpdateDate()));
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName,tvValue;

        public Holder(View parent) {
            super(parent);
            tvName = (TextView) parent.findViewById(R.id.tvItemComplain_name);
            tvValue = parent.findViewById(R.id.tvItemComplain_value);
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
