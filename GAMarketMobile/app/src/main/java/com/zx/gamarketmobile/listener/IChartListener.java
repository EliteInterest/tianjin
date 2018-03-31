package com.zx.gamarketmobile.listener;

/**
 * Created by Xiangb on 2017/5/17.
 * 功能：
 */

public interface IChartListener {

    void OnKeyClick(int position);

    void onValueClick(String columnString, String lineString);

}
