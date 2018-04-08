package com.zx.tjmarketmobile.ui.mainbase;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.R.id;
import com.zx.tjmarketmobile.entity.StatisticsInfo;
import com.zx.tjmarketmobile.entity.StatisticsItemInfo;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.ui.infomanager.DeviceListFragment;
import com.zx.tjmarketmobile.ui.infomanager.LegalSelectFragment;
import com.zx.tjmarketmobile.ui.infomanager.LegalSelectLawFragment;
import com.zx.tjmarketmobile.ui.infomanager.LisenceCosmeticFragment;
import com.zx.tjmarketmobile.ui.infomanager.LisenceDrugFragment;
import com.zx.tjmarketmobile.ui.infomanager.LisenceEquipmentFragment;
import com.zx.tjmarketmobile.ui.infomanager.LisenceFoodFragment;
import com.zx.tjmarketmobile.ui.infomanager.MeasureCustomFragment;
import com.zx.tjmarketmobile.ui.infomanager.MeasureLiebiaoFragment;
import com.zx.tjmarketmobile.ui.infomanager.StandardMessageSelectFragment;
import com.zx.tjmarketmobile.ui.map.WorkInMapShowActivity;
import com.zx.tjmarketmobile.ui.statistics.StatisticsFragment;
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
public class InfoHomeActivity extends BaseActivity implements OnClickListener {
    private static String TAG = "InfoHomeActivity";
    public ViewPager mVpContent;
    private TabLayout homeTabLayout;
    private TaskNumFragment mComplaintFragment;// 投诉举报
    private TaskNumFragment mSuperviseFragment;// 监管任务
    private int index;
    private Spinner mSpinner;
    public EditText etInfosearch;
    public ImageButton searchImage;
    private StatisticsFragment mStatisticsFragment;// 统计分析
    private SharedPreferences mSharePreferences;

    StandardMessageSelectFragment standardMessageSelectFragment;
    DeviceListFragment deviceListFragment;
    LisenceFoodFragment lisenceFoodFragment;
    LisenceDrugFragment lisenceDrugFragment;
    LisenceEquipmentFragment lisenceEquipmentFragment;
    LisenceCosmeticFragment lisenceCosmeticFragment;
    MeasureCustomFragment measureCustomFragment;
    MeasureLiebiaoFragment measureLiebiaoFragment;
    LegalSelectFragment legalSelectFragment;
    LegalSelectLawFragment legalSelectLawFragment;

    private BaseFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
        setContentView(R.layout.activity_info);

        addToolBar(true);
        showMidPic();

        getRightImg().setOnClickListener(this);

        mVpContent = (ViewPager) findViewById(R.id.vp_normal_pager);
        homeTabLayout = (TabLayout) findViewById(R.id.tb_normal_layout);
        mSpinner = (Spinner) findViewById(R.id.info_spinner);

        final List<String> datas = new ArrayList<>();
        datas.add("标准信息查询");
        datas.add("特种设备");
        datas.add("许可证书");
        datas.add("计量器具");
        datas.add("法律法规");

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datas);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //为TextView控件赋值！在适配器中获取一个值赋给tv
//                myPagerAdapter.fragmentList.clear();
//                myPagerAdapter.fragmentList.clear();
                index = i;
                Log.i("wangwansheng", "select!!");
//                initialViewPager();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etInfosearch = (EditText) findViewById(R.id.llInfoHome_bottom_edit);
        searchImage = (ImageButton) findViewById(R.id.info_case_search);
        searchImage.setOnClickListener(this);
        etInfosearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                loadData();
                return true;
            }
        });

        index = getIntent().getIntExtra("item", 0);
        initialViewPager();
//        findViewById(id.tvActHome_setting).setOnClickListener(this);
//        findViewById(id.tvActHome_message).setOnClickListener(this);
//        findViewById(id.tvActHome_help).setOnClickListener(this);
        mSharePreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void loadData() {
        switch (index) {
            case 0:
                Log.i("wws", "msg is " + etInfosearch.getText().toString() + "*");
                standardMessageSelectFragment.load(etInfosearch.getText().toString() + "*");
                break;
            case 1:
                Log.i("wws", "msg is " + etInfosearch.getText().toString() + "*");
                deviceListFragment.load(etInfosearch.getText().toString() + "*");
                break;
            case 2:
                Log.i("wws", "msg is " + etInfosearch.getText().toString() + "*");
                lisenceFoodFragment.load(etInfosearch.getText().toString() + "*");
                break;
            case 3:
                Log.i("wws", "msg is " + etInfosearch.getText().toString() + "*");
                measureCustomFragment.load(etInfosearch.getText().toString() + "*");
                break;
            case 4:
                break;
        }
    }

    public InfoHomeActivity() {
    }

    public void initialViewPager() {
        switch (index) {
            case 0://监管任务
                setMidText("标准信息查询");
                standardMessageSelectFragment = StandardMessageSelectFragment.newInstance();
                myPagerAdapter.addFragment(standardMessageSelectFragment, "标准信息查询");
//                fragment = standardMessageSelectFragment;
                break;
            case 1://投诉举报
                setMidText("特种设备");
                deviceListFragment = DeviceListFragment.newInstance();
                myPagerAdapter.addFragment(deviceListFragment, "特种设备");
                break;
            case 2://案件执法
                setMidText("许可证书");
                lisenceFoodFragment = LisenceFoodFragment.newInstance();
                myPagerAdapter.addFragment(lisenceFoodFragment, "食品");
                lisenceDrugFragment = LisenceDrugFragment.newInstance();
                myPagerAdapter.addFragment(lisenceDrugFragment, "药品");
                lisenceCosmeticFragment = LisenceCosmeticFragment.newInstance();
                myPagerAdapter.addFragment(lisenceCosmeticFragment, "化妆品");
                lisenceEquipmentFragment = LisenceEquipmentFragment.newInstance();
                myPagerAdapter.addFragment(lisenceEquipmentFragment, "医疗器械");
                break;
//                homeTabLayout.setVisibility(View.VISIBLE);
            case 3://信息管理
                setMidText("计量器具");
                measureCustomFragment = MeasureCustomFragment.newInstance();
                myPagerAdapter.addFragment(measureCustomFragment, "自定义表信息");
                measureLiebiaoFragment = MeasureLiebiaoFragment.newInstance();
                myPagerAdapter.addFragment(measureLiebiaoFragment, "计量器具");
                break;

            case 4://法律法规
                setMidText("法律法规");
                legalSelectFragment = LegalSelectFragment.newInstance();
                myPagerAdapter.addFragment(legalSelectFragment, "法律查詢");
                legalSelectLawFragment = LegalSelectLawFragment.newInstance();
                myPagerAdapter.addFragment(legalSelectLawFragment, "法律查詢");
                break;

        }
        if (myPagerAdapter.fragmentList.size() < 2) {
            homeTabLayout.setVisibility(View.GONE);
        } else {
            homeTabLayout.setVisibility(View.VISIBLE);
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
        dataInfo.itemList.add(new StatisticsItemInfo("部门统计", 0, "部门", R.mipmap.statistic_ajfb));
        dataInfo.itemList.add(new StatisticsItemInfo("来源统计", 0, "来源", R.mipmap.statistic_ajly));
        dataInfo.itemList.add(new StatisticsItemInfo("立案统计", 0, "类别", R.mipmap.statistic_wflx));
        dataInfo.itemList.add(new StatisticsItemInfo("结案统计", 0, "类别", R.mipmap.statistic_jatj));
        dataInfo.itemList.add(new StatisticsItemInfo("案件记录趋势", 0, "类别", R.mipmap.statistic_jatj));
        dataInfo.itemList.add(new StatisticsItemInfo("案件处罚趋势", 0, "类别", R.mipmap.statistic_jatj));
        return dataInfo;
    }

    /**
     * 投诉举报
     */
    private StatisticsInfo createCompData() {
        StatisticsInfo dataInfo = new StatisticsInfo();
        dataInfo.labelName = "投诉举报";
        dataInfo.itemList.add(new StatisticsItemInfo("受理部门", 0, "部门", R.mipmap.statistic_qyfb));
        dataInfo.itemList.add(new StatisticsItemInfo("投诉类别", 0, "类别", R.mipmap.statistic_jbly));
        dataInfo.itemList.add(new StatisticsItemInfo("信息来源", 0, "类别", R.mipmap.statistic_jblx));
        dataInfo.itemList.add(new StatisticsItemInfo("业务来源", 0, "类别", R.mipmap.statistic_jblx));
        return dataInfo;
    }

    /**
     * 监管任务
     */
    private StatisticsInfo createSuperviseData() {
        StatisticsInfo dataInfo = new StatisticsInfo();
        dataInfo.labelName = "监管任务";
        dataInfo.itemList.add(new StatisticsItemInfo("任务主体", 0, "类别", R.mipmap.statistic_qyfb));
        dataInfo.itemList.add(new StatisticsItemInfo("任务类型", 0, "类别", R.mipmap.statistic_jbly));
        dataInfo.itemList.add(new StatisticsItemInfo("检查主体", 0, "类别", R.mipmap.statistic_jblx));
        dataInfo.itemList.add(new StatisticsItemInfo("执行任务数", 0, "类别", R.mipmap.statistic_jblx));
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
        info.itemList.add(new StatisticsItemInfo("企业类型", 0, "类别", R.mipmap.statistic_ztsl));
        info.itemList.add(new StatisticsItemInfo("行业结构", 0, "类别", R.mipmap.statistic_asfl));
        info.itemList.add(new StatisticsItemInfo("特种设备", 0, "类别", R.mipmap.statistic_hyfl));
        info.itemList.add(new StatisticsItemInfo("消保维权", 0, "类别", R.mipmap.statistic_hyfl));
        info.itemList.add(new StatisticsItemInfo("主体发展", 0, "类别", R.mipmap.statistic_hyfl));
        info.itemList.add(new StatisticsItemInfo("年报信息", 0, "类别", R.mipmap.statistic_hyfl));
        info.itemList.add(new StatisticsItemInfo("许可证预警", 0, "类别", R.mipmap.statistic_hyfl));
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
            case id.info_case_search:
                loadData();
                break;
            case id.toolbar_right:
                Intent mapIntent = new Intent(InfoHomeActivity.this, WorkInMapShowActivity.class);
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
                Intent helpIntent = new Intent(InfoHomeActivity.this, HelpActivity.class);
                helpIntent.putExtra("isToLogin", false);
                startActivity(helpIntent);
                break;
            default:
                break;
        }
    }

    private void toActivity(Class<? extends Activity> c) {
        Intent intent = new Intent(InfoHomeActivity.this, c);
        startActivity(intent);
        Util.activity_In(this);
    }
}