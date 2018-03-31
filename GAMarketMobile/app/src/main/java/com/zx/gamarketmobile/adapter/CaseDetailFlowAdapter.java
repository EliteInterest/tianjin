package com.zx.gamarketmobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.entity.CaseFlowEntity;
import com.zx.gamarketmobile.listener.MyItemClickListener;

import java.util.List;

/**
 * Create By Stanny On 2017/3/13
 * 功能：案件详情适配器
 */
public class CaseDetailFlowAdapter extends MyRecycleAdapter {

    private List<CaseFlowEntity> dataList;
    private Context mContext;

    public CaseDetailFlowAdapter(Context c, List<CaseFlowEntity> complainList) {
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
        CaseFlowEntity mEntify = dataList.get(position);
        myHolder.tvTime.setText(mEntify.getfSpsj());
        myHolder.tvPersion.setText(mEntify.getfSpr() + "-\n" + mEntify.getfSpjs());
        myHolder.tvOperate.setText(mEntify.getfLcZztmc());
        String lczz = "";
        if (!"10000".equals(mEntify.getfLcZzt())) {
            lczz = "0".equals(mEntify.getfSpyj()) ? "不通过:" : "通过:";
        }
        if (mEntify.getfSpbz() == null || "null".equals(mEntify.getfSpbz())) {
            myHolder.tvRemark.setText(lczz);
        } else {
            myHolder.tvRemark.setText(lczz + mEntify.getfSpbz());
        }
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