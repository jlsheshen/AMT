package com.edu.library.chart;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 饼图创建类
 * 
 * @author lucher
 * 
 */
public class PieChartBuilder extends BaseChartBuilder implements OnClickListener {

	// 属性数据
	private CategorySeries mSeries;
	// 渲染器
	private DefaultRenderer mRenderer;

	// 对应颜色
	protected static int[] COLORS = new int[] { Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN, Color.RED, Color.LTGRAY, Color.GRAY, Color.YELLOW, Color.DKGRAY };

	/**
	 * 构造，使用预置颜色
	 * @param context
	 */
	public PieChartBuilder(Context context) {
		super(context);
	}

	@Override
	protected void init() {
		mSeries = new CategorySeries("");
		mRenderer = new DefaultRenderer();
		// 缩放按钮可见
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setStartAngle(180);
		// 显示数量值
		mRenderer.setDisplayValues(true);
		// 设置item可点
		mRenderer.setClickEnabled(true);
		if (mChartView == null) {
			mChartView = ChartFactory.getPieChartView(mContext, mSeries, mRenderer);
		}
		mChartView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
		if (seriesSelection == null) {
		} else {
			for (int i = 0; i < mSeries.getItemCount(); i++) {
				mRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
			}
			mChartView.repaint();
		}
	}

	@Override
	public GraphicalView buildChartView(List<BaseChartData> datas) {
		// 存放产品名-数量键值对
		HashMap<String, Double> map = parseDatas(datas);
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String name = iterator.next();
			Double count = map.get(name);
			addRenderer(name, count);
		}
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

	/**
	 * 加入统计图item
	 * 
	 * @param label
	 *            标签
	 * @param value
	 *            值
	 */
	private void addRenderer(String label, double value) {
		mSeries.add(label, value);
		SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
		renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setLabelsTextSize(30);
		mRenderer.setLegendTextSize(0);
		mRenderer.setLabelsColor(Color.WHITE);
		mChartView.repaint();
	}

	@Override
	public DefaultRenderer getRenderer() {
		return mRenderer;
	}
}
