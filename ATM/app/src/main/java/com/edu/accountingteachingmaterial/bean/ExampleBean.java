package com.edu.accountingteachingmaterial.bean;


import com.edu.library.data.BaseData;

public class ExampleBean extends BaseData {
	
	private int type ;
	
	String name;
	
	String url;


	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	

}
