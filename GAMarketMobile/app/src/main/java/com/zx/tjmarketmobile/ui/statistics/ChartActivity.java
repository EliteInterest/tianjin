package com.zx.tjmarketmobile.ui.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.adapter.ChartTableAdapter;
import com.zx.tjmarketmobile.entity.DeviceSecurityRiskEntity;
import com.zx.tjmarketmobile.entity.KeyValueInfo;
import com.zx.tjmarketmobile.entity.QualitySampleEntity;
import com.zx.tjmarketmobile.entity.StatisticsItemInfo;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.IChartListener;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.ChartUtil;
import com.zx.tjmarketmobile.util.DigitUtil;
import com.zx.tjmarketmobile.util.DrawChartUtil;
import com.zx.tjmarketmobile.util.Util;

import org.achartengine.GraphicalView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create By Xiangb On 2017/5/17
 * 功能：统计分析界面
 */
public class ChartActivity extends BaseActivity implements IChartListener {

    private LinearLayout llChart;//图表
    private RecyclerView rvChart;
    public TextView tvKey, tvValue, tvOther, tvPercent;
    private TextView tvBack;
    private TextView mTvTimeContent, mTvQueryRiskByTime;
    private Spinner mQueryTypeSpinner;
    private LinearLayout mQueryByTypeSpinnerLayout;//按类型选择日期的线性布局
    private LinearLayout mTimeSelectedLayout;
    private LinearLayout llChartYear;
    private Spinner spChartyear;
    private TextView mTvStartTime, mTvEndTime, mTvQueryByTime;
    private StatisticsItemInfo mItemInfo;
    private ChartUtil mChartUtil;
    private ChartTableAdapter mAdapter;
    private List<KeyValueInfo> keyList = new ArrayList<>();
    private List<String> tableCodes = new ArrayList<>();
    private String mStartTime = DrawChartUtil.getDate(true, false), mEndTime = DrawChartUtil.getDate(false, true), mType = "";

    private enum mQueryType {
        year,
        month
    }

    private ApiData getChartInfo = new ApiData(ApiData.HTTP_ID_statistics_single_parameter);
    private ApiData countEntityType = new ApiData(ApiData.HTTP_ID_countEntityType);
    private ApiData deviceSecurityRiskData = new ApiData(ApiData.HTTP_ID_device_security_risk_parameter);
    private ApiData drugSamplingData = new ApiData(ApiData.HTTP_ID_drug_sample_parameter);
    private ApiData industrialProductData = new ApiData(ApiData.HTTP_ID_industrial_product_parameter);
    private ApiData countHzpList = new ApiData(ApiData.HTTP_ID_statistics_hzp);

    private ApiData caseDep = new ApiData(ApiData.HTTP_ID_statistics_case_queryDep);//根据部门统计
    private ApiData caseType = new ApiData(ApiData.HTTP_ID_statistics_case_queryType);//来源统计
    private ApiData caseIsCase = new ApiData(ApiData.HTTP_ID_statistics_case_queryIsCase);//立案统计
    private ApiData caseCloseCount = new ApiData(ApiData.HTTP_ID_statistics_case_queryClosedCount);//结案统计
    private ApiData caseRecordCount = new ApiData(ApiData.HTTP_ID_statistics_case_queryRecordCount);//案件记录趋势统计
    private ApiData casePunishCount = new ApiData(ApiData.HTTP_ID_statistics_case_queryPunishCount);//案件处罚趋势统计

    private ApiData compDepart = new ApiData(ApiData.HTTP_ID_statistics_comp_countDepartment);//受理部门统计
    private ApiData compType = new ApiData(ApiData.HTTP_ID_statistics_comp_countType);//投诉类别统计
    private ApiData compInfo = new ApiData(ApiData.HTTP_ID_statistics_comp_countInfo);//信息来源统计
    private ApiData compBussiniss = new ApiData(ApiData.HTTP_ID_statistics_comp_countBussiniss);//业务来源统计

    private ApiData enterType = new ApiData(ApiData.HTTP_ID_statistics_entity_enterpriseType);//主体
    private ApiData enterIndustry = new ApiData(ApiData.HTTP_ID_statistics_entity_enterpriseIndustry);
    private ApiData enterEquipType = new ApiData(ApiData.HTTP_ID_statistics_entity_equipmentType);
    private ApiData enterComplain = new ApiData(ApiData.HTTP_ID_statistics_entity_enterpriseComplain);
    private ApiData enterDev = new ApiData(ApiData.HTTP_ID_statistics_entity_enterpriseDev);
    private ApiData enterAnn = new ApiData(ApiData.HTTP_ID_statistics_entity_enterpriseAnn);
    private ApiData enterWarning = new ApiData(ApiData.HTTP_ID_statistics_entity_enterpriseWarning);

    private ApiData superCountTask = new ApiData(ApiData.HTTP_ID_statistics_super_countTask);
    private ApiData superCountType = new ApiData(ApiData.HTTP_ID_statistics_super_countType);
    private ApiData superEnterprise = new ApiData(ApiData.HTTP_ID_statistics_super_countEnterprise);
    private ApiData superDoTask = new ApiData(ApiData.HTTP_ID_statistics_super_countDoTask);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        addToolBar(true);
        hideRightImg();

        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        llChart = (LinearLayout) findViewById(R.id.llChart_chart);
        rvChart = (RecyclerView) findViewById(R.id.rvChart_table);
        tvKey = (TextView) findViewById(R.id.tvChart_key);
        tvValue = (TextView) findViewById(R.id.tvChart_value);
        tvOther = (TextView) findViewById(R.id.tvChart_other);
        tvPercent = (TextView) findViewById(R.id.tvChart_percent);
        tvBack = (TextView) findViewById(R.id.tv_reback);
        mTvTimeContent = (TextView) findViewById(R.id.tv_statistics_content_time);
        mTvQueryRiskByTime = (TextView) findViewById(R.id.tv_statistics_query);
        mQueryByTypeSpinnerLayout = (LinearLayout) findViewById(R.id.time_type_select_layout);
        mTimeSelectedLayout = (LinearLayout) findViewById(R.id.time_select_layout);
        mTvStartTime = (TextView) findViewById(R.id.tvActStatistics_starttime);
        mTvEndTime = (TextView) findViewById(R.id.tvActStatistics_endtime);
        mTvQueryByTime = (TextView) findViewById(R.id.tvActStatistics_query);
        llChartYear = findViewById(R.id.ll_chart_year);
        spChartyear = findViewById(R.id.sp_chart_year);

        getChartInfo.setLoadingListener(this);
        deviceSecurityRiskData.setLoadingListener(this);
        countEntityType.setLoadingListener(this);
        countHzpList.setLoadingListener(this);
        tvBack.setOnClickListener(this);
        mTvTimeContent.setOnClickListener(this);
        mTvQueryRiskByTime.setOnClickListener(this);
        drugSamplingData.setLoadingListener(this);
        industrialProductData.setLoadingListener(this);
        caseDep.setLoadingListener(this);
        caseType.setLoadingListener(this);
        caseIsCase.setLoadingListener(this);
        caseCloseCount.setLoadingListener(this);
        caseRecordCount.setLoadingListener(this);
        casePunishCount.setLoadingListener(this);
        compBussiniss.setLoadingListener(this);
        compInfo.setLoadingListener(this);
        compType.setLoadingListener(this);
        compDepart.setLoadingListener(this);
        enterType.setLoadingListener(this);
        enterIndustry.setLoadingListener(this);
        enterEquipType.setLoadingListener(this);
        enterComplain.setLoadingListener(this);
        enterDev.setLoadingListener(this);
        enterAnn.setLoadingListener(this);
        enterWarning.setLoadingListener(this);
        superCountTask.setLoadingListener(this);
        superCountType.setLoadingListener(this);
        superEnterprise.setLoadingListener(this);
        superDoTask.setLoadingListener(this);

        mItemInfo = (StatisticsItemInfo) getIntent().getSerializableExtra("task");
        setMidText(mItemInfo.name);

        mChartUtil = new ChartUtil(mContext);

        tvKey.setText(mItemInfo.tableTitle);

        mQueryTypeSpinner = (Spinner) findViewById(R.id.query_spinner);
        List<String> typeList = new ArrayList<>();
        typeList.add("按年统计");
        typeList.add("按月统计");
        ArrayAdapter<String> queryTypeAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_layout, typeList);
        mQueryTypeSpinner.setAdapter(queryTypeAdapter);
        mQueryTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                tv.setTextColor(ContextCompat.getColor(mContext, R.color.darkgrey));    //设置颜色
                tv.setTextSize(12.0f);    //设置大小
                tv.setGravity(Gravity.CENTER);//设置居中
                if (position == 0) {
                    mType = mQueryType.year.toString();
                    mTvTimeContent.setText(DrawChartUtil.getDate(true, false).subSequence(0, 4));
                    mStartTime = mTvTimeContent.getText().toString() + "-01-31";
                    mEndTime = mTvTimeContent.getText().toString() + "-12-31";
                } else {
                    mType = mQueryType.month.toString();
                    mTvTimeContent.setText(DrawChartUtil.getDate(true, false).subSequence(0, 7));
                    mStartTime = DrawChartUtil.getDate(true, false);
                    mEndTime = DrawChartUtil.getDate(false, true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mAdapter = new ChartTableAdapter(this, mItemInfo, keyList);
        rvChart.setLayoutManager(new LinearLayoutManager(this));
        rvChart.setAdapter(mAdapter);
        mAdapter.setChartListener(this);

        switch (mItemInfo.name) {
            case "任务类型":
            case "检查主体":
            case "执行任务数":
            case "结案统计":
            case "案件记录趋势":
            case "案件处罚趋势":
            case "年报信息":
            case "主体发展":
                llChartYear.setVisibility(View.VISIBLE);
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                List<String> yearList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    yearList.add((year - i) + "");
                }
                spChartyear.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, yearList));
                break;
            default:
                break;
        }
        spChartyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loadData();
    }

    /**
     * 加载数据
     */
    public void loadData() {
        switch (mItemInfo.name) {
            case "主体类别":
                countEntityType.loadData();
                break;
            case "特种设备隐患":
                deviceSecurityRiskData.loadData(mItemInfo.name, mStartTime, mEndTime, mType);
                break;
            case "工业产品检查":
                industrialProductData.loadData(mStartTime);
                break;
            case "化妆品":
                countHzpList.loadData(mItemInfo.name);
                break;
            case "部门统计":
                caseDep.loadData("", "");
                break;
            case "来源统计":
                caseType.loadData("", "");
                break;
            case "立案统计":
                caseIsCase.loadData("", "");
                break;
            case "结案统计":
                caseIsCase.loadData(spChartyear.getSelectedItem());
                break;
            case "案件记录趋势":
                tvPercent.setVisibility(View.GONE);
                caseRecordCount.loadData(spChartyear.getSelectedItem());
                break;
            case "案件处罚趋势":
                tvPercent.setVisibility(View.GONE);
                casePunishCount.loadData(spChartyear.getSelectedItem());
                break;
            case "受理部门":
                compDepart.loadData("", "");
                break;
            case "投诉类别":
                compType.loadData("", "");
                break;
            case "信息来源":
                compInfo.loadData("", "");
                break;
            case "业务来源":
                compBussiniss.loadData("", "");
                break;
            case "企业类型":
                enterType.loadData();
                break;
            case "行业结构":
                enterIndustry.loadData();
                break;
            case "特种设备":
                enterEquipType.loadData();
                break;
            case "消保维权":
                enterComplain.loadData();
                break;
            case "主体发展":
                enterDev.loadData(spChartyear.getSelectedItem());
                break;
            case "年报信息":
                enterAnn.loadData(spChartyear.getSelectedItem());
                break;
            case "许可证预警":
                enterWarning.loadData();
                break;
            case "任务主体":
                superCountTask.loadData("");
                break;
            case "任务类型":
                superCountType.loadData(spChartyear.getSelectedItem());
                break;
            case "检查主体":
                superEnterprise.loadData(spChartyear.getSelectedItem());
                break;
            case "执行任务数":
                superEnterprise.loadData(spChartyear.getSelectedItem());
                break;
            default:
                getChartInfo.loadData(mItemInfo.name);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_reback:
                tableCodes.remove(tableCodes.size() - 1);
                if (mItemInfo.name.equals("主体类别")) {
                    countEntityType.loadData();
                } else if (mItemInfo.name.equals("行业分类")) {
                    getChartInfo.loadData(mItemInfo.name, tableCodes.size() == 0 ? "" : tableCodes.get(tableCodes.size() - 1));
                }
                break;
            case R.id.tv_statistics_query:
                loadData();
                break;
            case R.id.tvActStatistics_starttime:
                Util.setDateIntoText(ChartActivity.this, v);
                break;
            case R.id.tvActStatistics_endtime:
                Util.setDateIntoText(ChartActivity.this, v);
                break;
            case R.id.tvActStatistics_query:
                String startTime = mTvStartTime.getText().toString();
                String endTime = mTvEndTime.getText().toString();
                if (!startTime.equals("开始时间")) {
                    if (!endTime.equals("结束时间")) {
                        boolean isRight = Util.compareTime(startTime, endTime);
                        if (isRight) {
                            loadData();
                        } else {
                            showToast("开始时间应该早于结束时间！");
                        }
                    } else {
                        showToast("请输入结束时间！");
                    }
                } else {
                    showToast("请输入开始时间！");
                }
                break;
            case R.id.tv_statistics_content_time:
                Util.setDateYearIntoText(this, v, mType);
                break;
            default:
                break;
        }
    }

    //图表name的点击事件
    @Override
    public void OnKeyClick(int position) {
        KeyValueInfo mEntity = keyList.get(position);
        if ("合计".equals(mEntity.key) || "总数".equals(mEntity.key) || "总计".equals(mEntity.key)) {
            return;
        }
        switch (mItemInfo.name) {
            case "主体类别":
                if (mEntity.key.equals("企业")) {
                    tableCodes.add(mEntity.code);
                    getChartInfo.loadData(mItemInfo.name, mEntity.code);
                }
                break;
            case "行业分类":
                tableCodes.add(mEntity.code);
                getChartInfo.loadData(mItemInfo.name, mEntity.code);
                break;
            default:
                break;
        }
    }

    //图表num的点击事件
    @Override
    public void onValueClick(String columnString, String lineString) {
        String[] hasList = new String[]{"商标", "微企", "食品隐患", "药品隐患", "特种设备隐患", "主体数量", "主体类别", "行业分类"};
        if (Arrays.asList(hasList).contains(mItemInfo.name)) {
            return;
        }
        Intent intent = new Intent(this, ChartListActivity.class);
        intent.putExtra("key", mItemInfo.name);
        intent.putExtra("line", lineString);
        intent.putExtra("column", columnString);
        startActivity(intent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        switch (id) {
            case ApiData.HTTP_ID_statistics_hzp:
            case ApiData.HTTP_ID_countEntityType:
            case ApiData.HTTP_ID_statistics_single_parameter:
            case ApiData.HTTP_ID_statistics_case_queryDep:
            case ApiData.HTTP_ID_statistics_case_queryType:
            case ApiData.HTTP_ID_statistics_case_queryClosedCount:
            case ApiData.HTTP_ID_statistics_case_queryIsCase:
            case ApiData.HTTP_ID_statistics_case_queryPunishCount:
            case ApiData.HTTP_ID_statistics_case_queryRecordCount:
            case ApiData.HTTP_ID_statistics_comp_countDepartment:
            case ApiData.HTTP_ID_statistics_comp_countType:
            case ApiData.HTTP_ID_statistics_comp_countBussiniss:
            case ApiData.HTTP_ID_statistics_comp_countInfo:
            case ApiData.HTTP_ID_statistics_entity_enterpriseType:
            case ApiData.HTTP_ID_statistics_entity_enterpriseDev:
            case ApiData.HTTP_ID_statistics_entity_enterpriseIndustry:
            case ApiData.HTTP_ID_statistics_entity_enterpriseAnn:
            case ApiData.HTTP_ID_statistics_entity_enterpriseComplain:
            case ApiData.HTTP_ID_statistics_entity_equipmentType:
            case ApiData.HTTP_ID_statistics_super_countTask:
            case ApiData.HTTP_ID_statistics_super_countType:
            case ApiData.HTTP_ID_statistics_super_countEnterprise:
            case ApiData.HTTP_ID_statistics_super_countDoTask:
            case ApiData.HTTP_ID_statistics_entity_enterpriseWarning:
                List<KeyValueInfo> tempList = (List<KeyValueInfo>) b.getEntry();
                initChart(tempList);
                keyList.clear();
                keyList.addAll(tempList);
                mAdapter.notifyDataSetChanged();
                if (tableCodes.size() > 0) {
                    tvBack.setVisibility(View.VISIBLE);
                } else {
                    tvBack.setVisibility(View.GONE);
                }
                break;
            case ApiData.HTTP_ID_device_security_risk_parameter://特种设备隐患
                List<DeviceSecurityRiskEntity> deviceSecurityRiskEntityList = (List<DeviceSecurityRiskEntity>) b.getEntry();
                List<KeyValueInfo> deviceList = new ArrayList<>();
                for (int i = 0; i < deviceSecurityRiskEntityList.size(); i++) {
                    DeviceSecurityRiskEntity mDevice = deviceSecurityRiskEntityList.get(i);
                    deviceList.add(new KeyValueInfo(mDevice.getType(), mDevice.getSecurityFile(), mDevice.getSecurityContract(), mDevice.getNextOverhaul()));
                }
                initChart(deviceList);
                keyList.clear();
                keyList.addAll(deviceList);
                mAdapter.notifyDataSetChanged();
                break;
            case ApiData.HTTP_ID_drug_sample_parameter://药品检验
            case ApiData.HTTP_ID_industrial_product_parameter://工业产品检验
                List<QualitySampleEntity> qualitySampleEntityList = (List<QualitySampleEntity>) b.getEntry();
                List<KeyValueInfo> sampleList = new ArrayList<>();
                for (int i = 0; i < qualitySampleEntityList.size(); i++) {
                    QualitySampleEntity mSample = qualitySampleEntityList.get(i);
                    sampleList.add(new KeyValueInfo(mSample.getType(), mSample.getCheckoutTotal(), mSample.getCheckoutPass()));
                }
                initChart(sampleList);
                keyList.clear();
                keyList.addAll(sampleList);
                mAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化图表
     *
     * @param keyList
     */
    private void initChart(List<KeyValueInfo> keyList) {
        llChart.removeAllViews();
        if (keyList.size() == 0) {
            return;
        }
        switch (mItemInfo.chartType) {
            case 0://柱状图
                drawBarChart(keyList);
                break;
            case 1://折线图
                drawLineChart(keyList);
                break;
            case 2://饼状图
                drawPieChart(keyList);
                break;
        }
    }

    /**
     * 画饼状图
     *
     * @param keyList
     */
    private void drawPieChart(List<KeyValueInfo> keyList) {
        List<KeyValueInfo> tempList = new ArrayList<>();
        for (int i = 0; i < keyList.size(); i++) {
            KeyValueInfo keyValueInfo = keyList.get(i);
            if ("合计".equals(keyValueInfo.key) || "总数".equals(keyValueInfo.key)
                    || "0".equals(keyValueInfo.value) || "总计".equals(keyValueInfo.key)) {
            } else {
                tempList.add(keyList.get(i));
            }
        }
        int[] colors = new int[]{getMyColor(R.color.chart8), getMyColor(R.color.chart7),
                getMyColor(R.color.chart6), getMyColor(R.color.chart4), getMyColor(R.color.chart5),
                getMyColor(R.color.chart3), getMyColor(R.color.chart2), getMyColor(R.color.chart1)};
        mChartUtil.setColors(colors);
        GraphicalView categoryView = mChartUtil.createCategoryView("", "", tempList);
        llChart.removeAllViews();
        llChart.addView(categoryView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    /**
     * 画折线图
     *
     * @param keyList
     */
    private void drawLineChart(List<KeyValueInfo> keyList) {
        List<double[]> values = new ArrayList<>();
        Map<String, Object> map = getChartData(keyList);
        double[] value = (double[]) map.get("value");
        String[] names = (String[]) map.get("name");
        values.add(value);
        int[] colors = new int[]{getMyColor(R.color.chart9), getMyColor(R.color.chart1), getMyColor(R.color.chart2),
                getMyColor(R.color.chart3), getMyColor(R.color.chart4), getMyColor(R.color.chart5), getMyColor(R.color.chart6),
                getMyColor(R.color.chart7), getMyColor(R.color.chart8)};
        mChartUtil.setColors(colors);
        mChartUtil.setColors(colors);
        GraphicalView graphicalView = mChartUtil.createLineChartView(new String[]{mItemInfo.name}, names, "", values);
        llChart.removeAllViews();
        llChart.addView(graphicalView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    /**
     * 画柱状图
     *
     * @param keyList
     */
    private void drawBarChart(List<KeyValueInfo> keyList) {
        List<double[]> barValues = new ArrayList<>();
        Map<String, Object> map = getChartData(keyList);
        double[] barValue = (double[]) map.get("value");
        String[] barNames = (String[]) map.get("name");
        if (map.get("value1") != null) {
            double[] barValue1 = (double[]) map.get("value1");
            barValues.add(barValue1);
        }
        if (map.get("value2") != null) {
            double[] barValue2 = (double[]) map.get("value2");
            barValues.add(barValue2);
        }
        barValues.add(barValue);
        int[] colors = new int[]{getMyColor(R.color.chart9), getMyColor(R.color.chart1), getMyColor(R.color.chart2),
                getMyColor(R.color.chart3), getMyColor(R.color.chart4), getMyColor(R.color.chart5), getMyColor(R.color.chart6),
                getMyColor(R.color.chart7), getMyColor(R.color.chart8)};
        mChartUtil.setColors(colors);
        String[] titles = new String[]{mItemInfo.name};
        if (mItemInfo.name.equals("特种设备隐患")) {
            titles = new String[]{"安全附件", "安保合同", "下次检修"};
        } else if (mItemInfo.name.equals("药品检查") || mItemInfo.name.equals("工业产品检查")) {
            titles = new String[]{"检查数", "通过数"};
        } else if (mItemInfo.name.equals("化妆品")) {
            titles = new String[]{"美容", "美发"};
        }
        GraphicalView barView = mChartUtil.createBarChartView(titles, barNames, "", barValues);
        llChart.removeAllViews();
        llChart.addView(barView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    /**
     * 获取数据map
     *
     * @param keyList
     * @return
     */
    private Map<String, Object> getChartData(List<KeyValueInfo> keyList) {
        Map<String, Object> map = new HashMap<>();
        int size = 0;
        for (int i = 0; i < keyList.size(); i++) {
            KeyValueInfo key = keyList.get(i);
            if (!"合计".equals(key.key) && !"总数".equals(key.key) && !"总计".equals(key.key)) {
                size++;
            }
        }
        double[] value = new double[size];
        double[] value1 = new double[size];
        double[] value2 = new double[size];
        String[] name = new String[size];
        for (int i = 0; i < keyList.size(); i++) {
            KeyValueInfo key = keyList.get(i);
            if (!"合计".equals(key.key) && !"总数".equals(key.key) && !"总计".equals(key.key)) {
                value[i] = DigitUtil.StringToDouble(key.value);
                name[i] = key.key;
                value1[i] = DigitUtil.StringToDouble(key.value1);
                value2[i] = DigitUtil.StringToDouble(key.value2);
            }
        }
        map.put("value", value);
        map.put("name", name);
        map.put("value1", keyList.get(0).value1 == null ? null : value1);
        map.put("value2", keyList.get(0).value2 == null ? null : value2);
        return map;
    }

    /**
     * 获取颜色
     *
     * @param id
     * @return
     */
    public int getMyColor(int id) {
        return ContextCompat.getColor(mContext, id);
    }

}
