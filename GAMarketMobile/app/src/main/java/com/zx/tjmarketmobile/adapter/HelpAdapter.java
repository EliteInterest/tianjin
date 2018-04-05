package com.zx.tjmarketmobile.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.ui.system.HelpActivity;

/**
 * Create By Stanny On 2016/9/19
 * 功能：帮助界面适配器
 */
public class HelpAdapter extends PagerAdapter {

    private View[] views;
    private int[] mPhotoList;
    private HelpActivity mContext;

    public HelpAdapter(HelpActivity c) {
        this.mPhotoList = new int[]{R.mipmap.help1, R.mipmap.help2, R.mipmap.help3,
                R.mipmap.help4, R.mipmap.help5, R.mipmap.help6, R.mipmap.help7, R.mipmap.help8,
                R.mipmap.help9, R.mipmap.help10, R.mipmap.help11, R.mipmap.help12};
        this.mContext = c;
        views = new View[this.mPhotoList.length];
    }

    @Override
    public int getCount() {
        return mPhotoList.length;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0.equals(arg1);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views[position]);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        int resId = mPhotoList[position];
        Drawable originalImage = ContextCompat.getDrawable(mContext, resId);
        ImageView imageView = new ImageView(mContext);
        imageView.setImageDrawable(originalImage);
        imageView.setScaleType(ScaleType.CENTER);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == mPhotoList.length - 1) {
                    mContext.skipHelp();
                }
            }
        });
        container.addView(imageView);
        views[position] = imageView;
        return imageView;
    }

}
