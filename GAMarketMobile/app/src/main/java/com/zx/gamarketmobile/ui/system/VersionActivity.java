package com.zx.gamarketmobile.ui.system;

import android.os.Bundle;
import android.widget.TextView;

import com.zx.gamarketmobile.R;
import com.zx.gamarketmobile.ui.base.BaseActivity;
import com.zx.gamarketmobile.entity.VersionInfo;

/**
 *
 * Create By Xiangb On 2016/9/19
 * 功能：版本说明界面
 *
 */

public class VersionActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_version);
		addToolBar(true);
		hideRightImg();
		setMidText("版本说明");
		TextView tvCode = (TextView) findViewById(R.id.tvActVersion_code);
		TextView tvRemark = (TextView) findViewById(R.id.tvActVersion_remark);
		VersionInfo info = (VersionInfo) getIntent().getSerializableExtra("version");
		tvCode.setText("版本号：" + info.versionCode);
		tvRemark.setText(info.versionRemark);
	}
	
}
