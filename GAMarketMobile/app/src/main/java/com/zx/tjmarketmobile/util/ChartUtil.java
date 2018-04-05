package com.zx.tjmarketmobile.util;

import android.content.Context;
import android.graphics.Paint.Align;
import android.support.v4.content.ContextCompat;

import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.entity.KeyValueInfo;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;

public class ChartUtil {

    private Context mContext;
    private int[] mColors;

    public ChartUtil(Context c) {
        mContext = c;
        mColors = new int[]{getColor(R.color.chart1), getColor(R.color.chart2), getColor(R.color.chart3),
                getColor(R.color.chart4), getColor(R.color.chart5), getColor(R.color.chart6), getColor(R.color.chart7),
                getColor(R.color.chart8)};
    }

    public void setColors(int[] mColors) {
        this.mColors = mColors;
    }

    private int getColor(int id) {
        return ContextCompat.getColor(mContext, id);
    }

    public GraphicalView createCategoryView(String title, String yLabel, List<KeyValueInfo> values) {
        CategorySeries dataset = buildCategoryDataset(title, values);
        DefaultRenderer renderer = buildCategoryRenderer(title, values.size());
        GraphicalView graphicalView = ChartFactory.getPieChartView(mContext, dataset, renderer);
        return graphicalView;
    }

    public GraphicalView createBarChartView(String[] titles, String[] xlabels, String yLabel, List<double[]> values) {
        XYMultipleSeriesDataset dataset = buildBarDataset(titles, values);
        XYMultipleSeriesRenderer renderer = buildBarchartRenderer(xlabels, values);
        GraphicalView graphicalView = ChartFactory.getBarChartView(mContext, dataset, renderer, Type.DEFAULT);
        return graphicalView;
    }

    public GraphicalView createLineChartView(String[] titles, String[] xValues, String yLabel, List<double[]> values) {
        XYMultipleSeriesDataset dataset = buildLineDataset(titles, values);
        XYMultipleSeriesRenderer renderer = createLineRender(xValues, yLabel, values);
        GraphicalView graphicalView = ChartFactory.getLineChartView(mContext, dataset, renderer);
        return graphicalView;
    }

    private CategorySeries buildCategoryDataset(String title, List<KeyValueInfo> values) {
        CategorySeries series = new CategorySeries(title);
        for (KeyValueInfo keyValueInfo : values) {
            series.add(keyValueInfo.key, Double.parseDouble(keyValueInfo.value));
        }
        return series;
    }

    private DefaultRenderer buildCategoryRenderer(String title, int size) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLegendTextSize(20);// 设置左下角表注的文字大小
        // renderer.setZoomButtonsVisible(true);//设置显示放大缩小按钮
        renderer.setZoomEnabled(true);// 设置不允许放大缩小.
        renderer.setChartTitle(title);// 设置图表的标题 默认是居中顶部显示
        renderer.setLabelsTextSize(20);// 饼图上标记文字的字体大小
        renderer.setLabelsColor(getColor(R.color.darkgrey));// 饼图上标记文字的颜色
        renderer.setPanEnabled(true);// 设置是否可以平移
        // renderer.getSeriesRendererAt(0).setDisplayChartValues(true) ;
        // renderer.setDisplayValues(true);// 是否显示值
        renderer.setClickEnabled(true);// 设置是否可以被点击
        renderer.setBackgroundColor(getColor(R.color.home_radio_off));
        for (int i = 0; i < size; i++) {
            int color = i > mColors.length - 1 ? mColors[((int) (Math.random() * (mColors.length - 2))) + 1] : mColors[i];
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    private XYMultipleSeriesDataset buildBarDataset(String[] titles, List<double[]> values) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        for (int i = 0; i < values.size(); i++) {
            CategorySeries series = new CategorySeries(titles[i]);
            double[] v = values.get(i);
            int seriesLength = v.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(v[k]);
            }
            dataset.addSeries(series.toXYSeries());
        }
        return dataset;
    }

    private double getMaxValue(List<double[]> values) {
        double max = 0;
        double sum = 0;
        int index = 0;
        for (double[] value : values) {
            for (int i = 0; i < value.length; i++) {
                sum += value[i];
                index++;
                if (max < value[i]) {
                    max = value[i];
                }
            }
        }
        return max + sum / index;
    }

    private XYMultipleSeriesRenderer buildBarchartRenderer(String[] xLabels, List<double[]> values) {
        XYMultipleSeriesRenderer renderer = buildBarRenderer(values.size());
        renderer.setZoomEnabled(true, false);
        renderer.setPanEnabled(true, false);// 设置是否允许平移
        setChartSettings(renderer, "", "", "", 0, values.get(0).length + 1, 0, getMaxValue(values));
        for (int i = 0; i < renderer.getSeriesRendererCount(); i++) {
            renderer.getSeriesRendererAt(i).setDisplayChartValues(true);
        }
        renderer.setXLabels(0);// 设置x轴上的下标数量
        renderer.setYLabels(10); // 设置y轴上的下标数量
        renderer.setXLabelsPadding(5);
        renderer.setYLabelsColor(0, getColor(R.color.white));
        renderer.setXLabelsColor(getColor(R.color.white));
        renderer.setYLabelsAlign(Align.RIGHT);// y轴 数字表示在坐标还是右边
        int textLength = 0;
        for (int i = 0; i < values.get(0).length; i++) {
            if (xLabels[i].length() > textLength) {
                textLength = xLabels[i].length();
            }
            renderer.addXTextLabel(i + 1, xLabels[i]);
        }
        renderer.setBarSpacing(1);
        // renderer.setBarWidth(textLength * 20);
        renderer.setDisplayValues(true);
        renderer.setXLabelsAlign(Align.RIGHT);
        renderer.setXLabelsAngle(300f);
        renderer.setXLabelsPadding(textLength * 10);
        renderer.setShowGrid(true);
        renderer.setGridColor(getColor(R.color.gray));
        int length = values.get(0).length + 1;
        renderer.setPanLimits(new double[]{-length / 2, length * 1.5, 0, 1400});// 设置拉动的范围
        // renderer.setXRoundedLabels(true);
        renderer.setMargins(new int[]{0, 50, textLength * 10, 0});
        // renderer.setFitLegend(true);// 调整合适的位置
        return renderer;
    }

    private void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle, String Ytitle,
                                  double xMin, double xMax, double yMin, double yMax) {
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(Ytitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
    }

    private XYMultipleSeriesRenderer buildBarRenderer(int size) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(20);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(20);
        renderer.setLegendTextSize(20);
        renderer.setAxesColor(getColor(R.color.colorPrimaryDark));
        renderer.setMarginsColor(getColor(R.color.colorPrimaryDark));
        for (int i = 0; i < size; i++) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(mColors[i]);
            r.setChartValuesTextSize(20);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    private XYMultipleSeriesDataset buildLineDataset(String[] titles, List<double[]> values) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        for (int i = 0; i < values.size(); i++) {
            double[] ds = values.get(i);
            XYSeries series = new XYSeries(titles[i]);
            for (int j = 0; j < ds.length; j++) {
                series.add(j + 1, ds[j]);
            }
            dataset.addSeries(series);
        }
        return dataset;
    }

    private XYMultipleSeriesRenderer createLineRender(String[] xValues, String yLabel, List<double[]> values) {
        PointStyle[] styles = new PointStyle[values.size()];
        for (int i = 0; i < styles.length; i++) {
            styles[i] = PointStyle.CIRCLE;
        }
        XYMultipleSeriesRenderer renderer = buildLineRenderer(styles);// 调用AbstractDemoChart中的方法设置renderer.
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);// 设置图上的点为实心
        }
        int textLength = 0;
        for (int i = 0; i < xValues.length; i++) {
            if (xValues[i].length() > textLength) {
                textLength = xValues[i].length();
            }
            renderer.addXTextLabel(i + 1, xValues[i]);
        }
        renderer.setZoomEnabled(true, false);
        renderer.setPanEnabled(true, false);// 设置是否允许平移
        renderer.setAxesColor(getColor(R.color.blue));
        setChartSettings(renderer, "", "", yLabel, 0, xValues.length + 1, 0, getMaxValue(values));// 调用AbstractDemoChart中的方法设置图表的renderer属性.
        renderer.setXLabels(0);
        renderer.setPointSize(5);
        renderer.setYLabels(10);// 设置y轴显示10个点,根据setChartSettings的最大值和最小值自动计算点的间隔
        renderer.setShowGrid(true);// 是否显示网格
        renderer.setXLabelsAlign(Align.RIGHT);// 刻度线与刻度标注之间的相对位置关系
        renderer.setYLabelsAlign(Align.RIGHT);// 刻度线与刻度标注之间的相对位置关系
        renderer.setZoomButtonsVisible(true);// 是否显示放大缩小按钮
        renderer.setLabelsTextSize(20);
        renderer.setLegendTextSize(20);
        renderer.setAxisTitleTextSize(20);
        renderer.setDisplayValues(true);
        renderer.setGridColor(getColor(R.color.gray));
        renderer.setMarginsColor(getColor(R.color.colorPrimaryDark));
        renderer.setFitLegend(true);
        renderer.setXLabelsAngle(300f);
        renderer.setXLabelsPadding(5);
        renderer.setZoomButtonsVisible(false);
        textLength++;
        renderer.setMargins(new int[]{0, 60, textLength * 15, 0});
        renderer.setPanLimits(new double[]{-10, 20, -10, 40}); // 设置拖动时X轴Y轴允许的最大值最小值.
        renderer.setZoomLimits(new double[]{-10, 20, -10, 40});// 设置放大缩小时X轴Y轴允许的最大最小值.
        return renderer;
    }

    protected XYMultipleSeriesRenderer buildLineRenderer(PointStyle[] style) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        for (int i = 0; i < style.length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(mColors[i]);
            r.setPointStyle(style[i]);
            r.setFillPoints(true);
            r.setLineWidth(3);
            r.setChartValuesTextSize(20);
            r.setDisplayChartValuesDistance(25);
            r.setDisplayChartValues(true);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

}
