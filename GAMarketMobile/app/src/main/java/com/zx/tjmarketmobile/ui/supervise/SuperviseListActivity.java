package com.zx.tjmarketmobile.ui.supervise;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.R.id;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.entity.TaskCountInfo;

/**
 * Create By Xiangb On 2016/9/22
 * 功能：监管任务列表
 */
public class SuperviseListActivity extends BaseActivity implements OnClickListener {

    private int mTableFlag = 0;
    private TaskCountInfo mTaskInfo;
    private TabLayout mTabLayout;
    private ViewPager mVpContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchtask);

        addToolBar(true);
        getRightImg().setOnClickListener(this);

        mTaskInfo = (TaskCountInfo) getIntent().getSerializableExtra("task");
        mTableFlag = getIntent().getIntExtra("type", 0);
        mTabLayout = (TabLayout) findViewById(id.TL_complainSearchTask);
        mVpContent = (ViewPager) findViewById(id.VP_complainSearch);
        TextView tvStatus = (TextView) findViewById(id.tvFmSearchTask_status);
        if (null != mTaskInfo) {
            tvStatus.setText(mTaskInfo.status);
            if ("待处置".equals(mTaskInfo.status)) {
                setMidText("待办理");
            } else if ("待提交".equals(mTaskInfo.status)) {
                setMidText("办理中");
            } else {
                setMidText(mTaskInfo.status);
            }
            mTabLayout.getTabAt(0).setText("全部（" + mTaskInfo.allCount + "）");
            mTabLayout.getTabAt(1).setText("即将到期（" + mTaskInfo.soonCount + "）");
            mTabLayout.getTabAt(2).setText("逾期（" + mTaskInfo.expireCount + "）");
        }
        findViewById(id.llFmSearchTask_status).setVisibility(View.GONE);
        findViewById(id.llFmSearchTask_select).setVisibility(View.GONE);
        initialViewPager();
    }

    private void initialViewPager() {
        myPagerAdapter.addFragment(SuperviseListFragment.newInstance(0, mTaskInfo.status), "全部");
        myPagerAdapter.addFragment(SuperviseListFragment.newInstance(1, mTaskInfo.status), "逾期");
        myPagerAdapter.addFragment(SuperviseListFragment.newInstance(2, mTaskInfo.status), "即将逾期");
        mVpContent.setOffscreenPageLimit(3);
        mVpContent.setAdapter(myPagerAdapter);
        mTabLayout.setupWithViewPager(mVpContent);
        mVpContent.setCurrentItem(mTableFlag);

    }

    public void setText(int flag, int num) {
        switch (flag) {
            case 0:
                mTabLayout.getTabAt(0).setText("全部（" + num + "）");
                break;
            case 1:
                mTabLayout.getTabAt(0).setText("即将到期（" + num + "）");
                break;
            case 2:
                mTabLayout.getTabAt(0).setText("逾期（" + num + "）");
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.toolbar_right:
                int index = mVpContent.getCurrentItem();
                SuperviseListFragment fragment = (SuperviseListFragment) myPagerAdapter.getItem(index);
                fragment.goToMapActivity();
                break;
            default:
                break;
        }
    }

}
