package com.zx.tjmarketmobile.adapter.supervise;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.MyRecycleAdapter;
import com.zx.tjmarketmobile.entity.supervise.MyTaskFlow;
import com.zx.tjmarketmobile.listener.MyItemClickListener;

import java.util.List;

/**
 * Created by zhouzq on 2017/4/1.
 */

public class SuperviseDetialFlowAdapter extends MyRecycleAdapter {

    private List<MyTaskFlow.DataBean> dataList;
    private Context mContext;

    public SuperviseDetialFlowAdapter(Context c, List<MyTaskFlow.DataBean> dataBeanList) {
        this.dataList = dataBeanList;
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
        MyTaskFlow.DataBean mEntify = dataList.get(position);
        myHolder.tvTime.setText(mEntify.getFHandleDate());
        myHolder.tvPersion.setText(mEntify.getFHandleUser());
        myHolder.tvOperate.setText(mEntify.getFLink());
        String lczz = "";
        if (!"10000".equals(mEntify.getFIsPass())) {
            lczz = "1".equals(mEntify.getFIsPass()) ? "通过:" : "不通过:";
        }
        if (mEntify.getFReamrk() == null || "null".equals(mEntify.getFReamrk())) {
            myHolder.tvRemark.setText(lczz);
        } else {
            myHolder.tvRemark.setText(lczz + mEntify.getFReamrk());
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
