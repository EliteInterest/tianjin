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
import com.zx.tjmarketmobile.entity.CaseInfoEntity;
import com.zx.tjmarketmobile.util.DateUtil;

import java.util.List;

/**
 * Created by Xiangb on 2017/3/10.
 * 功能：案件执法列表适配器
 */
public class CaseListAdapter extends MyRecycleAdapter {

    private List<CaseInfoEntity> mItemList;
    private Context mContext;
    private boolean showOverdue = false;
    public Holder myHolder;

    public CaseListAdapter(Context mContext, List<CaseInfoEntity> itemList, boolean showOverdue) {
        mItemList = itemList;
        this.mContext = mContext;
        this.showOverdue = showOverdue;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_case_todo, parent, false);
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
            CaseInfoEntity entity = mItemList.get(position);
            myHolder.tvField.setText(entity.getTypeName());
            myHolder.tvDate.setText(DateUtil.getDateFromMillis(entity.getRegDate()));
            myHolder.tvName.setText(entity.getCaseName());
            myHolder.tvPerson.setText(entity.getEnterpriseName());
            myHolder.tvStage.setText(entity.getStatusName());
            if (showOverdue && entity.getIsOverdue() == 0) {//判断是否逾期
                myHolder.ivOverdue.setVisibility(View.VISIBLE);
            } else {
                myHolder.ivOverdue.setVisibility(View.GONE);
            }
            switch (entity.getTypeCode()) {
                case "01":
                    myHolder.ivField.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.case_jdjc));
                    break;
                case "02":
                    myHolder.ivField.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.case_jdcy));
                    break;
                case "03":
                    myHolder.ivField.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.case_tsjb));
                    break;
                case "04":
                    myHolder.ivField.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.case_qtjgys));
                    break;
                case "05":
                    myHolder.ivField.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.case_sjjgjb));
                    break;
                case "06":
                    myHolder.ivField.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.comp_more));
                    break;

                default:
                    break;
            }
        } else {
            footerViewHolder = (FooterViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivField, ivOverdue;
        private TextView tvField, tvDate, tvName, tvPerson, tvStage;

        public Holder(View parent) {
            super(parent);
            ivField = (ImageView) parent.findViewById(R.id.iv_case_field);//案件领域图片
            ivOverdue = (ImageView) parent.findViewById(R.id.iv_case_overdue);//逾期图片
            tvField = (TextView) parent.findViewById(R.id.tv_case_field);//案件领域文字
            tvDate = (TextView) parent.findViewById(R.id.tv_case_date);//案件日期
            tvName = (TextView) parent.findViewById(R.id.tv_case_name);//案件名
            tvPerson = (TextView) parent.findViewById(R.id.tv_case_person);//案件当事人
            tvStage = (TextView) parent.findViewById(R.id.tv_case_stage);//案件环节
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
