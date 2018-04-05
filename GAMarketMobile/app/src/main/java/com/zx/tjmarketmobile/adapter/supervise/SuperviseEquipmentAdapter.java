package com.zx.tjmarketmobile.adapter.supervise;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.MyRecycleAdapter;
import com.zx.tjmarketmobile.entity.supervise.SuperviseEquimentEntity;
import com.zx.tjmarketmobile.listener.OnEquipmentLocationListener;

import java.util.List;

/**
 * Created by Xiangb on 2017/12/20.
 * 功能：
 */

public class SuperviseEquipmentAdapter extends MyRecycleAdapter {

    private List<SuperviseEquimentEntity> dataList;
    private Context context;
    private OnEquipmentLocationListener equipmentLocationListener;

    public SuperviseEquipmentAdapter(Context context, List<SuperviseEquimentEntity> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_equipment, parent, false);
            return new MyHolder(view);
        } else {//footer
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_foot_view, parent, false);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (holder instanceof MyHolder) {
            MyHolder myHolder = (MyHolder) holder;
            SuperviseEquimentEntity entity = dataList.get(position);
            myHolder.tvType.setText(entity.getCategory());
            myHolder.tvName.setText(entity.getEtype());
            myHolder.tvNum.setText(entity.getEnums());
            myHolder.tvTele.setText(entity.getUseownertel());
            switch (entity.getCategory()) {
                case "电梯":
                    myHolder.ivIcon.setBackground(ContextCompat.getDrawable(context, R.mipmap.equip_dt));
                    break;
                case "锅炉":
                    myHolder.ivIcon.setBackground(ContextCompat.getDrawable(context, R.mipmap.equip_gl));
                    break;
                case "客运索道":
                    myHolder.ivIcon.setBackground(ContextCompat.getDrawable(context, R.mipmap.equip_kysd));
                    break;
                case "厂内车辆":
                    myHolder.ivIcon.setBackground(ContextCompat.getDrawable(context, R.mipmap.equip_cncl));
                    break;
                case "压力容器":
                    myHolder.ivIcon.setBackground(ContextCompat.getDrawable(context, R.mipmap.equip_ylrq));
                    break;
                case "起重机械":
                    myHolder.ivIcon.setBackground(ContextCompat.getDrawable(context, R.mipmap.equip_qzjx));
                    break;
                case "压力管道":
                    myHolder.ivIcon.setBackground(ContextCompat.getDrawable(context, R.mipmap.equip_ylgd));
                    break;
                case "游乐设施":
                    myHolder.ivIcon.setBackground(ContextCompat.getDrawable(context, R.mipmap.equip_ylss));
                    break;
                default:
                    break;
            }
            myHolder.llLocation.setOnClickListener(v -> {
                if (equipmentLocationListener != null) {
                    equipmentLocationListener.OnEquipmentLocation(position, dataList.get(position).getId());
                }
            });
        } else {
            footerViewHolder = (FooterViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size() + 1;
    }

    public void setEquipmentLocationListener(OnEquipmentLocationListener equipmentLocationListener) {
        this.equipmentLocationListener = equipmentLocationListener;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private ImageView ivIcon;
        private TextView tvType, tvName, tvNum, tvTele;
        private LinearLayout llLocation;

        public MyHolder(View itemView) {
            super(itemView);
            llLocation = itemView.findViewById(R.id.ll_equipment_location);
            ivIcon = itemView.findViewById(R.id.iv_equipment);
            tvType = itemView.findViewById(R.id.tv_equipment_type);
            tvName = itemView.findViewById(R.id.tv_equipment_name);
            tvNum = itemView.findViewById(R.id.tv_equipment_num);
            tvTele = itemView.findViewById(R.id.tv_equipment_tele);
            itemView.setOnClickListener(v -> {
                if (mListener != null) mListener.onItemClick(v, MyHolder.this.getAdapterPosition());
            });
        }
    }
}
