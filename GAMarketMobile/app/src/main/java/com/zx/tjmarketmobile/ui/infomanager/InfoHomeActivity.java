package com.zx.tjmarketmobile.ui.infomanager;

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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.R.id;
import com.zx.tjmarketmobile.entity.LegalEntity;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.ui.base.BaseFragment;
import com.zx.tjmarketmobile.ui.mainbase.MessageCenterActivity;
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
@SuppressWarnings("deprecation")
public class InfoHomeActivity extends BaseActivity implements OnClickListener {
    private static String TAG = "InfoHomeActivity";
    public ViewPager mVpContent;
    private TabLayout homeTabLayout;
    private int index;
    private Spinner mSpinner;
    private RadioGroup mRadioGroup;
    public EditText etInfosearch;
    public ImageButton searchImage;
    private StatisticsFragment mStatisticsFragment;// 统计分析
    private SharedPreferences mSharePreferences;
    private LinearLayout llRadioButton;
    private LegalEntity legalEntity;
    private RadioButton rbLegal1, rbLegal2, rbLegal3;
//    private boolean hasSetted = false;

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
        setContentView(R.layout.activity_info);

        addToolBar(true);
        showMidPic();
        hideRightImg();

        getRightImg().setOnClickListener(this);

        rbLegal1 = findViewById(id.rb_info_legal1);
        rbLegal2 = findViewById(id.rb_info_legal2);
        rbLegal3 = findViewById(id.rb_info_legal3);
        llRadioButton = findViewById(R.id.infoHome_search_radios);
        mVpContent = (ViewPager) findViewById(R.id.vp_normal_pager);
        homeTabLayout = (TabLayout) findViewById(R.id.tb_normal_layout);
        mSpinner = (Spinner) findViewById(R.id.info_spinner);
        mRadioGroup = (RadioGroup) findViewById(R.id.infoHome_search_radioGroup);

        index = getIntent().getIntExtra("item", 0);
        initialViewPager();

        mVpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.i("wangwansheng", "page scroll to " + i);
                String title = getMidText().getText().toString();
                if (title.equals("许可证书")) {
                    int curItem = mVpContent.getCurrentItem();
                    if (i == 1 || i == 3) {
                        //drug/equipment:
                        final List<String> datas = new ArrayList<>();
                        datas.add("销售");
                        datas.add("生产");
                        enableSpinner(datas);
                    } else {
                        enableSpinner(null);
                    }
                } else if (title.equals("计量器具")) {
                    if (i == 0) {
//                        final List<String> datas = new ArrayList<>();
//                        datas.add("销大型商场超市贸易结算");
//                        datas.add("医疗卫生强检");
//                        datas.add("加油站");
//                        datas.add("制造计量器具许可");
//                        enableSpinner(datas);
                    }
                } else if (title.equals("法律法规")) {

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (legalEntity != null) {
                    switch (checkedId) {
                        case id.rb_info_legal1:
                            legalEntity.setSelecteRadio(1);
                            break;
                        case id.rb_info_legal2:
                            legalEntity.setSelecteRadio(2);
                            break;
                        case id.rb_info_legal3:
                            legalEntity.setSelecteRadio(3);
                            break;
                    }
                }
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

        mSharePreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void loadData() {
        TextView textView = getMidText();
        String title = textView.getText().toString();
        int curItem = mVpContent.getCurrentItem();
        fragment = (BaseFragment) myPagerAdapter.getItem(curItem);
        if (fragment != null) {
            if (findViewById(R.id.infoHome_search_radios).getVisibility() == View.VISIBLE) {
                mVpContent.setCurrentItem(1);
                fragment = (BaseFragment) myPagerAdapter.getItem(1);
                fragment.load(etInfosearch.getText().toString(), legalEntity);
            } else {
                String value = "";
                int selectPos = mSpinner.getSelectedItemPosition();
                if (title.equals("标准信息查询")) {
                    fragment.load(etInfosearch.getText().toString());
                } else if (title.equals("特种设备")) {
                    fragment.load(etInfosearch.getText().toString() + "*");
                } else if (title.equals("许可证书")) {
                    if (curItem == 0 || curItem == 2) {
                        fragment.load(etInfosearch.getText().toString() + "*");
                    } else if (curItem == 1) {
                        //drug
                        if (selectPos == 0)
                            value = "t_lic_drug_sale";
                        else
                            value = "t_lic_drug_producted";
                        fragment.load(value + ";" + etInfosearch.getText().toString() + "*");
                    } else if (curItem == 3) {
                        //equipment
                        if (selectPos == 0)
                            value = "t_lic_equipment_sale";
                        else
                            value = "t_lic_equipment_producted";
                        fragment.load(value + ";" + etInfosearch.getText().toString() + "*");
                    }
                } else if (title.equals("计量器具")) {
                    switch (selectPos) {
                        case 0:
                            value = "t_trading_tools";
                            break;
                        case 1:
                            value = "t_medical_tools";
                            break;
                        case 2:
                            value = "t_tanker_tools";
                            break;
                        case 3:
                            value = "t_license_tools";
                            break;
                    }

                    if (curItem == 0) {
                        fragment.load(value);
                    } else {
                        fragment.load(value + ";" + etInfosearch.getText().toString() + "*");
                    }

                } else if (title.equals("法律法规")) {
//                    fragment = (BaseFragment) myPagerAdapter.getItem(1);
                    fragment.load(etInfosearch.getText().toString());
                }
            }
        } else {
        }
    }

    private void enableSpinner(final List<String> datas) {
        if (datas == null) {
            findViewById(R.id.llActHome_type_bar).setVisibility(View.GONE);
            return;
        }
        findViewById(R.id.llActHome_type_bar).setVisibility(View.VISIBLE);
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, datas);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //为TextView控件赋值！在适配器中获取一个值赋给tv
                Log.i("wangwansheng", "select!! " + i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setLegalRadioStatus(int type, LegalEntity customData) {
        legalEntity = customData;
        if (type == 0) {//item
            rbLegal1.setEnabled(true);
            rbLegal2.setEnabled(true);
            rbLegal3.setEnabled(true);
            mRadioGroup.check(rbLegal1.getId());
            legalEntity.setSelecteRadio(1);
        } else if (type == 1) {//全部
            rbLegal1.setEnabled(false);
            rbLegal2.setEnabled(false);
            rbLegal3.setEnabled(true);
            mRadioGroup.check(rbLegal3.getId());
            legalEntity.setSelecteRadio(3);
        } else {//当前目录
            rbLegal1.setEnabled(false);
            rbLegal2.setEnabled(true);
            rbLegal3.setEnabled(true);
            mRadioGroup.check(rbLegal2.getId());
            legalEntity.setSelecteRadio(2);
        }
    }

    public void initialViewPager() {
        switch (index) {
            case 0://标准信息查询
                setMidText("标准信息查询");
                standardMessageSelectFragment = StandardMessageSelectFragment.newInstance();
                myPagerAdapter.addFragment(standardMessageSelectFragment, "标准信息查询");
                break;
            case 1://特种设备
                setMidText("特种设备");
                deviceListFragment = DeviceListFragment.newInstance();
                myPagerAdapter.addFragment(deviceListFragment, "特种设备");
                break;
            case 2://许可证书
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
            case 3://计量器具
                setMidText("计量器具");
                final List<String> datas = new ArrayList<>();
                datas.add("大型商场超市贸易结算");
                datas.add("医疗卫生强检");
                datas.add("加油站");
                datas.add("制造计量器具许可");
                enableSpinner(datas);
//                measureCustomFragment = MeasureCustomFragment.newInstance();
//                myPagerAdapter.addFragment(measureCustomFragment, "自定义表信息");
                measureLiebiaoFragment = MeasureLiebiaoFragment.newInstance();
                myPagerAdapter.addFragment(measureLiebiaoFragment, "计量器具");
                break;

            case 4://法律法规
                setMidText("法律法规");
//                findViewById(R.id.llInfoHome_bottom).setVisibility(View.GONE);
//                findViewById(R.id.infoHome_search_radios).setVisibility(View.VISIBLE);
//                findViewById(R.id.infoHome_search_edit).setVisibility(View.VISIBLE);
                llRadioButton.setVisibility(View.VISIBLE);
                legalSelectFragment = LegalSelectFragment.newInstance(userManager.getUser(this).getId(), userManager.getUser(this).getDepartmentCode());
                myPagerAdapter.addFragment(legalSelectFragment, "查询菜单");
                legalSelectLawFragment = LegalSelectLawFragment.newInstance();
                myPagerAdapter.addFragment(legalSelectLawFragment, "法律法规搜索");
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