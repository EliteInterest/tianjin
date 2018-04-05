package com.zx.tjmarketmobile.ui.system;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.R.id;
import com.zx.tjmarketmobile.R.layout;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.ui.mainbase.LoginActivity;
import com.zx.tjmarketmobile.adapter.HelpAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * Create By Xiangb On 2016/9/19
 * 功能：欢迎界面
 *
 */
public class HelpActivity extends BaseActivity {

	private HelpAdapter mImageAdapter;
	private ViewPager mVpImage;
	private Button mBtnSkip;
	private int mPagerPosition;
	private Timer mAutoTimer;
	private MTimerTask mTimerTask;
	private final int DELAYTIME = 5 * 1000;
	private Handler mHandler;
	private boolean mIsToLogin = true;
	
	private LinearLayout mLLDots;
	private ArrayList<ImageView> imgviewList = new ArrayList<ImageView>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_help);
		addToolBar(false);

		mHandler = new Handler();
		mAutoTimer = new Timer();
		mVpImage = (ViewPager) findViewById(id.mygallery);
		mBtnSkip = (Button) findViewById(R.id.btnActHelp_skip);
		mIsToLogin = getIntent().getBooleanExtra("isToLogin", true);
		mLLDots = (LinearLayout) findViewById(id.lldots);
		initialViewPager();
		mBtnSkip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				skipHelp();
			}
		});
	}

	@Deprecated
	private void initialViewPager() {
		mImageAdapter = new HelpAdapter(this);
		mVpImage.setAdapter(mImageAdapter);
		purgeTimer();
		mVpImage.setOnPageChangeListener(pageChangeListener);
		//设置图片滑动示意（选中与未选中圆点）
		if (mImageAdapter.getCount() > 1) {
			for (int i = 0; i < mImageAdapter.getCount(); i++) {
				ImageView img = new ImageView(this);
				if (i == 0) {
					img.setBackgroundResource(R.mipmap.dotsel);
				}
				else {
					img.setBackgroundResource(R.mipmap.dot);
				}
				LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				lp.setMargins(0, 0, 8, 0);
				img.setLayoutParams(lp);
				mLLDots.addView(img);
				imgviewList.add(img);
			}
		}
	}

	private void purgeTimer() {
		if (mTimerTask != null) {
			mTimerTask.cancel();
		}
		mTimerTask = new MTimerTask();
		mAutoTimer.schedule(mTimerTask, DELAYTIME);
	}

	class MTimerTask extends TimerTask {
		@Override
		public void run() {
			mPagerPosition++;
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					if (mPagerPosition < mImageAdapter.getCount()) {
						mVpImage.setCurrentItem(mPagerPosition, true);
					} else if (mTimerTask != null) {
						mTimerTask.cancel();
					}
				}
			});
		}
	}

	OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			if (mImageAdapter.getCount() > 1) {
				if (position - 1 >= 0) {
					imgviewList.get(position - 1).setBackgroundResource(R.mipmap.dot);
				}
				if (position +1 < mImageAdapter.getCount()) {
					imgviewList.get(position +1).setBackgroundResource(R.mipmap.dot);
				}
				imgviewList.get(position).setBackgroundResource(R.mipmap.dotsel);
			}
			mPagerPosition = position;
			if (position == mImageAdapter.getCount()-1) {
				mBtnSkip.setVisibility(View.GONE);
			} else {
				mBtnSkip.setVisibility(View.VISIBLE);
			}
			purgeTimer();
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};

	public void skipHelp() {
//		saveLoginState();
		if (mIsToLogin) {
			Intent intent = new Intent(HelpActivity.this, LoginActivity.class);
			startActivity(intent);
		}
		finish();
	}

//	private void saveLoginState() {
//		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
//		Editor editor = sp.edit();
//		editor.putBoolean("isFirstLogin", false);
//		editor.commit();
//	}
}
