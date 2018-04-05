package com.zx.tjmarketmobile.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.QualitySampleEntity;
import com.zx.tjmarketmobile.entity.StatisticsNum;

import org.achartengine.GraphicalView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by zhouzq on 2016/11/23.
 */
public class DrawChartUtil {


    public static void doQualitySampleCallBackEvent(Context mContext, Object[] objects,
                       int chartType, List<QualitySampleEntity> qualitySampleEntityList,int number) {
        switch (chartType) {
            case 0:// 柱状图
                List<StatisticsNum> statisticsNumArrayList = new ArrayList<>();
                DecimalFormat df = new DecimalFormat("######0.00");
                if (qualitySampleEntityList!=null&&qualitySampleEntityList.size()>0){
                    for (int i = 0; i < qualitySampleEntityList.size(); i++) {
                        StatisticsNum statisticsNum = new StatisticsNum();
                        statisticsNum.name = qualitySampleEntityList.get(i).getType();
                        statisticsNum.first = Double.valueOf(qualitySampleEntityList.get(i).getCheckoutTotal());
                        statisticsNum.second = Double.valueOf(qualitySampleEntityList.get(i).getCheckoutPass());
                        if (statisticsNum.second==0||statisticsNum.first==0){
                            statisticsNum.three = 0;
                        }else{
                            statisticsNum.three = Double.valueOf(df.format(statisticsNum.second/statisticsNum.first));
                        }
                        statisticsNumArrayList.add(statisticsNum);
                    }
                }
                drawMoreBarChart(mContext,objects,statisticsNumArrayList,number);
                break;
            case 1:// 折线图

                break;
            default:
                break;
        }
    }


    public static void drawMoreBarChart(Context mContext, Object[] objects, List<StatisticsNum> numList,int number) {
        List<double[]> barValues = new ArrayList<double[]>();
        double[] barValue0 = new double[numList.size()];
        double[] barValue1 = new double[numList.size()];
        double[] barValue2 = new double[numList.size()];
        String[] barNames = new String[numList.size()];
        for (int i = 0; i < numList.size(); i++) {
            StatisticsNum statisticsNum = numList.get(i);
            barValue0[i] = statisticsNum.first;
            barValue1[i] = statisticsNum.second;
            barValue2[i] = statisticsNum.three;
            barNames[i] = statisticsNum.name;
        }
        barValues.add(barValue0);
        barValues.add(barValue1);
        barValues.add(barValue2);
        int[] colors = new int[]{getMyColor(mContext,R.color.chart9), getMyColor(mContext,R.color.chart1), getMyColor(mContext,R.color.chart2),
                getMyColor(mContext,R.color.chart3), getMyColor(mContext,R.color.chart4), getMyColor(mContext,R.color.chart5), getMyColor(mContext,R.color.chart6),
                getMyColor(mContext,R.color.chart7), getMyColor(mContext,R.color.chart8)};
        if (objects[0]!=null&&objects[0] instanceof ChartUtil){
            ChartUtil  chartUtil = (ChartUtil)objects[0];
            chartUtil.setColors(colors);
            if (objects[3]!=null&&objects[3] instanceof String[]){
                String[]  strings = (String[])objects[3];
                GraphicalView barView = chartUtil.createBarChartView(strings, barNames, "", barValues);
                if (objects[1]!=null&&objects[1] instanceof LinearLayout){
                    LinearLayout  linearLayout = (LinearLayout)objects[1];
                    linearLayout.removeAllViews();
                    linearLayout.addView(barView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    addFourView(mContext,objects, numList,number);
                }
            }

        }

    }

    public static void addFourView(Context mContext, Object[] objects, List<StatisticsNum> keyList,int number) {
        if (objects[2]!=null&&objects[2] instanceof LinearLayout){
            LinearLayout mLlTable = (LinearLayout)objects[2];
            mLlTable.removeAllViews();
            if (null != keyList && !keyList.isEmpty()) {
                for (StatisticsNum keyValueInfo : keyList) {
                    View view = View.inflate(mContext, R.layout.item_table, null);
                    TextView tvName = (TextView) view.findViewById(R.id.tvItemTable_name);
                    TextView tvFirst = (TextView) view.findViewById(R.id.tvItemTable_first);
                    TextView tvSecond = (TextView) view.findViewById(R.id.tvItemTable_second);
                    View secondLine = (View) view.findViewById(R.id.tvItemTable_second_right_line);
                    TextView tvThree = (TextView) view.findViewById(R.id.tvItemTable_three);
                    tvThree.setVisibility(View.VISIBLE);
                    tvName.setText(keyValueInfo.name);
                    if (keyValueInfo.first==(int)keyValueInfo.first){
                        tvFirst.setText((int)keyValueInfo.first + "");
                    }else{
                        tvFirst.setText(keyValueInfo.first + "");
                    }
                    if (keyValueInfo.second==(int)keyValueInfo.second){
                        tvSecond.setText((int)keyValueInfo.second + "");
                    }else{
                        tvSecond.setText(keyValueInfo.second + "");
                    }
                    if(number==3){
                        tvSecond.setVisibility(View.GONE);
                        secondLine.setVisibility(View.GONE);
                    }
                    if (keyValueInfo.three==(int)keyValueInfo.three){
                        tvThree.setText((int)keyValueInfo.three + "");
                    }else{
                        tvThree.setText(keyValueInfo.three + "");
                    }
                    mLlTable.addView(view);
                }
            }
        }
    }



    public static int getMyColor(Context mContext,int id) {
        return ContextCompat.getColor(mContext, id);
    }


    public static String getDate(boolean isFirstDay,boolean isLastDay) {
        String date = "";
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        date += year;
        date += "-";
        if (month < 10) {
            date += "0" + month;
        } else {
            date += month;
        }
        date += "-";
        if (isFirstDay) {
            date += "01";
        } else {
            if (day < 10) {
                date += "0" + day;
            } else {
                if (isLastDay){
                    c.set(Calendar.DATE, 1);
                    c.roll(Calendar.DATE, -1);
                    int maxDate = c.get(Calendar.DATE);
                    date += maxDate;
                }else{
                    date += day;
                }
            }
        }
        return date;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     * */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

}
