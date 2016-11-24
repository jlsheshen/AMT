package com.edu.accountingteachingmaterial.base;


public class BasePresenter<T> {
	public T mView;
	
	public void attach(T view){
		this.mView = view;
	}
	public void dettach(){
		this.mView = null;
		
	}
	
			

}
