package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.listener.MyItemClickListener;

import java.util.List;
import java.util.Map;

/**
 * Created by Xiangb on 2016/9/19.
 * 功能：设置列表适配器
 */
public class SettingAdapter extends MyRecycleAdapter {

    private List<Map<String, String>> mItemList;
    private Context mContext;
    private MyItemClickListener mListener;

    public SettingAdapter(Context mContext, List<Map<String, String>> itemList) {
        mItemList = itemList;
        this.mContext = mContext;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_settinglist, parent, false);
        return new Holder(view);
    }

    //从holder中获取控件并设置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder myHolder = (Holder) holder;
        Map<String, String> itemMap = mItemList.get(position);
        myHolder.tvTaskName.setText(itemMap.get("name"));
        String flag = itemMap.get("flag");
        if ("1".equals(flag)) {
            myHolder.imgTimeType.setBackgroundResource(R.mipmap.update_new);
        } else {
            myHolder.imgTimeType.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mListener = listener;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTaskName;
        private ImageView imgTimeType;

        public Holder(View parent) {
            super(parent);
            parent.setOnClickListener(this);
            tvTaskName = (TextView) parent.findViewById(R.id.about_listview_text);
            imgTimeType = (ImageView) parent.findViewById(R.id.ivsettinglist_img);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}
