package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.ComplainInfoEntity;
import com.zx.tjmarketmobile.listener.PAOnClickListener;
import com.zx.tjmarketmobile.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Stanny On 2017/3/29
 * 功能：任务定位适配器
 */
public class CompPoiPagerAdapter extends PagerAdapter {
    private PAOnClickListener listener;

    private boolean mIsShowChangepos = true;
    private Context mContext;
    private List<ComplainInfoEntity> mPoiCases;
    private List<View> mViews = new ArrayList<>();

    public CompPoiPagerAdapter(Context context, List<ComplainInfoEntity> infos, boolean isShowChangepos) {
        mContext = context;
        mPoiCases = infos;
//        mIsShowChangepos = isShowChangepos;
        for (int i = 0; i < mPoiCases.size(); i++) {
            View view = ViewGroup.inflate(mContext, R.layout.item_complain_list, null);
            final ComplainInfoEntity entity = mPoiCases.get(i);
            ImageView imgTimeType = (ImageView) view.findViewById(R.id.iv_comp_type);
            TextView tvTaskType = (TextView) view.findViewById(R.id.tv_comp_type);
            TextView tvTaskName = (TextView) view.findViewById(R.id.tv_comp_name);
            TextView tvRegcompany = (TextView) view.findViewById(R.id.tv_comp_company);
            TextView tvTaskUnit = (TextView) view.findViewById(R.id.tv_comp_regunit);
            TextView tvTaskTime = (TextView) view.findViewById(R.id.tv_comp_time);
            TextView tvTitleText = (TextView) view.findViewById(R.id.tv_comp_titletext);
            ImageView ivOverdue = (ImageView) view.findViewById(R.id.iv_comp_overdue);//逾期图片

            tvTaskType.setText(entity.getFType());
//            int timeType = mEntity.getTimeType();
//            setDrawable(timeType, "complain", myHolder.imgTimeType);
            tvTaskTime.setText(DateUtil.getDateFromMillis(entity.getFRegTime()));
            tvTaskName.setText(entity.getFEntityName());
            tvRegcompany.setText(entity.getFName());
//            if (entity.getFStatus().length() != 0) {
//                tvTitleText.setText("流程状态:");
//                tvTaskUnit.setText(entity.getfStatus());
//            } else {
//                tvTitleText.setText("登记单位:");
//                tvTaskUnit.setText(entity.getfRegUnit());
//            }
//            if ("投诉".equals(entity.getfType())) {
//                imgTimeType.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.comp_ts));
//            } else if ("举报".equals(entity.getfType())) {
//                imgTimeType.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.comp_jb));
//            } else if ("咨询".equals(entity.getfType())) {
//                imgTimeType.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.comp_zx));
//            } else {
//                imgTimeType.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.comp_more));
//            }

            LinearLayout llmain = (LinearLayout) view.findViewById(R.id.ll_search_comp_result_list_view);
            llmain.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (listener != null)
                        listener.onClick(R.id.ll_search_comp_result_list_view);
                }
            });
            // 导航
//            if (mIsShowChangepos) {
            ImageButton ibtnChangepos = (ImageButton) view.findViewById(R.id.ibtn_task_changepos);
            ibtnChangepos.setVisibility(View.VISIBLE);
            ibtnChangepos.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (listener != null)
                        listener.onClick(R.id.ibtn_task_changepos);
                }
            });
//            }

            mViews.add(view);
        }
    }

    private void setDrawable(int timeType, String flag, ImageView imgTimeType) {
        Drawable drawable = null;
        try {
            int id = mContext.getResources().getIdentifier(flag + timeType, "mipmap", mContext.getPackageName());
            drawable = ContextCompat.getDrawable(mContext, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        imgTimeType.setBackground(drawable);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return mPoiCases.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position % getCount());
        container.addView(view);

        return view;
    }

    public void setOnClickListener(PAOnClickListener listener) {
        this.listener = listener;
    }

}
