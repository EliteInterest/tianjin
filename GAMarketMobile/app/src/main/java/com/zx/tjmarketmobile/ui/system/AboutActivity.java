package com.zx.tjmarketmobile.ui.system;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.SYSUtil;

/**
 *
 * Create By Xiangb On 2016/9/19
 * 功能：关于界面
 *
 */

public class AboutActivity extends BaseActivity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		addToolBar(true);
		hideRightImg();
		setMidText("关于我们");

		TextView tvAppName = (TextView) findViewById(R.id.tvActAbout_appname);
		TextView tvVersion = (TextView) findViewById(R.id.tvActAbout_version);
		tvAppName.setText("应用名称: " + SYSUtil.getAppName(this));
		tvVersion.setText("版本号: " + SYSUtil.getVersionName(this));
		findViewById(R.id.tvActAbout_phone).setOnClickListener(this);

		ListView listView = new ListView(this);
		listView.setOnItemClickListener(new myListener());

	}
	class myListener implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvActAbout_phone:
			callSomebody("023-63424876");
			break;
		default:
			break;
		}
	}

	private void callSomebody(String number) {
		Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + number)); 
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent); 
	}
}
