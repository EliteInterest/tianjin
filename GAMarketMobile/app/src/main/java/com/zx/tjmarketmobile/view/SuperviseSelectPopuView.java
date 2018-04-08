package com.zx.tjmarketmobile.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.SelectPopDataList;
import com.zx.tjmarketmobile.listener.ICommonListener.IOnInfoSelectListener;

import java.util.List;

/**
 * Create By Xiangb On 2016/9/22
 * 功能：监管选择控件
 */
@SuppressWarnings("deprecation")
public class SuperviseSelectPopuView {

    private Context mContext;
    private PopupWindow mWindowSilice;
    private int index;
    private LinearLayout mLlPic;
    private IOnInfoSelectListener mDataSelectListener;
    private OnDismissListener mDismissListener;

    public void setDataSelectListener(IOnInfoSelectListener dataSelectListener) {
        this.mDataSelectListener = dataSelectListener;
    }

    public SuperviseSelectPopuView(Context context) {
        this.mContext = context;
    }

    public boolean show(View showLocationView, List<SelectPopDataList> list, int type) {
        return show(showLocationView, 0, list, type);
    }

    public boolean show(View showLocationView, int width, List<SelectPopDataList> list, int index) {
        this.index = index;
        initWindow(showLocationView, width, list.size());
        addView(list);
        int[] location = new int[2];
        showLocationView.getLocationInWindow(location);
        if (!list.isEmpty() && list.size() > 0) {
            mWindowSilice.showAtLocation(showLocationView, Gravity.NO_GRAVITY, location[0], location[1]
                    + showLocationView.getHeight());
            mWindowSilice.update();
            return true;
        } else {
            return false;
        }
    }

    private void initWindow(View showLocationView, int viewWidth, int size) {
        int height = (int) mContext.getResources().getDimension(R.dimen.select_window_item_heitht);
        if (viewWidth == 0) {
            viewWidth = showLocationView.getWidth();
        }
        if (size > 6) {
            size = 6;
        }
        int popupHeight = height * size;
        if (popupHeight > 1280) {
            popupHeight = 1280;
        }
        if (mWindowSilice == null) {
            LayoutInflater mLayoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popView = (View) mLayoutInflater.inflate(R.layout.popu_single_select, null, false);
            mLlPic = (LinearLayout) popView.findViewById(R.id.llPopuSelect_content);
            mWindowSilice = new PopupWindow(popView, viewWidth, popupHeight, true);
            mWindowSilice.setBackgroundDrawable(new BitmapDrawable());
            mWindowSilice.setOutsideTouchable(true);
            if (null != mDismissListener) {
                mWindowSilice.setOnDismissListener(mDismissListener);
            }
        } else {
            mWindowSilice.setHeight(popupHeight);
        }
    }

    private void addView(final List<SelectPopDataList> list) {
        mLlPic.removeAllViews();
        for (final SelectPopDataList data : list) {
            if (!TextUtils.isEmpty(data.fTaskName)) {
                View view = View.inflate(mContext, R.layout.item_text, null);
                TextView tvName = (TextView) view.findViewById(R.id.tvItemText_name);
                tvName.setText(data.fTaskName);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mDataSelectListener) {
                            mDataSelectListener.onSelect(list.indexOf(data), data, index);
                        }
                        dismiss();
                    }
                });
                mLlPic.addView(view);
            }
        }
    }

    public void setOnDismissListener(OnDismissListener listener) {
        this.mDismissListener = listener;
    }

    public void dismiss() {
        if (mWindowSilice != null && mWindowSilice.isShowing()) {
            mWindowSilice.dismiss();
        }
    }

    public boolean isShowing() {
        return mWindowSilice.isShowing();
    }
}
