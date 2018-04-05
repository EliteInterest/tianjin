package com.zx.tjmarketmobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.HttpZtEntity;
import com.zx.tjmarketmobile.listener.PAOnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Stanny On 2016/9/22
 * 功能：主体位置适配器
 */
public class ZtPoiPagerAdapter extends PagerAdapter {

    private PAOnClickListener listener;

    private boolean mIsShowChangepos = true;
    private Context mContext;
    public List<HttpZtEntity> mPoiZts;
    private List<View> mViews = new ArrayList<View>();

    public ZtPoiPagerAdapter(Context context, List<HttpZtEntity> infos, boolean isShowChangepos) {
        mContext = context;
        mPoiZts = infos;
        mIsShowChangepos = isShowChangepos;
        for (int i = 0; i < mPoiZts.size(); i++) {
            View view = ViewGroup.inflate(mContext, R.layout.item_search_result_list, null);
            final HttpZtEntity zt = mPoiZts.get(i);
            //信用等级
            ImageView imgCreditLevel = (ImageView) view.findViewById(R.id.iv_creditlevel);
            String creditLevel = zt.getCreditCode();
            //默认为A级
            if (creditLevel == null) {
                imgCreditLevel.setBackgroundResource(R.mipmap.a);
            } else if (creditLevel.equalsIgnoreCase("B")) {
                imgCreditLevel.setBackgroundResource(R.mipmap.b);
            } else if (creditLevel.equalsIgnoreCase("C")) {
                imgCreditLevel.setBackgroundResource(R.mipmap.c);
            } else if (creditLevel.equalsIgnoreCase("D")) {
                imgCreditLevel.setBackgroundResource(R.mipmap.d);
            } else if (creditLevel.equalsIgnoreCase("Z")) {
                imgCreditLevel.setBackgroundResource(R.mipmap.z);
            } else {
                imgCreditLevel.setBackgroundResource(R.mipmap.a);
            }
            //主体名称
            TextView tvZtName = (TextView) view.findViewById(R.id.tv_zt_name);
            tvZtName.setLines(1);
            tvZtName.setEllipsize(TruncateAt.END);
            tvZtName.setText(zt.getEnterpriseName());
            //地址
            TextView tvZtAddress = (TextView) view.findViewById(R.id.tv_zt_address);
            tvZtAddress.setText(zt.getAddress());
//            tvZtAddress.setTextColor(ContextCompat.getColor(mContext,R.color.colorAccent));
//            tvZtAddress.setOnClickListener(new OnClickListener() {//导航
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        listener.onClick(0);
//                    }
//                }
//            });
            //电话
            TextView tvZtTel = (TextView) view.findViewById(R.id.tv_zt_tel);
            final String phonenum = zt.getContactInfo();
            tvZtTel.setText(phonenum);
            tvZtTel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (phonenum != null && !phonenum.isEmpty()) {
                        Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                                Uri.parse("tel:" + phonenum));
                        mContext.startActivity(phoneIntent);
                    }
                }
            });

            LinearLayout llmain = (LinearLayout) view.findViewById(R.id.ll_search_result_list_view);
            llmain.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (listener != null)
                        listener.onClick(R.id.ll_search_result_list_view);
                }
            });

//            //主体位置纠正
//            if (mIsShowChangepos) {
                ImageButton ibtnChangepos = (ImageButton) view.findViewById(R.id.ibtn_zt_changepos);
                ibtnChangepos.setVisibility(View.VISIBLE);
                ibtnChangepos.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (listener != null)
                            listener.onClick(R.id.ibtn_zt_changepos);
                    }
                });
//            }


            mViews.add(view);
        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return mPoiZts.size();
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
