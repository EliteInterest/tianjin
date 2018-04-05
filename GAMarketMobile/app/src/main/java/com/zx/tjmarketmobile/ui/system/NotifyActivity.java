package com.zx.tjmarketmobile.ui.system;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.VersionAdapter;
import com.zx.tjmarketmobile.entity.VersionInfo;
import com.zx.tjmarketmobile.listener.MyItemClickListener;
import com.zx.tjmarketmobile.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class NotifyActivity extends BaseActivity implements MyItemClickListener{

	private VersionAdapter mVersionAdapter;
	private List<VersionInfo> mVersionList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.normal_swipe_recycler_view);
		addToolBar(true);
		hideRightImg();
		setMidText("系统通知");
		RecyclerView notifyRView = (RecyclerView) findViewById(R.id.rv_normal_view);
		((SwipeRefreshLayout)findViewById(R.id.srl_normal_layout)).setEnabled(false);
		mVersionList = getInfoList();
		notifyRView.setLayoutManager(new LinearLayoutManager(this));
		mVersionAdapter = new VersionAdapter(this, mVersionList);
		notifyRView.setAdapter(mVersionAdapter);
		mVersionAdapter.setOnItemClickListener(this);
	}

	private List<VersionInfo> getInfoList() {
		List<VersionInfo> versionList = new ArrayList<VersionInfo>();
		VersionInfo info = createVersion("1.3.0", "09月01日");
		versionList.add(info);
		info = createVersion("1.2.0", "08月12日");
		versionList.add(info);
		info = createVersion("1.1.9", "06月08日");
		versionList.add(info);
		info = createVersion("1.1.8", "05月13日");
		versionList.add(info);
		info = createVersion("1.1.7", "04月08日");
		versionList.add(info);
		info = createVersion("1.1.6", "03月29日");
		versionList.add(info);
		info = createVersion("1.1.5", "12月30日");
		versionList.add(info);
		info = createVersion("1.1.4", "11月24日");
		versionList.add(info);
		info = createVersion("1.1.3", "10月14日");
		versionList.add(info);
		info = createVersion("1.1.2", "10月04日");
		versionList.add(info);
		info = createVersion("1.1.1", "09月27日");
		versionList.add(info);
		info = createVersion("1.1.0", "09月20日");
		versionList.add(info);
		info = createVersion("1.0.9", "09月13日");
		versionList.add(info);
		info = createVersion("1.0.8", "08月16日");
		versionList.add(info);
		info = createVersion("1.0.7", "07月19日");
		versionList.add(info);
		info = createVersion("1.0.6", "07月05日");
		versionList.add(info);
		info = createVersion("1.0.5", "06月21日");
		versionList.add(info);
		info = createVersion("1.0.4", "06月14日");
		versionList.add(info);
		info = createVersion("1.0.3", "06月07日");
		versionList.add(info);
		info = createVersion("1.0.2", "05月24日");
		versionList.add(info);
		info = createVersion("1.0.1", "05月10日");
		versionList.add(info);
		return versionList;
	}

	private VersionInfo createVersion(String code, String date) {
		VersionInfo info = new VersionInfo();
		int id = getResources().getIdentifier("version" + code, "string", getPackageName());
		info.versionCode = code;
		info.versionRemark = getResources().getString(id);
		info.updateDate = date;
		return info;
	}

	@Override
	public void onItemClick(View view, int position) {
		VersionInfo info = mVersionList.get(position);
		Intent intent = new Intent(this, VersionActivity.class);
		intent.putExtra("version", info);
		startActivity(intent);
	}
}
