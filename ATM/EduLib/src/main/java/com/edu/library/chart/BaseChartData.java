package com.edu.library.chart;

/**
 * 统计图数据类封装
 * 
 * @author lucher
 * 
 */
public class BaseChartData {

	// 模块名称
	private String name;
	// 模块对应比重
	private double value;
	
	/**
	 * @param name 模块名
	 * @param value 对应的属性值
	 */
	public BaseChartData(String name, double value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
