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
import com.zx.tjmarketmobile.entity.ComplainInfoEntity;
import com.zx.tjmarketmobile.util.DateUtil;

import java.util.List;

/**
 * Created by Xiangb on 2017/3/21
 * 功能：投诉举报列表适配器
 */
public class ComplainListAdapter extends MyRecycleAdapter {

    private List<ComplainInfoEntity> mItemList;
    private Context mContext;
    public Holder myHolder;
    private boolean showOverdue = false;

    public ComplainListAdapter(Context mContext, List<ComplainInfoEntity> itemList, boolean showOverdue) {
        mItemList = itemList;
        this.mContext = mContext;
        this.showOverdue = showOverdue;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complain_list, parent, false);
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
            ComplainInfoEntity mEntity = mItemList.get(position);
            myHolder.tvTaskType.setText(mEntity.getFType());
            myHolder.tvTaskTime.setText(DateUtil.getDateFromMillis(mEntity.getFRegTime()));
            myHolder.tvTaskName.setText(mEntity.getFEntityName());
            myHolder.tvRegcompany.setText(mEntity.getFName());
            if (mEntity.getFStatus() != 0) {
                myHolder.tvTitleText.setText("流程状态:");
                switch (mEntity.getFStatus()) {
                    case 0:
                        myHolder.tvTaskUnit.setText("信息录入");
                        break;
                    case 10:
                        myHolder.tvTaskUnit.setText("待受理");
                        break;
                    case 20:
                        myHolder.tvTaskUnit.setText("待分流");
                        break;
                    case 30:
                        myHolder.tvTaskUnit.setText("待指派");
                        break;
                    case 50:
                        myHolder.tvTaskUnit.setText("待处置");
                        break;
                    case 60:
                        myHolder.tvTaskUnit.setText("待审核");
                        break;
                    case 80:
                        myHolder.tvTaskUnit.setText("待办结");
                        break;
                    case 90:
                        myHolder.tvTaskUnit.setText("已办结");
                        break;
                    default:
                        break;
                }
            } else {
                myHolder.tvTitleText.setText("登记单位:");
//                myHolder.tvTaskUnit.setText(mEntity.getfRegUnit());
                myHolder.tvTaskUnit.setText("");
            }
            if ("投诉".equals(mEntity.getFType())) {
                myHolder.imgTimeType.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.comp_ts));
            } else if ("举报".equals(mEntity.getFType())) {
                myHolder.imgTimeType.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.comp_jb));
            } else if ("咨询".equals(mEntity.getFType())) {
                myHolder.imgTimeType.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.comp_zx));
            } else {
                myHolder.imgTimeType.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.comp_more));
            }
//            if (showOverdue && mEntity.isfYqzt()) {//判断是否逾期
//                myHolder.ivOverdue.setVisibility(View.VISIBLE);
//            } else {
//                myHolder.ivOverdue.setVisibility(View.GONE);
//            }
            myHolder.ivOverdue.setVisibility(View.GONE);
        } else {
            footerViewHolder = (FooterViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTaskType, tvTaskName, tvRegcompany, tvTaskUnit, tvTaskTime, tvTitleText;
        private ImageView imgTimeType, ivOverdue;

        public Holder(View parent) {
            super(parent);
            tvTitleText = (TextView) parent.findViewById(R.id.tv_comp_titletext);
            imgTimeType = (ImageView) parent.findViewById(R.id.iv_comp_type);
            tvTaskType = (TextView) parent.findViewById(R.id.tv_comp_type);
            tvTaskName = (TextView) parent.findViewById(R.id.tv_comp_name);
            tvRegcompany = (TextView) parent.findViewById(R.id.tv_comp_company);
            tvTaskUnit = (TextView) parent.findViewById(R.id.tv_comp_regunit);
            tvTaskTime = (TextView) parent.findViewById(R.id.tv_comp_time);
            ivOverdue = (ImageView) parent.findViewById(R.id.iv_comp_overdue);//逾期图片
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
