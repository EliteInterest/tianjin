package com.zx.tjmarketmobile.ui.emergency;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.R.id;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.entity.TaskCountInfo;

/**
 * Create By Xiangb On 2016/9/23
 * 功能：预警信息
 */
public class DeviceEmergencyActivity extends BaseActivity implements OnClickListener {

    private TaskCountInfo mTaskInfo;
    private ViewPager mVpContent;
    public TabLayout mTabLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_tab_viewpager);

        addToolBar(true);
        getRightImg().setOnClickListener(this);
        setMidText("特种设备预警");


        mTabLayout = (TabLayout) findViewById(id.tb_normal_layout);
        mVpContent = (ViewPager) findViewById(id.vp_normal_pager);

        initialViewPager();
    }

    private void initialViewPager() {
        myPagerAdapter.addFragment(DeviceEmergencyListFragment.newInstance(0, "特种设备预警"), "特种设备预警");
        myPagerAdapter.addFragment(DeviceEmergencyListFragment.newInstance(1, "维保合同预警"), "维保合同预警");
        myPagerAdapter.addFragment(DeviceEmergencyListFragment.newInstance(2, "安全附件预警"), "安全附件预警");
        mVpContent.setOffscreenPageLimit(3);
        mVpContent.setAdapter(myPagerAdapter);
        if (myPagerAdapter.getCount() > 3) {
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        mTabLayout.setupWithViewPager(mVpContent);
        mVpContent.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.toolbar_right:
                int index = mVpContent.getCurrentItem();
                DeviceEmergencyListFragment fragment = (DeviceEmergencyListFragment) myPagerAdapter.getItem(index);
                fragment.goToMapActivity();
                break;
            default:
                break;
        }
    }
}
