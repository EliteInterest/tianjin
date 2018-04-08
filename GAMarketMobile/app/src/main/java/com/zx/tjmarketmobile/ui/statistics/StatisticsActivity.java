package com.zx.tjmarketmobile.ui.statistics;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.R.color;
import com.zx.tjmarketmobile.R.id;
import com.zx.tjmarketmobile.entity.DeviceSecurityRiskEntity;
import com.zx.tjmarketmobile.entity.KeyValueInfo;
import com.zx.tjmarketmobile.entity.QualitySampleEntity;
import com.zx.tjmarketmobile.entity.SecurityflawEntity;
import com.zx.tjmarketmobile.entity.StatisticsItemInfo;
import com.zx.tjmarketmobile.entity.StatisticsNum;
import com.zx.tjmarketmobile.http.ApiData;
import com.zx.tjmarketmobile.http.BaseHttpResult;
import com.zx.tjmarketmobile.listener.ICommonListener.IOnSingleSelectListener;
import com.zx.tjmarketmobile.ui.base.BaseActivity;
import com.zx.tjmarketmobile.util.ChartUtil;
import com.zx.tjmarketmobile.util.DigitUtil;
import com.zx.tjmarketmobile.util.DrawChartUtil;
import com.zx.tjmarketmobile.util.Util;
import com.zx.tjmarketmobile.view.AddressSelectPopuView;

import org.achartengine.GraphicalView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create By Xiangb On 2016/9/23
 * 功能：统计分析界面
 */
public class StatisticsActivity extends BaseActivity implements OnClickListener {

    private ChartUtil mChartUtil;
    private ApiData singleData = new ApiData(ApiData.HTTP_ID_statistics_single_parameter);
    private ApiData fourData = new ApiData(ApiData.HTTP_ID_statistics_four_parameter);
    private ApiData securityFlawData = new ApiData(ApiData.HTTP_ID_security_flaws_parameter);
    private ApiData drugSamplingData = new ApiData(ApiData.HTTP_ID_drug_sample_parameter);
    private ApiData industrialProductData = new ApiData(ApiData.HTTP_ID_industrial_product_parameter);
    private ApiData countEntityType = new ApiData(ApiData.HTTP_ID_countEntityType);
    private ApiData deviceSecurityRiskData = new ApiData(ApiData.HTTP_ID_device_security_risk_parameter);
    private LinearLayout mLlChart;
    private LinearLayout mLlTable, mTimeSelectedLayout;
    private LinearLayout mQueryByTypeSpinnerLayout;//按类型选择日期的线性布局
    private StatisticsItemInfo mItemInfo;
    private TextView mTvName;
    private TextView mTvFirst;
    private TextView mTvSecond;
    private TextView mTvThree;
    private TextView mTvYear;
    private TextView mTvStartTime, mTvTimeContent, mTvQueryRiskByTime;
    private TextView mTvEndTime, mTvQueryByTime;
    private TextView mTvReback;
    private String mStartTime = DrawChartUtil.getDate(true, false), mEndTime = DrawChartUtil.getDate(false, true), mType = "";
    private Spinner mQueryTypeSpinner;

    private enum mQueryType {
        year,
        month
    }

    private AddressSelectPopuView mSelectWindow;
    private List<String> tableCodes = new ArrayList<>();
    private String[] chartTitleFieldArr;
    private short mShowColumNum = 2;
    private boolean mIsLongName = false;
    private HashMap<String, Object> objectHashMap = new HashMap<>();

    public ApiData getCurrentApiData() {
        return currentApiData;
    }

    public void setCurrentApiData(ApiData currentApiData) {
        this.currentApiData = currentApiData;
    }

    private ApiData currentApiData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        addToolBar(true);
        hideRightImg();

        mChartUtil = new ChartUtil(mContext);
        singleData.setLoadingListener(this);
        fourData.setLoadingListener(this);
        countEntityType.setLoadingListener(this);
        securityFlawData.setLoadingListener(this);
        drugSamplingData.setLoadingListener(this);
        industrialProductData.setLoadingListener(this);
        deviceSecurityRiskData.setLoadingListener(this);
        mItemInfo = (StatisticsItemInfo) getIntent().getSerializableExtra("task");
        mLlChart = (LinearLayout) findViewById(id.llActStatistics_chart);
        mLlTable = (LinearLayout) findViewById(id.llActStatistics_table);
        mTvName = (TextView) findViewById(id.tvActStatistics_name);
        mTvFirst = (TextView) findViewById(id.tvActStatistics_first);
        mTvSecond = (TextView) findViewById(id.tvActStatistics_second);
        mTvThree = (TextView) findViewById(id.tvActStatistics_three);
        mTvYear = (TextView) findViewById(id.tvActStatistics_year);
        mTvStartTime = (TextView) findViewById(id.tvActStatistics_starttime);
        mTvEndTime = (TextView) findViewById(id.tvActStatistics_endtime);
        mTvQueryByTime = (TextView) findViewById(id.tvActStatistics_query);
        mTvReback = (TextView) findViewById(id.tv_reback);
        mTimeSelectedLayout = (LinearLayout) findViewById(id.time_select_layout);
        mQueryByTypeSpinnerLayout = (LinearLayout) findViewById(R.id.time_type_select_layout);
        mTvTimeContent = (TextView) findViewById(id.tv_statistics_content_time);
        mTvQueryRiskByTime = (TextView) findViewById(R.id.tv_statistics_query);
        setMidText(mItemInfo.name);
        updateTableTitle();
        mTvReback.setOnClickListener(this);
        mTvYear.setOnClickListener(this);
        mTvStartTime.setOnClickListener(this);
        mTvEndTime.setOnClickListener(this);
        mTvQueryByTime.setOnClickListener(this);
        mTvTimeContent.setOnClickListener(this);
        mTvQueryRiskByTime.setOnClickListener(this);
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
                tv.setTextColor(ContextCompat.getColor(mContext, color.darkgrey));    //设置颜色
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
        initData();
        Object[] views = new Object[]{mChartUtil, mLlChart, mLlTable, chartTitleFieldArr};
        if (String.valueOf(getCurrentApiData().getId()) != null) {
            if (!objectHashMap.containsKey(String.valueOf(getCurrentApiData().getId())))
                objectHashMap.put(String.valueOf(getCurrentApiData().getId()), views);
        }
    }

    private void initData() {
        if (mItemInfo.dataType == 0) {
            mTvThree.setVisibility(View.GONE);
            if ("隐患情况".equals(mItemInfo.name)
                    || "排除情况".equals(mItemInfo.name)) {
                securityFlawData.loadData();
                setCurrentApiData(securityFlawData);
            } else if ("药品检查".equals(mItemInfo.name)) {
                mTvThree.setVisibility(View.VISIBLE);
                mTimeSelectedLayout.setVisibility(View.VISIBLE);
                String start = DrawChartUtil.getDate(true, false);
                drugSamplingData.loadData(start);
                setCurrentApiData(drugSamplingData);
            } else if ("工业产品检查".equals(mItemInfo.name)) {
                mTvThree.setVisibility(View.VISIBLE);
                mTimeSelectedLayout.setVisibility(View.VISIBLE);
                String start = DrawChartUtil.getDate(true, false);
                industrialProductData.loadData(start);
                setCurrentApiData(industrialProductData);
            } else if ("主体类别".equals(mItemInfo.name)) {
                countEntityType.loadData();
                setCurrentApiData(countEntityType);
                tableCodes.add("");
            } else if ("特种设备隐患".equals(mItemInfo.name)) {
                mTvThree.setVisibility(View.VISIBLE);
                mQueryByTypeSpinnerLayout.setVisibility(View.VISIBLE);
                deviceSecurityRiskData.loadData(mItemInfo.name, mStartTime, mEndTime, mType);
                setCurrentApiData(deviceSecurityRiskData);
            } else {
                singleData.loadData(mItemInfo.name);
                setCurrentApiData(singleData);
            }
        } else if (mItemInfo.dataType == 1) {
            fourData.loadData(mItemInfo.name);
            setCurrentApiData(fourData);
        } else if (mItemInfo.dataType == 2) {
            if (mItemInfo.name.equals("行业分类")) {
                singleData.loadData(mItemInfo.name, "");
                setCurrentApiData(singleData);
                tableCodes.add("");
            } else {
                mTvThree.setVisibility(View.GONE);
                String year = mTvYear.getText().toString();
                singleData.loadData(mItemInfo.name, year);
                setCurrentApiData(singleData);
            }
        } else if (mItemInfo.dataType == 3) {
            mTvThree.setVisibility(View.GONE);
            String start = DrawChartUtil.getDate(true, false);
            String end = DrawChartUtil.getDate(false, false);
            mTvStartTime.setText(start);
            mTvEndTime.setText(end);
            singleData.loadData(mItemInfo.name, start.replace("-", ""), end.replace("-", ""));
            setCurrentApiData(singleData);
        }

    }


    private void updateTableTitle() {
        if ("主体数量".equals(mItemInfo.name)
                || "隐患情况".equals(mItemInfo.name)
                || "排除情况".equals(mItemInfo.name)
                || "锅炉".equals(mItemInfo.name)
                || "起重机械".equals(mItemInfo.name)
                || "计量认证".equals(mItemInfo.name)
                || "计量授权".equals(mItemInfo.name)
                || "特种设备".equals(mItemInfo.name)
                || "工业生产".equals(mItemInfo.name)
                || "保健品销售".equals(mItemInfo.name)
                || "许可隐患".equals(mItemInfo.name)
                || "案件分布".equals(mItemInfo.name)
                || "食品经营".equals(mItemInfo.name)
                || "食品生产".equals(mItemInfo.name)
                || "餐饮服务".equals(mItemInfo.name)
                || "食品检查".equals(mItemInfo.name)
                || "药品生产".equals(mItemInfo.name)
                || "药品经营".equals(mItemInfo.name)
                || "器械经营".equals(mItemInfo.name)
                || "器械生产".equals(mItemInfo.name)
                || "区域分布".equals(mItemInfo.name)) {
            mTvName.setText("区域");
            mTvFirst.setText("数量");
            mTvSecond.setText("所占比重");
            mShowColumNum = 3;
            mTvThree.setVisibility(View.GONE);
        } else if ("举报来源".equals(mItemInfo.name)
                || "案件来源".equals(mItemInfo.name)) {
            mTvName.setText("来源");
            mTvFirst.setText("数量");
            mTvSecond.setText("所占比重");
            mShowColumNum = 3;
            mTvThree.setVisibility(View.GONE);
        } else if ("主体类别".equals(mItemInfo.name)
                || "食品".equals(mItemInfo.name)
                || "药品".equals(mItemInfo.name)
                || "器械".equals(mItemInfo.name)
                || "违法类型".equals(mItemInfo.name)
                || "药品检查".equals(mItemInfo.name)
                || "举报类型".equals(mItemInfo.name)
                || "结案统计".equals(mItemInfo.name)) {
            mTvName.setText("类别");
            mTvFirst.setText("数量");
            mTvSecond.setText("所占比重");
            mShowColumNum = 3;
            mTvThree.setVisibility(View.GONE);
        } else if ("行业分类".equals(mItemInfo.name)) {
            mTvName.setText("主体行业");
            mTvFirst.setText("个数");
            mShowColumNum = 3;
            mTvThree.setVisibility(View.GONE);
            mIsLongName = true;
            mTvName.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 2.0f));
        } else if ("设备结构".equals(mItemInfo.name)) {
            mTvName.setText("设备类别");
            mTvFirst.setText("数量");
            mShowColumNum = 3;
            mTvThree.setVisibility(View.GONE);
        } else if ("压力容器".equals(mItemInfo.name)
                || "电梯数量".equals(mItemInfo.name)) {
            mTvName.setText("区域");
            mTvFirst.setText("数量");
            mShowColumNum = 3;
            mTvThree.setVisibility(View.GONE);
        } else if ("药品检查".equals(mItemInfo.name)
                || "工业产品检查".equals(mItemInfo.name)) {
            mTvName.setText("类别");
            mTvFirst.setText("抽检数");
            mTvSecond.setText("合格数");
            mTvThree.setText("合格率");
            mShowColumNum = 4;
        } else if ("特种设备隐患".equals(mItemInfo.name)) {
            mTvName.setText("区域");
            mTvFirst.setText("安全附件");
            mTvSecond.setText("安保合同");
            mTvThree.setText("下次检修");
            mShowColumNum = 4;
            mTvThree.setVisibility(View.VISIBLE);
        } else if ("名牌产品".equals(mItemInfo.name)
                || "商标数量".equals(mItemInfo.name)) {
            mTvName.setText("类型");
            mTvFirst.setText("数量");
            mTvSecond.setText("所占比重");
            mShowColumNum = 3;
            mTvThree.setVisibility(View.GONE);
        }
        chartTitleFieldArr = new String[]{mTvFirst.getText().toString(),
                mTvSecond.getText().toString(), mTvThree.getText().toString()};
    }

    @SuppressWarnings({"unused", "unchecked", "rawtypes"})
    public void onLoadComplete(int id, BaseHttpResult b) {
        super.onLoadComplete(id, b);
        Object[] objects = null;
        if (b.isSuccess()) {
            switch (id) {
                case ApiData.HTTP_ID_countEntityType:
                case ApiData.HTTP_ID_statistics_single_parameter:
                    List<KeyValueInfo> keyList = (List<KeyValueInfo>) b.getEntry();
                    AllDataCallBackDrawChart(keyList);
                    break;
                case ApiData.HTTP_ID_statistics_four_parameter:
                    objects = (Object[]) objectHashMap.get(String.valueOf(ApiData.HTTP_ID_statistics_four_parameter));
                    List<StatisticsNum> numList = (List<StatisticsNum>) b.getEntry();
                    switch (mItemInfo.chartType) {
                        case 0:
                            // 柱状图
                            DrawChartUtil.drawMoreBarChart(StatisticsActivity.this, objects, numList, 4);
                            break;
                        case 1:
                            // 折线图
                            drawFourLineChart(objects, numList);
                            break;
                        default:
                            break;
                    }
                    break;
                case ApiData.HTTP_ID_security_flaws_parameter://安全隐患回调
                    List<SecurityflawEntity> securityflawEntityList = (List<SecurityflawEntity>) b.getEntry();
                    switch (mItemInfo.chartType) {
                        case 0:// 柱状图
                            List<KeyValueInfo> keyValueList = new ArrayList<>();
                            if (securityflawEntityList != null && securityflawEntityList.size() > 0) {
                                for (int i = 0; i < securityflawEntityList.size(); i++) {
                                    KeyValueInfo keyValueInfo = new KeyValueInfo();
                                    keyValueInfo.key = securityflawEntityList.get(i).getName();
                                    if ("隐患情况".equals(mItemInfo.name)) {
                                        keyValueInfo.value = securityflawEntityList.get(i).getNum();
                                    } else {
                                        keyValueInfo.value = securityflawEntityList.get(i).getClear();
                                    }
                                    keyValueList.add(keyValueInfo);
                                }
                            }
                            drawSingleBarChart(keyValueList);
                            break;
                        case 1:// 折线图

                            break;
                        default:
                            break;
                    }
                    break;
                case ApiData.HTTP_ID_drug_sample_parameter://药品检验
                    List<QualitySampleEntity> qualitySampleEntityList = (List<QualitySampleEntity>) b.getEntry();
                    objects = (Object[]) objectHashMap.get(String.valueOf(ApiData.HTTP_ID_drug_sample_parameter));
                    DrawChartUtil.doQualitySampleCallBackEvent(StatisticsActivity.this, objects, mItemInfo.chartType, qualitySampleEntityList, 4);
                    break;
                case ApiData.HTTP_ID_industrial_product_parameter://工业产品检验
                    List<QualitySampleEntity> qualityProductSampleEntityList = (List<QualitySampleEntity>) b.getEntry();
                    objects = (Object[]) objectHashMap.get(String.valueOf(ApiData.HTTP_ID_industrial_product_parameter));
                    DrawChartUtil.doQualitySampleCallBackEvent(StatisticsActivity.this, objects, mItemInfo.chartType, qualityProductSampleEntityList, 4);
                    break;
                case ApiData.HTTP_ID_device_security_risk_parameter://特种设备隐患
                    List<DeviceSecurityRiskEntity> deviceSecurityRiskEntityList = (List<DeviceSecurityRiskEntity>) b.getEntry();
                    drawDeviceSecurityRiskChart(deviceSecurityRiskEntityList);
                    break;
                default:
                    break;
            }
        } else {
            showToast("系统异常，请稍后再试");
        }
    }

    private void drawDeviceSecurityRiskChart(List<DeviceSecurityRiskEntity> keyList) {
        List<StatisticsNum> statisticsNumArrayList = new ArrayList<>();
        if (keyList != null && keyList.size() > 0) {
            for (int i = 0; i < keyList.size(); i++) {
                StatisticsNum statisticsNum = new StatisticsNum();
                statisticsNum.name = keyList.get(i).getType();
                statisticsNum.first = Integer.valueOf(keyList.get(i).getSecurityFile());
                statisticsNum.second = Integer.valueOf(keyList.get(i).getSecurityContract());
                statisticsNum.three = Integer.valueOf(keyList.get(i).getNextOverhaul());
                statisticsNumArrayList.add(statisticsNum);
            }
        }
        Object[] objects = (Object[]) objectHashMap.get(String.valueOf(ApiData.HTTP_ID_device_security_risk_parameter));
        DrawChartUtil.drawMoreBarChart(this, objects, statisticsNumArrayList, 4);
    }

    private void AllDataCallBackDrawChart(List<KeyValueInfo> keyList) {
        switch (mItemInfo.chartType) {
            case 0:
                // 柱状图
                drawSingleBarChart(keyList);
                break;
            case 4://可点击柱状图
                if (keyList.size() == 0) {
                    showToast("没有更多");
                    tableCodes.remove(tableCodes.size() - 1);
                    return;
                }
                // 柱状图
                drawSingleBarChart(keyList);
                break;
            case 5://可点击饼状图
                if (keyList.size() == 0) {
                    showToast("没有更多");
                    tableCodes.remove(tableCodes.size() - 1);
                    return;
                }
                // 饼状图
                addSingleView(keyList);
                for (int i = 0; i < keyList.size(); i++) {
                    KeyValueInfo keyValueInfo = keyList.get(i);
                    if ("合计".equals(keyValueInfo.key) || "总数".equals(keyValueInfo.key)
                            || "0".equals(keyValueInfo.value) || "总计".equals(keyValueInfo.key)) {
                        keyList.remove(i);
                    }
                }
                GraphicalView category = mChartUtil.createCategoryView("", "", keyList);
                mLlChart.removeAllViews();
                mLlChart.addView(category, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                break;
            case 1:
                // 折线图
                drawSingleLineChart(keyList);
                break;
            case 2:
                // 饼状图
                addSingleView(keyList);
                List<KeyValueInfo> tempList = new ArrayList<>();
                for (int i = 0; i < keyList.size(); i++) {
                    KeyValueInfo keyValueInfo = keyList.get(i);
                    if ("合计".equals(keyValueInfo.key) || "总数".equals(keyValueInfo.key)
                            || "0".equals(keyValueInfo.value) || "总计".equals(keyValueInfo.key)) {
//                        keyList.remove(i);
                    }else {
                        tempList.add(keyList.get(i));
                    }
                }
//                if ("餐饮结构".equals(mItemInfo.name)) {
                    int[] colors = new int[]{getMyColor(color.chart8), getMyColor(color.chart7),
                            getMyColor(color.chart6), getMyColor(color.chart4), getMyColor(color.chart5),
                            getMyColor(color.chart3), getMyColor(color.chart2), getMyColor(color.chart1)};
                    mChartUtil.setColors(colors);
//                }
                GraphicalView categoryView = mChartUtil.createCategoryView("", "", tempList);
                mLlChart.removeAllViews();
                mLlChart.addView(categoryView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                break;
            default:
                break;
        }
    }


    private void drawSingleBarChart(List<KeyValueInfo> keyList) {
        List<double[]> barValues = new ArrayList<double[]>();
        Map<String, Object> map = getChartData(keyList);
        double[] barValue = (double[]) map.get("value");
        String[] barNames = (String[]) map.get("name");
        barValues.add(barValue);
        int[] colors = new int[]{getMyColor(color.chart9), getMyColor(color.chart1), getMyColor(color.chart2),
                getMyColor(color.chart3), getMyColor(color.chart4), getMyColor(color.chart5), getMyColor(color.chart6),
                getMyColor(color.chart7), getMyColor(color.chart8)};
        mChartUtil.setColors(colors);
        GraphicalView barView = mChartUtil.createBarChartView(new String[]{mItemInfo.name}, barNames, "", barValues);
        mLlChart.removeAllViews();
        mLlChart.addView(barView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addSingleView(keyList);
    }

    private Map<String, Object> getChartData(List<KeyValueInfo> keyList) {
        Map<String, Object> map = new HashMap<String, Object>();
        int size = 0;
        for (int i = 0; i < keyList.size(); i++) {
            KeyValueInfo key = keyList.get(i);
            if (!"合计".equals(key.key) && !"总数".equals(key.key) && !"总计".equals(key.key)) {
                size++;
            }
        }
        double[] value = new double[size];
        String[] name = new String[size];
        for (int i = 0; i < keyList.size(); i++) {
            KeyValueInfo key = keyList.get(i);
            if (!"合计".equals(key.key) && !"总数".equals(key.key) && !"总计".equals(key.key)) {
                value[i] = DigitUtil.StringToDouble(key.value);
                name[i] = key.key;
            }
        }
        map.put("value", value);
        map.put("name", name);
        return map;
    }

    private void drawSingleLineChart(List<KeyValueInfo> keyList) {
        List<double[]> values = new ArrayList<>();
        Map<String, Object> map = getChartData(keyList);
        double[] value = (double[]) map.get("value");
        String[] names = (String[]) map.get("name");
        values.add(value);
        int[] colors = new int[]{getMyColor(color.chart9), getMyColor(color.chart1), getMyColor(color.chart2),
                getMyColor(color.chart3), getMyColor(color.chart4), getMyColor(color.chart5), getMyColor(color.chart6),
                getMyColor(color.chart7), getMyColor(color.chart8)};
        mChartUtil.setColors(colors);
        mChartUtil.setColors(colors);
        GraphicalView graphicalView = mChartUtil.createLineChartView(new String[]{mItemInfo.name}, names, "", values);
        mLlChart.removeAllViews();
        mLlChart.addView(graphicalView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addSingleView(keyList);
    }

    private void drawFourLineChart(Object[] objects, List<StatisticsNum> numList) {
        List<double[]> values = new ArrayList<double[]>();
        double[] value0 = new double[numList.size()];
        double[] value1 = new double[numList.size()];
        double[] value2 = new double[numList.size()];
        String[] names = new String[numList.size()];
        for (int i = 0; i < numList.size(); i++) {
            StatisticsNum statisticsNum = numList.get(i);
            value0[i] = statisticsNum.first;
            value1[i] = statisticsNum.second;
            value2[i] = statisticsNum.three;
            names[i] = statisticsNum.name;
        }
        values.add(value0);
        values.add(value1);
        values.add(value2);
        int[] colors = new int[]{getMyColor(color.chart9), getMyColor(color.chart1), getMyColor(color.chart2),
                getMyColor(color.chart3), getMyColor(color.chart4), getMyColor(color.chart5), getMyColor(color.chart6),
                getMyColor(color.chart7), getMyColor(color.chart8)};
        mChartUtil.setColors(colors);
        GraphicalView graphicalView = mChartUtil.createLineChartView(new String[]{mTvFirst.getText().toString(),
                mTvSecond.getText().toString(), mTvThree.getText().toString()}, names, "", values);
        mLlChart.removeAllViews();
        mLlChart.addView(graphicalView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        DrawChartUtil.addFourView(mContext, objects, numList, 4);
    }


    private void addSingleView(List<KeyValueInfo> keyList) {
        mLlTable.removeAllViews();
        if (null != keyList && !keyList.isEmpty()) {
            double sum = 0;
            for (KeyValueInfo keyValueInfo : keyList) {
                if (!"合计".equals(keyValueInfo.key) && !"总数".equals(keyValueInfo.key) && !"总计".equals(keyValueInfo.key)) {
                    double value = DigitUtil.StringToDouble(keyValueInfo.value);
                    sum += value;
                }
            }
            if (sum == 0) {
                sum = 1;
            }
            DecimalFormat df = new DecimalFormat("######0.00");
            for (final KeyValueInfo keyValueInfo : keyList) {
                double value = DigitUtil.StringToDouble(keyValueInfo.value);
                View view = View.inflate(mContext, R.layout.item_table, null);
                final TextView tvName = (TextView) view.findViewById(id.tvItemTable_name);
                TextView tvFirst = (TextView) view.findViewById(id.tvItemTable_first);
                tvName.setText(keyValueInfo.key);
                tvFirst.setText(keyValueInfo.value);
                tvName.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemInfo.chartType == 4) {
                            tableCodes.add(keyValueInfo.code);
                            singleData.loadData(mItemInfo.name, keyValueInfo.code);
                        } else if (mItemInfo.chartType == 5) {
                            if (tvName.getText().equals("企业")) {
                                tableCodes.add(keyValueInfo.code);
                                singleData.loadData(mItemInfo.name, keyValueInfo.code);
                            }
                        } else {
                        }
                    }
                });
                switch (mShowColumNum) {
                    case 3:
                        TextView tvSecond = (TextView) view.findViewById(id.tvItemTable_second);
                        tvSecond.setVisibility(View.VISIBLE);
                        if (value == 0 || sum == 0) {
                            tvSecond.setText(0 + "%");
                        } else {
                            tvSecond.setText(df.format(value * 100 / sum) + "%");
                        }
                        break;
                    default:
                        break;
                }
                if (mIsLongName) {
                    tvName.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 2.0f));
                }
                mLlTable.addView(view);
                if (tableCodes.size() > 1) {
                    mTvReback.setVisibility(View.VISIBLE);
                } else {
                    mTvReback.setVisibility(View.GONE);
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.tvActStatistics_year:
                showSelectWindow(mTvYear);
                break;
            case id.tv_reback:
                tableCodes.remove(tableCodes.size() - 1);
                if (mItemInfo.chartType == 4) {
                    singleData.loadData(mItemInfo.name, tableCodes.get(tableCodes.size() - 1));
                } else {
                    countEntityType.loadData();
                }
                break;
            case id.tvActStatistics_starttime:
                Util.setDateIntoText(StatisticsActivity.this, v);
                break;
            case id.tvActStatistics_endtime:
                Util.setDateIntoText(StatisticsActivity.this, v);
                break;
            case id.tvActStatistics_query:
                String startTime = mTvStartTime.getText().toString();
                String endTime = mTvEndTime.getText().toString();
                if (!startTime.equals("开始时间")) {
                    if (!endTime.equals("结束时间")) {
                        boolean isRight = Util.compareTime(startTime, endTime);
                        if (isRight) {
                            initData();
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
            case id.tv_statistics_content_time:
                Util.setDateYearIntoText(StatisticsActivity.this, v, mType);
                break;
            case id.tv_statistics_query:
                initData();
                break;
            default:
                break;
        }
    }

    IOnSingleSelectListener selectListener = new IOnSingleSelectListener() {
        @Override
        public void onSelect(String flag, String data) {
            mTvYear.setText(data);
//            singleData.loadData(mItemInfo.name, data);
        }
    };

    private void showSelectWindow(TextView view) {
        if (mSelectWindow == null) {
            mSelectWindow = new AddressSelectPopuView(this);
            mSelectWindow.setDataSelectListener(selectListener);
        }
        List<String> allList = new ArrayList<String>();
        allList.add("2016");
        allList.add("2015");
        allList.add("2014");
        mSelectWindow.show(view, allList);
    }

    public int getMyColor(int id) {
        return ContextCompat.getColor(mContext, id);
    }

}
