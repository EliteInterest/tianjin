package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.MsgEntity;
import com.zx.tjmarketmobile.listener.MyItemClickListener;
import com.zx.tjmarketmobile.util.ConstStrings;

import java.util.List;

/**
 * Created by Xiangb on 2016/9/19.
 * 功能：消息列表适配器
 */
public class MessageAdapter extends MyRecycleAdapter {

    private List<MsgEntity> mItemList;
    private Context mContext;

    public MessageAdapter(Context mContext, List<MsgEntity> itemList) {
        mItemList = itemList;
        this.mContext = mContext;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg, parent, false);
        return new Holder(view);
    }

    //从holder中获取控件并设置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder myHolder = (Holder) holder;
        MsgEntity msg = mItemList.get(position);
        myHolder.tvName.setText(msg.getName());
        myHolder.tvTime.setText(msg.getTime());
        //未读状态
        int notreadColor = ContextCompat.getColor(mContext, R.color.msgblue);
        int readColor = ContextCompat.getColor(mContext, R.color.gray);
        switch (msg.getStatus()) {
            case 0:
                myHolder.tvName.setTextColor(notreadColor);
                myHolder.tvTime.setTextColor(notreadColor);
                break;
            case 1:
                myHolder.tvName.setTextColor(readColor);
                myHolder.tvTime.setTextColor(readColor);
                break;

            default:
                break;
        }
        //消息类型
        myHolder.tvMsgType.setText(msg.getType());
        if (msg.getType().equals(ConstStrings.Msg_Type_Event)) {
            myHolder.ivMsgType.setBackgroundResource(R.mipmap.msg_yjrw);
            myHolder.tvMsgType.setText(R.string.yjtask);
        } else if (msg.getType().equals(ConstStrings.Msg_Type_Event_Zl)) {
            myHolder.ivMsgType.setBackgroundResource(R.mipmap.msg_yjzl);
            myHolder.tvMsgType.setText(R.string.yjtaskzl);
        } else {
            myHolder.ivMsgType.setBackgroundResource(R.mipmap.msg_tsjb);
            myHolder.tvMsgType.setText(R.string.tstask);
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mListener = listener;
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView tvName, tvTime, tvMsgType;
        private ImageView ivMsgType;

        public Holder(View parent) {
            super(parent);
            tvName = (TextView) parent.findViewById(R.id.tv_msg_name);
            tvTime = (TextView) parent.findViewById(R.id.tv_msg_time);
            tvMsgType = (TextView) parent.findViewById(R.id.tv_msgtype);
            ivMsgType = (ImageView) parent.findViewById(R.id.iv_msgtype);
            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }
    }
}
