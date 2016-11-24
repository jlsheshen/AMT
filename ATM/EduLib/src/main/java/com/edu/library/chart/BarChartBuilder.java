package com.edu.library.chart;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;

/**
 * 条形图创建类
 * 
 * @author lucher
 * 
 */
public class BarChartBuilder extends BaseChartBuilder {

	// 渲染器
	private XYMultipleSeriesRenderer mRenderer;
	// 属性数据
	private XYMultipleSeriesDataset mDataSet;

	// 最大属性
	private double maxValue;

	/**
	 * 构造
	 * 
	 * @param context
	 */
	public BarChartBuilder(Context context) {
		super(context);
	}

	@Override
	protected void init() {
		mRenderer = new XYMultipleSeriesRenderer();
		mDataSet = new XYMultipleSeriesDataset();
		setChartSettings();
	}

	/**
	 * 设置渲染器
	 * 
	 * @param renderer
	 */
	private void setChartSettings() {
		mRenderer.setAxisTitleTextSize(30);// 设置轴标题文本大小
		mRenderer.setChartTitleTextSize(30);// 设置图表标题文字的大小
		mRenderer.setLabelsTextSize(30);// 设置标签的文字大小
		mRenderer.setLegendTextSize(30);// 设置图例文本大小
		mRenderer.setMargins(new int[] { 0, 100, 15, 0 });// 设置视图位置{t,l,b,r}
		mRenderer.setBarSpacing(0.5f);//条型柱间距
		mRenderer.setBarWidth(30);//条型柱宽度
		
		mRenderer.setAxesColor(Color.WHITE);//坐标轴颜色
		mRenderer.setPanLimits(new double[]{0,20,0,10000});//滑动范围限制

		SimpleSeriesRenderer render = new SimpleSeriesRenderer();//类似一个条型柱对象
		render.setColor(Color.BLUE);// 设置颜色
		render.setDisplayChartValues(true);// 显示条型柱上方对应的值
		render.setChartValuesTextAlign(Align.CENTER);//条型柱上方对应的值的对齐方式
		render.setChartValuesTextSize(30);
		//条型柱设置渐变
		render.setGradientEnabled(true);
		render.setGradientStart(0, Color.RED);
		render.setGradientStop(10, Color.GREEN);
		mRenderer.addSeriesRenderer(render);

		// mRenderer.setChartTitle("Chart demo");
		mRenderer.setXTitle("x title");// 设置为X轴的标题
		mRenderer.setYTitle("y title");// 设置y轴的标题
		mRenderer.setYLabelsColor(0, Color.WHITE);
		mRenderer.setXLabelsAlign(Align.RIGHT);
		mRenderer.setYLabelsAlign(Align.RIGHT);
		mRenderer.setYLabelsPadding(5);
		mRenderer.setXLabelsAngle(315);//xlable的角度
//		mRenderer.setXLabelsAlign(Align.CENTER);//xlable的对齐方式
		mRenderer.setLabelsColor(Color.RED);
		mRenderer.setPanEnabled(true, true);
		mRenderer.setXRoundedLabels(false);
		mRenderer.setXLabelsPadding(20);
		mRenderer.setXLabels(0);// 设置只显示自定义的x坐标，不显示1,2,3等
		
		mRenderer.setApplyBackgroundColor(true);//必须设置为true，颜色值才生效
		mRenderer.setBackgroundColor(Color.GRAY);//设置表格背景色
		mRenderer.setMarginsColor(Color.GRAY);//设置周边背景色
		
		mRenderer.setShowLegend(false);//是否显示底部说明
	}

	@Override
	public GraphicalView buildChartView(List<BaseChartData> datas) {
		// 存放产品名-数量键值对
		HashMap<String, Double> map = parseDatas(datas);
		Iterator<String> iterator = map.keySet().iterator();

		CategorySeries series = new CategorySeries("title");
		int index = 1;
		while (iterator.hasNext()) {
			String name = iterator.next();
			double count = map.get(name);
			series.add(count);
			mRenderer.addXTextLabel(index++, name);

			if (count > maxValue) {
				maxValue = count;
			}
		}
		mRenderer.setXAxisMin(0);
		mRenderer.setYAxisMin(0);
		mRenderer.setYAxisMax(maxValue + 5);
		mRenderer.setXAxisMax(10);

		mDataSet.addSeries(series.toXYSeries());
		mChartView = ChartFactory.getBarChartView(mContext, mDataSet, mRenderer, Type.DEFAULT);
//		mChartView.setBackgroundColor(Color.BLACK);
		
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
