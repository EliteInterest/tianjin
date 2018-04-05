package com.zx.tjmarketmobile.ui.system;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.R.id;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.ui.mainbase.GuideActivity;
import com.zx.tjmarketmobile.ui.mainbase.HomeActivity;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.MyApplication;
import com.zx.tjmarketmobile.util.Util;
/**
 *
 * Create By Xiangb On 2016/9/19
 * 功能：设置IP界面
 *
 */
public class SettingsIPActivity extends BaseActivity implements OnClickListener {
	private ImageButton mImgBtnGoback;
	private EditText mEtIp1, mEtIp2, mEtIp3, mEtIp4, mEtIpPort;
	private Button mBtnOk;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_ip);


		addToolBar(true);
		setMidText("设置-服务器");
		hideRightImg();
		mEtIp1 = (EditText) findViewById(id.et_settings_ip_1);
		mEtIp2 = (EditText) findViewById(id.et_settings_ip_2);
		mEtIp3 = (EditText) findViewById(id.et_settings_ip_3);
		mEtIp4 = (EditText) findViewById(id.et_settings_ip_4);
		mEtIpPort = (EditText) findViewById(id.et_settings_ip_port);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

		mEtIp1.setText(sp.getString("ip1", ConstStrings.ip1));
		mEtIp2.setText(sp.getString("ip2", ConstStrings.ip2));
		mEtIp3.setText(sp.getString("ip3", ConstStrings.ip3));
		mEtIp4.setText(sp.getString("ip4", ConstStrings.ip4));
		mEtIpPort.setText(sp.getString("ipport", ConstStrings.ipport));

		
		mBtnOk = (Button) findViewById(id.btn_settings_ip_ok);
		mBtnOk.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case id.btn_settings_ip_ok:
		{
			String ip1 = mEtIp1.getText().toString().trim();
			String ip2 = mEtIp2.getText().toString().trim();
			String ip3 = mEtIp3.getText().toString().trim();
			String ip4 = mEtIp4.getText().toString().trim();
			String ipport = mEtIpPort.getText().toString().trim();
			if (Util.isIpValid(this, ip1, ip2, ip3, ip4)) {
				String ip = ip1 + "." + ip2 + "." + ip3 + "." + ip4 + ":" + ipport;
				ApiData.setBaseUrl(ip);
				Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
				editor.putString("ip1", ip1);
				editor.putString("ip2", ip2);
				editor.putString("ip3", ip3);
				editor.putString("ip4", ip4);
				editor.putString("ipport", ipport);
				editor.commit();
				Util.showToast(this, "设置成功");

				userManager.setNoLogin(this);
//				Intent intent = new Intent(SettingsIPActivity.this, LoginActivity.class);
//				startActivity(intent);
//				Util.activity_Out(this);
//				MyApplication.getInstance().remove(SettingsActivity.class);
//				MyApplication.getInstance().remove(HomeActivity.class);
				editor.putString("curuser", "");
				editor.commit();
				MyApplication.getInstance().remove(HomeActivity.class);
				MyApplication.getInstance().remove(GuideActivity.class);
				MyApplication.getInstance().remove(SettingsActivity.class);
				finish();
			}
		}
			break;
		default:
			break;
		}
	}

	
}
