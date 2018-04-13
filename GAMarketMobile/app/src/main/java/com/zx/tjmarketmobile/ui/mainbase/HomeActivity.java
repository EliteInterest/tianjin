package com.zx.tjmarketmobile.ui.mainbase;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.R.id;
import com.zx.tjmarketmobile.entity.StatisticsInfo;
import com.zx.tjmarketmobile.entity.StatisticsItemInfo;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.ui.caselegal.CaseMonitorFragment;
import com.zx.tjmarketmobile.ui.caselegal.CaseMyListFragment;
import com.zx.tjmarketmobile.ui.caselegal.CaseSearchFragment;
import com.zx.tjmarketmobile.ui.complain.ComplainMonitorFragment;
import com.zx.tjmarketmobile.ui.complain.ComplainMyListFragment;
import com.zx.tjmarketmobile.ui.complain.ComplainSearchFragment;
import com.zx.tjmarketmobile.ui.map.WorkInMapShowActivity;
import com.zx.tjmarketmobile.ui.statistics.StatisticsFragment;
import com.zx.tjmarketmobile.ui.supervise.mytask.SuperviseMyTaskFragment;
import com.zx.tjmarketmobile.ui.system.HelpActivity;
import com.zx.tjmarketmobile.ui.system.SettingsActivity;
import com.zx.tjmarketmobile.util.ConstStrings;
import com.zx.tjmarketmobile.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Xiangb On 2016/9/19
 * 功能：主界面
 */
public class HomeActivity extends BaseActivity implements OnClickListener {
    private static String TAG = "HomeActivity";
    public ViewPager mVpContent;
    private TabLayout homeTabLayout;
    private TaskNumFragment mComplaintFragment;// 投诉举报
    private TaskNumFragment mSuperviseFragment;// 监管任务
    private int index;
    private StatisticsFragment mStatisticsFragment;// 统计分析
    private SharedPreferences mSharePreferences;
    private TextView mTextViewBottomMessage;/* 消息中心-消息通知 */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addToolBar(true);
        showMidPic();

        getRightImg().setOnClickListener(this);

        mVpContent = (ViewPager) findViewById(R.id.vp_normal_pager);
        homeTabLayout = (TabLayout) findViewById(R.id.tb_normal_layout);
        index = getIntent().getIntExtra("item", 0);
        initialViewPager();
        findViewById(id.tvActHome_setting).setOnClickListener(this);
        findViewById(id.tvActHome_message).setOnClickListener(this);
        findViewById(id.tvActHome_help).setOnClickListener(this);
        mSharePreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mTextViewBottomMessage = (TextView) findViewById(id.tv_bottom_messsage_msg);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (index != 3) {
            loadData();
        }
    }

    public void loadData() {
//        taskData.loadData(userInfo.getId(), userInfo.getAuthority(), userInfo.getDepartment());
    }

    public HomeActivity() {
    }

    public void initialViewPager() {
        switch (index) {
            case 0://监管任务
                setMidText("监管任务");
                String departName = "";
                if (userInfo != null && !TextUtils.isEmpty(userInfo.getDepartmentAlias())) {
                    departName = userInfo.getDepartmentAlias();
                }
                Log.i(TAG, "departName is " + departName);
//                if (!departName.equals("管理员")) {
//                    myPagerAdapter.addFragment(SuperviseMyTaskFragment.newInstance(), "我的任务");
//                }

                myPagerAdapter.addFragment(SuperviseMyTaskFragment.newInstance(), "我的任务");
//                myPagerAdapter.addFragment(TaskNumFragment.newInstance(1), "状态监控");

//                myPagerAdapter.addFragment(SuperviseClaimedFragment.newInstance(0), "坐标纠正");
//                myPagerAdapter.addFragment(SuperviseClaimedFragment.newInstance(1), "主体认领");
                break;
            case 1://投诉举报
                setMidText("投诉举报");
                myPagerAdapter.addFragment(ComplainMyListFragment.newInstance(), "我的任务");
                myPagerAdapter.addFragment(ComplainMonitorFragment.newInstance(), "流程监控");
                myPagerAdapter.addFragment(ComplainSearchFragment.newInstance(), "历史查询");
                break;
            case 2://案件执法
                setMidText("案件执法");
                myPagerAdapter.addFragment(CaseMyListFragment.newInstance(), "我的任务");
                myPagerAdapter.addFragment(CaseMonitorFragment.newInstance(), "案件监控");
                myPagerAdapter.addFragment(CaseSearchFragment.newInstance(), "案件查询");
                homeTabLayout.setVisibility(View.VISIBLE);
                break;
            case 3://信息管理
//                toActivity(InfoManagerActivity.class);
                break;

            case 4://统计分析
                setMidText("统计分析");
                getRightImg().setVisibility(View.GONE);
                StatisticsInfo superviseInfo = createSuperviseData();
                myPagerAdapter.addFragment(StatisticsFragment.newInstance(superviseInfo), "监管任务");
                StatisticsInfo compInfo = createCompData();
                myPagerAdapter.addFragment(StatisticsFragment.newInstance(compInfo), "投诉举报");
                StatisticsInfo caseInfo = createCaseData();
                myPagerAdapter.addFragment(StatisticsFragment.newInstance(caseInfo), "案件执法");
                List<StatisticsInfo> mDataList = createStatisticeData();
                for (int i = 0; i < mDataList.size(); i++) {
                    myPagerAdapter.addFragment(StatisticsFragment.newInstance(mDataList.get(i)), mDataList.get(i).labelName);
                }
                if (mDataList.size() == 0) {
                    homeTabLayout.setVisibility(View.GONE);
                }
                break;

            case 5://帮助

                break;

            case 6://设置

                break;
        }
        if (myPagerAdapter.fragmentList.size()<2){
            homeTabLayout.setVisibility(View.GONE);
        }
        mVpContent.setOffscreenPageLimit(myPagerAdapter.getCount());
        mVpContent.setAdapter(myPagerAdapter);
        homeTabLayout.setupWithViewPager(mVpContent);
        Util.dynamicSetTabLayoutMode(homeTabLayout);
        mVpContent.setCurrentItem(0);
    }

    /**
     * 案件执法
     *
     * @return
     */
    private StatisticsInfo createCaseData() {
        StatisticsInfo dataInfo = new StatisticsInfo();
        dataInfo.labelName = "案件执法";
        dataInfo.itemList.add(new StatisticsItemInfo("部门统计", 0, "部门", R.mipmap.statis_bmtj));
        dataInfo.itemList.add(new StatisticsItemInfo("来源统计", 0, "来源", R.mipmap.statis_lytj));
        dataInfo.itemList.add(new StatisticsItemInfo("立案统计", 0, "类别", R.mipmap.statis_latj));
        dataInfo.itemList.add(new StatisticsItemInfo("结案统计", 0, "类别", R.mipmap.statis_tsjl));
        dataInfo.itemList.add(new StatisticsItemInfo("案件记录趋势", 1, "月份", R.mipmap.statis_ajjlqs));
        dataInfo.itemList.add(new StatisticsItemInfo("案件处罚趋势", 1, "月份", R.mipmap.statis_ajcfqs));
        return dataInfo;
    }

    /**
     * 投诉举报
     */
    private StatisticsInfo createCompData() {
        StatisticsInfo dataInfo = new StatisticsInfo();
        dataInfo.labelName = "投诉举报";
        dataInfo.itemList.add(new StatisticsItemInfo("受理部门", 0, "部门", R.mipmap.statis_slbm));
        dataInfo.itemList.add(new StatisticsItemInfo("投诉类别", 0, "类别", R.mipmap.statis_tslx));
        dataInfo.itemList.add(new StatisticsItemInfo("信息来源", 0, "类别", R.mipmap.statis_xxly));
        dataInfo.itemList.add(new StatisticsItemInfo("业务来源", 0, "类别", R.mipmap.statis_ywly));
        return dataInfo;
    }

    /**
     * 监管任务
     */
    private StatisticsInfo createSuperviseData() {
        StatisticsInfo dataInfo = new StatisticsInfo();
        dataInfo.labelName = "监管任务";
//        dataInfo.itemList.add(new StatisticsItemInfo("任务主体", 0, "类别", R.mipmap.statistic_qyfb));
        dataInfo.itemList.add(new StatisticsItemInfo("任务类型", 0, "类别", R.mipmap.statis_rwlx));
        dataInfo.itemList.add(new StatisticsItemInfo("检查主体", 0, "类别", R.mipmap.statis_jczt));
        dataInfo.itemList.add(new StatisticsItemInfo("执行任务数", 0, "类别", R.mipmap.statis_zxrws));
        return dataInfo;
    }

    /**
     * 统计分析
     *
     * @return
     */
    private List<StatisticsInfo> createStatisticeData() {
        List<StatisticsInfo> dataList = new ArrayList<>();
        StatisticsInfo info = new StatisticsInfo();
        info.labelName = "市场主体";
        info.itemList.add(new StatisticsItemInfo("企业类型", 0, "类别", R.mipmap.statis_zxrws));
        info.itemList.add(new StatisticsItemInfo("行业结构", 0, "类别", R.mipmap.statis_rwlx));
        info.itemList.add(new StatisticsItemInfo("特种设备", 0, "类别", R.mipmap.statis_tzsb));
        info.itemList.add(new StatisticsItemInfo("消保维权", 0, "类别", R.mipmap.statis_xbwq));
//        info.itemList.add(new StatisticsItemInfo("主体发展", 0, "类别", R.mipmap.statistic_hyfl));
        info.itemList.add(new StatisticsItemInfo("年报信息", 0, "类别", R.mipmap.statis_tsjl));
        info.itemList.add(new StatisticsItemInfo("许可证预警", 0, "类别", R.mipmap.statistics_qzrz));
        dataList.add(info);

//        info = new StatisticsInfo();
//        info.labelName = "食  品";
//        info.itemList.add(new StatisticsItemInfo("食品经营", 0, "区域", R.mipmap.statistics_spjy));
//        info.itemList.add(new StatisticsItemInfo("食品生产", 0, "区域", R.mipmap.statistics_spsc));
//        info.itemList.add(new StatisticsItemInfo("区级检查", 0, "区域", R.mipmap.statistics_qjjc));
//        dataList.add(info);
//
//        info = new StatisticsInfo();
//        info.labelName = "药 化 医";
//        info.itemList.add(new StatisticsItemInfo("药品生产", 0, "区域", R.mipmap.statistics_ypsc));
//        info.itemList.add(new StatisticsItemInfo("药品经营", 0, "区域", R.mipmap.statistics_ypjy));
//        info.itemList.add(new StatisticsItemInfo("器械经营", 0, "区域", R.mipmap.statistics_qxjy));
//        info.itemList.add(new StatisticsItemInfo("器械生产", 0, "区域", R.mipmap.statistics_qxsc));
//        info.itemList.add(new StatisticsItemInfo("化妆品", 0, "区域", R.mipmap.statistics_hzp));
//        info.itemList.add(new StatisticsItemInfo("药品检查", 0, "类别", R.mipmap.statistics_ypjc));
//        dataList.add(info);
//
//        info = new StatisticsInfo();
//        info.labelName = "特种设备";
//        info.itemList.add(new StatisticsItemInfo("设备结构", 2, "类别", R.mipmap.statistic_sbjg));
//        info.itemList.add(new StatisticsItemInfo("压力容器", 0, "区域", R.mipmap.statistic_ylrq));
//        info.itemList.add(new StatisticsItemInfo("电梯数量", 0, "区域", R.mipmap.statistic_dtsl));
//        info.itemList.add(new StatisticsItemInfo("锅炉", 0, "区域", R.mipmap.statistic_cyry));
//        info.itemList.add(new StatisticsItemInfo("起重机械", 0, "区域", R.mipmap.device_structure));
//        dataList.add(info);
//
//        info = new StatisticsInfo();
//        info.labelName = "质  量";
//        info.itemList.add(new StatisticsItemInfo("工业产品许可", 0, "区域", R.mipmap.statistics_gycpxk));
//        info.itemList.add(new StatisticsItemInfo("监督抽检", 0, "区域", R.mipmap.statistics_jdcj));
//        info.itemList.add(new StatisticsItemInfo("强制性认证", 0, "区域", R.mipmap.statistics_qzrz));
//        info.itemList.add(new StatisticsItemInfo("分类监督", 0, "区域", R.mipmap.statistics_fljd));
//        info.itemList.add(new StatisticsItemInfo("自愿性认证", 0, "区域", R.mipmap.statistics_zyxrz));
//        dataList.add(info);
//
//        info = new StatisticsInfo();
//        info.labelName = "计量相关";
//        info.itemList.add(new StatisticsItemInfo("计量器具", 0, "区域", R.mipmap.statistic_jefj));//TODO
//        dataList.add(info);
//
//        info = new StatisticsInfo();
//        info.labelName = "市场服务";
//        info.itemList.add(new StatisticsItemInfo("商标", 0, "区域", R.mipmap.shangbiao));
//        info.itemList.add(new StatisticsItemInfo("名牌", 0, "区域", R.mipmap.statistics_mp));
//        info.itemList.add(new StatisticsItemInfo("微企", 0, "区域", R.mipmap.statistics_wq));
//        info.itemList.add(new StatisticsItemInfo("农资", 0, "主体标识", R.mipmap.statistics_nz));
//        dataList.add(info);

//        info = new StatisticsInfo();
//        info.labelName = "安全隐患";
//        info.itemList.add(new StatisticsItemInfo("食品隐患", 0, "区域", R.mipmap.statistic_sbjg));
//        info.itemList.add(new StatisticsItemInfo("药品隐患", 0, "区域", R.mipmap.statistic_sbjg));
//        info.itemList.add(new StatisticsItemInfo("特种设备隐患", 0, "区域", R.mipmap.statistic_sbjg));
////        info.itemList.add(new StatisticsItemInfo("许可隐患", 0, 0, R.mipmap.statistic_jefj,null));
//        dataList.add(info);
        return dataList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.toolbar_right:
                Intent mapIntent = new Intent(HomeActivity.this, WorkInMapShowActivity.class);
                mapIntent.putExtra("type", ConstStrings.MapType_Main);
                startActivity(mapIntent);
                break;
            case id.tvActHome_setting:
                toActivity(SettingsActivity.class);
                break;
            case id.tvActHome_message:
                toActivity(MessageCenterActivity.class);
                break;
            case id.tvActHome_help:
                Intent helpIntent = new Intent(HomeActivity.this, HelpActivity.class);
                helpIntent.putExtra("isToLogin", false);
                startActivity(helpIntent);
                break;
            default:
                break;
        }
    }

    private void toActivity(Class<? extends Activity> c) {
        Intent intent = new Intent(HomeActivity.this, c);
        startActivity(intent);
        Util.activity_In(this);
    }
}