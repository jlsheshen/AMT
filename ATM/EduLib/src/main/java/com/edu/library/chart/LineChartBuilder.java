package com.edu.library.chart;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;

/**
 * 折线图创建类
 * 
 * @author lucher
 * 
 */
public class LineChartBuilder extends BaseChartBuilder {

	// 渲染器
	private XYMultipleSeriesRenderer mRenderer;
	// 属性数据
	private XYMultipleSeriesDataset mDataset;
	// 最大值
	private double maxValue;

	/**
	 * 构造
	 * 
	 * @param context
	 * @param xTitle
	 *            x标题
	 * @param yTitle
	 *            y标题
	 */
	public LineChartBuilder(Context context) {
		super(context);
	}

	@Override
	protected void init() {
		mRenderer = new XYMultipleSeriesRenderer();
		mDataset = new XYMultipleSeriesDataset();
		setChartSettings();
	}

	/**
	 * 设置渲染器
	 * 
	 * @param renderer
	 */
	private void setChartSettings() {
		// 设置图表的X轴的当前方向
		mRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
		mRenderer.setXTitle("x title");// 设置为X轴的标题
		mRenderer.setYTitle("y title");// 设置y轴的标题
		mRenderer.setAxisTitleTextSize(30);// 设置轴标题文本大小
		// mRenderer.setChartTitle("销售总额走势图");// 设置图表标题
		mRenderer.setChartTitleTextSize(30);// 设置图表标题文字的大小
		mRenderer.setLabelsTextSize(30);// 设置标签的文字大小
		mRenderer.setLegendTextSize(30);// 设置图例文本大小
		mRenderer.setPointSize(10f);// 设置点的大小
		// mRenderer.setYLabels(10);// 设置Y轴刻度个数（貌似不太准确）
		mRenderer.setShowGrid(true);// 显示网格
		mRenderer.setMargins(new int[] { 0, 100, 20, 0 });// 设置视图位置{t,l,b,r}
		mRenderer.setXLabels(0);// 设置只显示替换后的东西，不显示1,2,3等
		mRenderer.setPanEnabled(true, true);

		mRenderer.setXLabelsAlign(Align.RIGHT);
		mRenderer.setYLabelsAlign(Align.RIGHT);
		mRenderer.setYLabelsPadding(5);
		mRenderer.setXLabelsAngle(315);
		mRenderer.setLabelsColor(Color.RED);

		XYSeriesRenderer renderer = new XYSeriesRenderer();// (类似于一条线对象)
		renderer.setColor(Color.BLUE);// 设置颜色
		renderer.setPointStyle(PointStyle.CIRCLE);// 设置点的样式
		renderer.setFillPoints(true);// 填充点（显示的点是空心还是实心）
		renderer.setDisplayChartValues(true);// 将点的值显示出来
		renderer.setChartValuesSpacing(10);// 显示的点的值与图的距离
		renderer.setChartValuesTextSize(30);// 点的值的文字大小
		// renderer.setFillBelowLine(true);//是否填充折线图的下方
		// renderer.setFillBelowLineColor(Color.GREEN);//填充的颜色，如果不设置就默认与线的颜色一致
		renderer.setLineWidth(3);// 设置线宽
		mRenderer.addSeriesRenderer(renderer);

	}

	@Override
	public GraphicalView buildChartView(List<BaseChartData> datas) {
		// 存放日期-销售额键值对
		HashMap<String, Double> map = parseDatas(datas);
		Iterator<String> iterator = map.keySet().iterator();

		XYSeries series = new XYSeries("");
		int index = 1;
		while (iterator.hasNext()) {
			String date = iterator.next();
			Double amount = map.get(date);
			series.add(index, amount);
			mRenderer.addXTextLabel(index++, date);

			if (amount > maxValue) {
				maxValue = amount;
			}
		}
		mRenderer.setXAxisMax(5);
		mRenderer.setXAxisMin(0);
		mRenderer.setYAxisMin(0);
		mRenderer.setYAxisMax(maxValue + 100);

		mDataset.addSeries(series);
		mChartView = ChartFactory.getLineChartView(mContext, mDataset, mRenderer);
		mChartView.setBackgroundColor(Color.BLACK);
		return mChartView;
	}

	@Override
	public HashMap<String, Double> parseDatas(List<BaseChartData> datas) {
		HashMap<String, Double> map = new HashMap<String, Double>();
		if (datas != null) {
			for (BaseChartData data : datas) {
				map.put(data.getName(), data.getValue());
			}
		}
		return map;
	}

	@Override
	public XYMultipleSeriesRenderer getRenderer() {
		return mRenderer;
	}

}
