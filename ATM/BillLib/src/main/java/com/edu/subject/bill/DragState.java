package com.edu.subject.bill;

/**
 * 印章拖拽状态
 * 
 * @author lucher
 * 
 */
public enum DragState {
	/**
	 * 初始化状态
	 */
	INITIAL, 
	/**
	 * 等待盖章状态
	 */
	WAITING, 
	/**
	 * 拖拽中
	 */
	DRAGGING, 
	/**
	 * 盖章结束
	 */
	DONE
}
