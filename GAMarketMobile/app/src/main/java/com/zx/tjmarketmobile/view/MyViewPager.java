package com.zx.tjmarketmobile.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Xiangb on 2017/3/14.
 * 功能：
 */
public class MyViewPager extends ViewPager {
    //父ViewPager的引用
    private ViewPager viewPager;
    private boolean flag = true;
    private float mLastMotionX;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        //下面这句话的作用 告诉父view，我的单击事件我自行处理，不要阻碍我。
//        getParent().requestDisallowInterceptTouchEvent(true);
//        return super.dispatchTouchEvent(ev);
//    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    //处理前必须调用此方法初始化冲突ViewPager
    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 使父控件不处理任何触摸事件
                viewPager.requestDisallowInterceptTouchEvent(true);
                flag = true;
                mLastMotionX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                if (flag) {
                    if (x - mLastMotionX > 5 && getCurrentItem() == 0) {
                        flag = false;
                        viewPager.requestDisallowInterceptTouchEvent(false); //将事件交由父控件处理
                    }

                    if (x - mLastMotionX < -5 && getCurrentItem() == getAdapter().getCount() - 1) {
                        flag = false;
                        viewPager.requestDisallowInterceptTouchEvent(false);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                viewPager.requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_CANCEL:
                viewPager.requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
