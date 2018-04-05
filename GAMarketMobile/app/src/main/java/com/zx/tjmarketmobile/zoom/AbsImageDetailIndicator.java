package com.zx.tjmarketmobile.zoom;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * 继承{@link OnPageChangeListener},用于监听滑动事件,更好的处理{@link GestureImageView}
 * 里面图片左右锁定.
 * 
 * @author Clude <byedown@gmail.com>
 * 
 */
public interface AbsImageDetailIndicator extends OnPageChangeListener {

	public void setViewPager(ViewPager pager);

	public void setOnPageChangeListener(OnPageChangeListener listener);
}
