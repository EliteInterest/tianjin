package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.EntityDetail;
import com.zx.tjmarketmobile.listener.MyItemClickListener;

import java.util.List;

/**
 * Create By Stanny On 2016/9/22
 * 功能：业务信息适配器
 */
public class BusinessAdapter extends MyRecycleAdapter {
    private List<EntityDetail.BusinessBean.LicBean> mDataList;
    private Context mContext;

    public BusinessAdapter(Context c, List<EntityDetail.BusinessBean.LicBean> complainList) {
        this.mDataList = complainList;
        this.mContext = c;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complain, parent, false);
        return new Holder(view);
    }

    //从holder中获取控件并设置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder myHolder = (Holder) holder;
        EntityDetail.BusinessBean.LicBean task = mDataList.get(position);
        myHolder.tvName.setText(task.getName() + "：");
        myHolder.tvRemark.setText("共" + task.getNum() + "个");
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mListener = listener;
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView tvName, tvRemark;

        public Holder(View parent) {
            super(parent);
            tvName = (TextView) parent.findViewById(R.id.tvItemComplain_name);
            tvRemark = (TextView) parent.findViewById(R.id.tvItemComplain_value);
        }
    }
}
