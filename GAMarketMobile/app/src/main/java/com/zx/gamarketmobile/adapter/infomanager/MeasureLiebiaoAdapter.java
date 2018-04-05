package com.zx.gamarketmobile.adapter.infomanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.adapter.MyRecycleAdapter;
import com.zx.gamarketmobile.entity.infomanager.InfoManagerMeasureCustom;
import com.zx.gamarketmobile.entity.infomanager.InfoManagerMeasureLiebiao;

import java.util.List;

/**
 * Create By Stanny On 2017/3/13
 * 功能：案件详情适配器
 */
public class MeasureLiebiaoAdapter extends MyRecycleAdapter {
    private static final String TAG = "InfoManagerStandardAdapter";
    private List<InfoManagerMeasureLiebiao.RowsBean> mItemList = null;
    private Context mContext;
    private boolean showOverdue = false;
    public MeasureLiebiaoAdapter.Holder myHolder;

    public MeasureLiebiaoAdapter(Context mContext, List<InfoManagerMeasureLiebiao.RowsBean> itemList, boolean showOverdue) {
        mItemList = itemList;
        this.mContext = mContext;
        this.showOverdue = showOverdue;
    }

    //创建页面并绑定holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervise_mytask_list_item, parent, false);
            return new MeasureLiebiaoAdapter.Holder(view);
        } else {//footer
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_foot_view, parent, false);
            return new FooterViewHolder(view);
        }
    }

    //从holder中获取控件并设置
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MeasureLiebiaoAdapter.Holder) {
            myHolder = (MeasureLiebiaoAdapter.Holder) holder;
            InfoManagerMeasureLiebiao.RowsBean entity = mItemList.get(position);
            Log.i(TAG, "getEnterpriseName is " + entity.getAccuracy());
            Log.i(TAG, "getAgencyCode is " + entity.getDepartment_id());
            Log.i(TAG, "getAdministrativeDivisions is " + entity.getEnterpriseName());


            myHolder.tvField.setText(entity.getAccuracy());
            myHolder.tvDate.setText(entity.getDepartment_id());
            myHolder.tvName.setText(entity.getEnterpriseName());
            myHolder.tvPerson.setText(entity.getFactory());
            myHolder.tvStage.setText(entity.getId());
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
            ivField = (ImageView) parent.findViewById(R.id.iv_supervise_field);
            ivOverdue = (ImageView) parent.findViewById(R.id.iv_supervise_overdue);
            tvField = (TextView) parent.findViewById(R.id.tv_supervise_field);
            tvDate = (TextView) parent.findViewById(R.id.tv_supervise_date);
            tvName = (TextView) parent.findViewById(R.id.tv_supervise_name);
            tvPerson = (TextView) parent.findViewById(R.id.tv_supervise_person);
            tvStage = (TextView) parent.findViewById(R.id.tv_supervise_stage);
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