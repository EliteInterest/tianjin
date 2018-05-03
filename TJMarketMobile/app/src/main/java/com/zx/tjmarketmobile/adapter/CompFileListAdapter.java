package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.ComplainInfoDetailsBean.BaseInfoBean.FFileNameBean;
import com.zx.tjmarketmobile.util.DateUtil;

import java.util.List;

;

/**
 * Created by Xiangb on 2017/3/10.
 * 功能：案件执法文档列表适配器
 */
public class CompFileListAdapter extends MyRecycleAdapter {

    private List<FFileNameBean> mItemList;
    private Context mContext;
    public Holder myHolder;
    private Context context;

    public CompFileListAdapter(Context mContext, List<FFileNameBean> itemList) {
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
        FFileNameBean entity = mItemList.get(position);
        myHolder.tvName.setText(entity.getRealName());

        myHolder.tvName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        myHolder.tvValue.setText(DateUtil.getDateFromMillis(entity.getSaveDate()));
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        private TextView tvName, tvValue;

        public Holder(View parent) {
            super(parent);
            tvName = (TextView) parent.findViewById(R.id.tvItemComplain_name);
            tvValue = parent.findViewById(R.id.tvItemComplain_value);
        }

    }

}
