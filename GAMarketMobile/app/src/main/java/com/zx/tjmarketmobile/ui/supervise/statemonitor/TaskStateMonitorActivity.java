package com.zx.tjmarketmobile.ui.supervise.statemonitor;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.supervise.MonitorPrecessCountEntity;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.ConstStrings;

import static com.zx.tjmarketmobile.util.ConstStrings.SUPERVISESTATES;

/**
 * Created by zhouzq on 2017/4/7.
 */

public class TaskStateMonitorActivity extends BaseActivity implements View.OnClickListener {


    private MonitorPrecessCountEntity mTaskInfo;
    private int mTableFlag = 0;
    private TabLayout mTabLayout;
    private ViewPager mVpContent;
    private Spinner spinner;
    private String currentState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_monitor_supervise);
        addToolBar(true);
        hideRightImg();
        mTabLayout = (TabLayout) findViewById(R.id.tb_supervise_state_monitor);
        mVpContent = (ViewPager) findViewById(R.id.vp_supervise_state_monitor_content);
        spinner = (Spinner)findViewById(R.id.sp_state_select);
        mTableFlag = getIntent().getIntExtra("type", 0);
        mTaskInfo = (MonitorPrecessCountEntity) getIntent().getSerializableExtra("task");
        setMidText("监管流程");
        initialViewPager();
        if (null != mTaskInfo) {
            mTabLayout.getTabAt(0).setText("全部(" + mTaskInfo.getTotal() + ")");
            mTabLayout.getTabAt(1).setText("即将到期(" + mTaskInfo.getWillOver() + ")");
            mTabLayout.getTabAt(2).setText("逾期(" + mTaskInfo.getOver() + ")");
        }

        ArrayAdapter<String> selectAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item_layout, SUPERVISESTATES);
        spinner.setAdapter(selectAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < myPagerAdapter.getCount(); i++) {
                    TaskStateMonitorFragment taskStateMonitorFragment = (TaskStateMonitorFragment)myPagerAdapter.getItem(i);
                    currentState = ConstStrings.SUPERVISESTATES[position];
                    taskStateMonitorFragment.loadData(currentState);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initialViewPager() {
        myPagerAdapter.addFragment(TaskStateMonitorFragment.newInstance(TaskStateMonitorActivity.this,0), "全部");
        myPagerAdapter.addFragment(TaskStateMonitorFragment.newInstance(TaskStateMonitorActivity.this,1), "即将到期");
        myPagerAdapter.addFragment(TaskStateMonitorFragment.newInstance(TaskStateMonitorActivity.this,2), "逾期");
        mVpContent.setOffscreenPageLimit(3);
        mVpContent.setAdapter(myPagerAdapter);
        mTabLayout.setupWithViewPager(mVpContent);
        mVpContent.setCurrentItem(mTableFlag);
    }

    public void setText(int flag, int num) {
        switch (flag) {
            case 0:
                mTabLayout.getTabAt(0).setText("全部(" + num + ")");
                break;
            case 1:
                mTabLayout.getTabAt(1).setText("即将到期(" + num + ")");
                break;
            case 2:
                if (mTabLayout.getTabCount() > 2) {
                    mTabLayout.getTabAt(2).setText("逾期(" + num + ")");
                }
                break;
            default:
                break;
        }
    }

    public void setText(int flag) {
        switch (flag) {
            case 0:
                mTabLayout.getTabAt(0).setText("全部");
                break;
            case 1:
                mTabLayout.getTabAt(1).setText("即将到期");
                break;
            case 2:
                if (mTabLayout.getTabCount() > 2) {
                    mTabLayout.getTabAt(2).setText("逾期");
                }
                break;
            default:
                break;
        }
    }



}
