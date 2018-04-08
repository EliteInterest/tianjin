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
import com.zx.tjmarketmobile.listener.ICommonListener.IOnSingleSelectListener;

import java.util.List;

/**
 *
 * Create By Stanny On 2016/9/21
 * 功能：地址选择列表
 *
 */
public class AddressSelectPopuView {

	private Context mContext;
	private PopupWindow mWindowSilice;
	private LinearLayout mLlPic;
	private TextView mTvName;
	private IOnSingleSelectListener mDataSelectListener;
	private OnDismissListener mDismissListener;

	public void setDataSelectListener(IOnSingleSelectListener dataSelectListener) {
		this.mDataSelectListener = dataSelectListener;
	}

	public AddressSelectPopuView(Context context) {
		this.mContext = context;
	}

	public boolean show(TextView showLocationView, List<String> list) {
		initWindow(showLocationView, list.size());
		addView(list);
		mTvName = showLocationView;
		int[] location = new int[2];
		showLocationView.getLocationInWindow(location);
		if (!list.isEmpty() && list.size() > 1) {
			mWindowSilice.showAtLocation(showLocationView, Gravity.NO_GRAVITY, location[0], location[1]
					+ showLocationView.getHeight());
			mWindowSilice.update();
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	private void initWindow(TextView showLocationView, int size) {
		int height = (int) mContext.getResources().getDimension(R.dimen.select_window_item_heitht);
		int popupHeight = height * size;
		if (popupHeight > 1280) {
			popupHeight = 1280;
		}
		if (mWindowSilice == null) {
			LayoutInflater mLayoutInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View popView = (View) mLayoutInflater.inflate(R.layout.popu_single_select, null, false);
			mLlPic = (LinearLayout) popView.findViewById(R.id.llPopuSelect_content);
			mWindowSilice = new PopupWindow(popView, showLocationView.getWidth(), popupHeight, true);
			mWindowSilice.setBackgroundDrawable(new BitmapDrawable());
			mWindowSilice.setOutsideTouchable(true);
			if (null != mDismissListener) {
				mWindowSilice.setOnDismissListener(mDismissListener);
			}
		} else {
			mWindowSilice.setHeight(popupHeight);
		}
	}

	private void addView(List<String> list) {
		mLlPic.removeAllViews();
		for (final String data : list) {
			if (!TextUtils.isEmpty(data)) {
				View view = View.inflate(mContext, R.layout.item_text, null);
				TextView tvName = (TextView) view.findViewById(R.id.tvItemText_name);
				tvName.setText(data);
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String tag = null;
						if (mTvName.getTag() != null) {
							tag = mTvName.getTag().toString();
						}
						mTvName.setText(data);
						if (null != mDataSelectListener) {
							mDataSelectListener.onSelect(tag, data);
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
