package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.ComplainInfoDetailsBean;
import com.zx.tjmarketmobile.listener.MyItemClickListener;
import com.zx.tjmarketmobile.util.DateUtil;

import java.util.List;

/**
 * Create By Stanny On 2017/3/22
 * 功能：投诉举报流程适配器
 */
public class ComplainDetailFlowAdapter extends MyRecycleAdapter {

    private List<ComplainInfoDetailsBean.StatusInfoBean> dataList;
    private Context mContext;

    public ComplainDetailFlowAdapter(Context c, List<ComplainInfoDetailsBean.StatusInfoBean> complainList) {
        this.dataList = complainList;
        this.mContext = c;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dispose, parent, false);
        return new Holder(view);
    }

    //从holder中获取控件并设置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder myHolder = (Holder) holder;
        ComplainInfoDetailsBean.StatusInfoBean mEntify = dataList.get(position);
        myHolder.tvTime.setText(DateUtil.getDateFromMillis(mEntify.getFDisposeDate()));
        myHolder.tvPersion.setText(mEntify.getFRealName());
        myHolder.tvOperate.setText(mEntify.getFDispose());
        myHolder.tvRemark.setText(mEntify.getFDisposeRemark());
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mListener = listener;
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView tvTime, tvPersion, tvOperate, tvRemark;

        public Holder(View parent) {
            super(parent);
            tvTime = (TextView) parent.findViewById(R.id.tvItemDispose_time);
            tvPersion = (TextView) parent.findViewById(R.id.tvItemDispose_persion);
            tvOperate = (TextView) parent.findViewById(R.id.tvItemDispose_operate);
            tvRemark = (TextView) parent.findViewById(R.id.tvItemDispose_remark);
        }
    }

}