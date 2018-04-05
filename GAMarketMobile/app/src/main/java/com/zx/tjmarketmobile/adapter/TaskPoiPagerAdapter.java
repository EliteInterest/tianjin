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
import com.zx.tjmarketmobile.entity.HttpTaskEntity;
import com.zx.tjmarketmobile.listener.PAOnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Stanny On 2016/9/22
 * 功能：任务定位适配器
 */
public class TaskPoiPagerAdapter extends PagerAdapter {
    private PAOnClickListener listener;

    private boolean mIsShowChangepos = true;
    private Context mContext;
    private List<HttpTaskEntity> mPoiTasks;
    private List<View> mViews = new ArrayList<View>();

    public TaskPoiPagerAdapter(Context context, List<HttpTaskEntity> infos, boolean isShowChangepos) {
        mContext = context;
        mPoiTasks = infos;
//        mIsShowChangepos = isShowChangepos;
        for (int i = 0; i < mPoiTasks.size(); i++) {
            View view = ViewGroup.inflate(mContext, R.layout.item_search_task_list, null);
            final HttpTaskEntity task = mPoiTasks.get(i);
            // 紧急程度
            ImageView imgTimeType = (ImageView) view.findViewById(R.id.iv_task_timetype);
            int timeType = task.getTimeType();

            // 任务类型
            TextView tvTaskType = (TextView) view.findViewById(R.id.tv_task_tasktype);
            int taskType = task.getTaskType();
            switch (taskType) {
                case 0:
                    tvTaskType.setText(R.string.jgtask);
                    setDrawable(timeType, "time", imgTimeType);
                    break;
                case 1:
                    tvTaskType.setText(R.string.tstask);
                    setDrawable(timeType, "complain", imgTimeType);
                    break;
                case 2:
                    tvTaskType.setText(R.string.yjtask);
                    imgTimeType.setBackgroundResource(R.mipmap.yjrw_task);
                    break;
                default:
                    break;
            }
            // 任务名称
            TextView tvTaskName = (TextView) view.findViewById(R.id.tv_task_name);
            tvTaskName.setText(task.getTaskName());
            // 涉及主体
            TextView tvEntityName = (TextView) view.findViewById(R.id.tv_task_entityname);
            tvEntityName.setText(task.getEntityName());
            // 地址
            TextView tvTaskAddress = (TextView) view.findViewById(R.id.tv_task_address);
            tvTaskAddress.setText(task.getAddress());
//            tvTaskAddress.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
//            tvTaskAddress.setOnClickListener(new OnClickListener() {//导航
//                @Override
//                public void onClick(View v) {
//                    if (listener != null){
//                        listener.onClick(0);
//                    }
//                }
//            });
            // 下放时间
            TextView tvTaskTime = (TextView) view.findViewById(R.id.tv_task_time);
            tvTaskTime.setText(task.getTaskTime());

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
        return mPoiTasks.size();
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
