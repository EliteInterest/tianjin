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
import com.zx.tjmarketmobile.entity.CaseInfoEntity;
import com.zx.tjmarketmobile.listener.PAOnClickListener;
import com.zx.tjmarketmobile.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Stanny On 2016/9/22
 * 功能：任务定位适配器
 */
public class CasePoiPagerAdapter extends PagerAdapter {
    private PAOnClickListener listener;

    private boolean mIsShowChangepos = true;
    private Context mContext;
    private List<CaseInfoEntity> mPoiCases;
    private List<View> mViews = new ArrayList<>();

    public CasePoiPagerAdapter(Context context, List<CaseInfoEntity> infos, boolean isShowChangepos) {
        mContext = context;
        mPoiCases = infos;
//        mIsShowChangepos = isShowChangepos;
        for (int i = 0; i < mPoiCases.size(); i++) {
            View view = ViewGroup.inflate(mContext, R.layout.item_case_todo, null);
            final CaseInfoEntity entity = mPoiCases.get(i);
            ImageView ivField = (ImageView) view.findViewById(R.id.iv_case_field);//案件领域图片
            TextView tvField = (TextView) view.findViewById(R.id.tv_case_field);//案件领域文字
            TextView tvDate = (TextView) view.findViewById(R.id.tv_case_date);//案件日期
            TextView tvName = (TextView) view.findViewById(R.id.tv_case_name);//案件名
            TextView tvPerson = (TextView) view.findViewById(R.id.tv_case_person);//案件当事人
            TextView tvStage = (TextView) view.findViewById(R.id.tv_case_stage);//案件环节

            tvField.setText(entity.getTypeName());
            tvDate.setText(DateUtil.getDateFromMillis(entity.getRegDate()));
            tvName.setText(entity.getCaseName());
            tvPerson.setText(entity.getEnterpriseName());
            tvStage.setText(entity.getStatusName());

            switch (entity.getTypeCode()) {
                case "01":
                    ivField.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.case_jdjc));
                    break;
                case "02":
                    ivField.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.case_jdcy));
                    break;
                case "03":
                    ivField.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.case_tsjb));
                    break;
                case "04":
                    ivField.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.case_qtjgys));
                    break;
                case "05":
                    ivField.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.case_sjjgjb));
                    break;
                case "06":
                    ivField.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.comp_more));
                    break;

                default:
                    break;
            }

            LinearLayout llmain = (LinearLayout) view.findViewById(R.id.ll_search_task_result_list_view);
            llmain.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (listener != null)
                        listener.onClick(R.id.ll_search_task_result_list_view);
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
