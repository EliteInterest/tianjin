package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.EmergencyListInfo;

import java.util.List;

/**
 * Created by Xiangb on 2016/9/20.
 * 功能：预警信息列表适配器
 */
public class EmergencyAdapter extends MyRecycleAdapter {

    private List<EmergencyListInfo> taskItems;
    private Context mContext;
    public Holder myHolder;
    private int mTaskStatus;// 0代表特种设备预警,1维保合同预警,2安全附件,3许可预警

    public EmergencyAdapter(Context c, List<EmergencyListInfo> taskItems, int type) {
        this.taskItems = taskItems;
        this.mContext = c;
        this.mTaskStatus = type;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emergency_list_item, parent, false);
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
            EmergencyListInfo mEntity = taskItems.get(position);
            switch (mTaskStatus) {
                case 0:
                    myHolder.imgTimeType.setBackgroundResource(R.mipmap.device_structure);
                    break;
                case 1:
                    myHolder.imgTimeType.setBackgroundResource(R.mipmap.device_item);
                    break;
                case 2:
                    myHolder.imgTimeType.setBackgroundResource(R.mipmap.device_item);
                    break;
                case 3:
                    myHolder.imgTimeType.setBackgroundResource(R.mipmap.police);
                    break;
                default:
                    break;
            }
            setBaseInfo(mTaskStatus,mEntity);

        } else {
            footerViewHolder = (FooterViewHolder) holder;
        }
    }

    private void setBaseInfo(int type,EmergencyListInfo mEntity){
        if (type==0||type==1||type==2){
            if (mEntity.getfType()!=null)
                myHolder.tvTaskName.setText(mEntity.getfType());
            if (mEntity.getfEntityName()!=null)
                myHolder.tvEntityName.setText(mEntity.getfEntityName());
            if (mEntity.getfInsertDate()!=null)
                myHolder.tvTaskTime.setText(mEntity.getfInsertDate());
        }else if (type==3){
            if (mEntity.getfEntityName()!=null)
                myHolder.tvTaskName.setText(mEntity.getfEntityName());
                LinearLayout parentLayout =  (LinearLayout)myHolder.tvEntityName.getParent();
                parentLayout.setVisibility(View.GONE);
            if (mEntity.getfExpireDate()!=null)
                myHolder.tvTaskTime.setText(mEntity.getfExpireDate());
        }
    }

    @Override
    public int getItemCount() {
        return taskItems == null ? 0 : taskItems.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTaskName, tvEntityName, tvTaskTime,tvType1,tvType2;
        private ImageView imgTimeType;

        public Holder(View parent) {
            super(parent);
            imgTimeType = (ImageView) parent.findViewById(R.id.iv_task_timetype);
            tvTaskName = (TextView) parent.findViewById(R.id.tv_task_name);
            tvEntityName = (TextView) parent.findViewById(R.id.tv_task_entityname);
            tvTaskTime = (TextView) parent.findViewById(R.id.tv_task_time);
            tvType1 = (TextView) parent.findViewById(R.id.tv_tasktype1);
            tvType2 = (TextView) parent.findViewById(R.id.tv_tasktype2);
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
