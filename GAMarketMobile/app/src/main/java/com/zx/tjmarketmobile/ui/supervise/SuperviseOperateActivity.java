package com.zx.tjmarketmobile.ui.supervise;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.R.id;
import com.zx.tjmarketmobile.entity.SuperviseDetailInfo;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.ui.base.BaseActivity;

/**
 * Create By Xiangb On 2016/9/22
 * 功能：监管处置界面
 */

public class SuperviseOperateActivity extends BaseActivity implements OnClickListener {

    private ViewPager mVpContent;
    private TabLayout mTabLayout;
    private String mGuid;
    private SuperviseDetailInfo mSuperviseDetailInfo;
    private ApiData taskData = new ApiData(ApiData.HTTP_ID_superviseoperate_detail);
    private ApiData disposeData = new ApiData(ApiData.HTTP_ID_superviseoperate_dispose);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superviseoparate);

        addToolBar(true);
        hideRightImg();
        setMidText("监管详情");


        taskData.setLoadingListener(this);
        disposeData.setLoadingListener(this);
        mGuid = getIntent().getStringExtra("guid");
        mTabLayout = (TabLayout) findViewById(id.tb_normal_layout);
        mVpContent = (ViewPager) findViewById(id.vp_normal_pager);
        findViewById(id.btnActSuperviseOparate_untread).setOnClickListener(this);
        findViewById(id.btnActSuperviseOparate_transfer).setOnClickListener(this);
        taskData.loadData(mGuid);
    }

    @Override
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        if (b.isSuccess()) {
            switch (id) {
                case ApiData.HTTP_ID_superviseoperate_detail:
                    mSuperviseDetailInfo = (SuperviseDetailInfo) b.getEntry();
                    if (myPagerAdapter.getCount() > 0) {
                        SuperviseDetailFragment sdFragment = (SuperviseDetailFragment) myPagerAdapter.getItem(0);
                        sdFragment.update(mSuperviseDetailInfo);
                    } else {
                        myPagerAdapter.addFragment(SuperviseDetailFragment.newInstance(0, mSuperviseDetailInfo),"任务信息");
                        myPagerAdapter.addFragment(SuperviseOperateFragment.newInstance(0, mSuperviseDetailInfo),"检查结果");
                        mVpContent.setOffscreenPageLimit(2);
                        mVpContent.setAdapter(myPagerAdapter);
                        mTabLayout.setupWithViewPager(mVpContent);
                    }
                    myPagerAdapter.notifyDataSetChanged();
                    break;
                case ApiData.HTTP_ID_superviseoperate_dispose:
                    showToast("退回成功");
                    taskData.loadData(mGuid);
                    break;
                default:
                    break;
            }
        } else {
            showToast("系统异常，请稍后再试");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.btnActSuperviseOparate_untread:
                if ("待初审".equals(mSuperviseDetailInfo.getTaskProgress())) {
                    disposeData.loadData(userInfo.getId(), mGuid, "rollback");
                } else {
                    showToast("待初审的任务才能退回！");
                }
                break;
            case id.btnActSuperviseOparate_transfer:
                if ("待初审".equals(mSuperviseDetailInfo.getTaskProgress())) {
                    disposeData.loadData(userInfo.getId(), mGuid, "handover");
                } else {
                    showToast("待初审的任务才能移交支队！");
                }
                break;

            default:
                break;
        }
    }
}
