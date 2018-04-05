package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

public class StatisticsItemInfo implements Serializable {
    //	private static final long serialVersionUID = 1L;
    public String name;


    /**
     * 统计分析数据类型: 0代表柱状图 1代表折线图 2代表饼状图 3代表地图图层 4代表可点击图表（柱状图）   5代表可点击图表（饼状图）
     */
    public int chartType = -1;


    public int resId = 0;


    /**
     * 接口请求类型: 0代表一个请求参数一个返回结果，1代表一个请求参数四个返回结果 2代表两个请求参数一个返回结果,3代表三个请求参数一个返回结果
     */
    public int dataType = 0;

    public String tableTitle = "类别";

    public String[] mSubNames;

    public StatisticsItemInfo(String name, int chartType, String tableTitle, int resId) {
        this.name = name;
        this.chartType = chartType;
        this.resId = resId;
        this.tableTitle = tableTitle;
    }

    public StatisticsItemInfo(String name, int chartType, int dataType, int resId) {
        this.name = name;
        this.chartType = chartType;
        this.resId = resId;
        this.dataType = dataType;
    }

    public StatisticsItemInfo(String name, int chartType, int dataType, int resId, String[] subNames) {
        this.name = name;
        this.chartType = chartType;
        this.resId = resId;
        this.dataType = dataType;
        this.mSubNames = subNames;
    }
}
