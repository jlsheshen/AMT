package com.edu.library.chart;

import java.util.HashMap;
import java.util.List;

import org.achartengine.GraphicalView;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

/**
 * 统计图创建基类
 * 
 * @author lucher
 * 
 */
public abstract class BaseChartBuilder {

	// 统计图控件
	protected GraphicalView mChartView;
	protected Context mContext;

	public BaseChartBuilder(Context context) {
		mContext = context;
		init();
	}
	
	/**
	 * 获取chartview
	 * 
	 * @return
	 */
	public GraphicalView getChartView() {
		return mChartView;
	}

	/**
	 * 初始化
	 */
	protected abstract void init();

	/**
	 * 创建统计图
	 * 
	 * @param datas
	 * @return
	 */
	public abstract GraphicalView buildChartView(List<BaseChartData> datas);
	
	/**
	 * 数据解析
	 * @param datas
	 * @return
	 */
	public abstract HashMap<String, Double> parseDatas(List<BaseChartData> datas);
	
	/**
	 * 获取Renderer
	 * @return
	 */
	public abstract Object getRenderer();
}
