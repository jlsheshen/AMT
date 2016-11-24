package com.edu.library.util;

import android.hardware.Camera;

/**
 * 相机工具类
 * 
 * @author lucher
 * 
 */
public class CameraUtil {
	/**
	 * 获取前置摄像头id
	 * 
	 * @return
	 */
	private static int findFrontCamera() {
		int cameraCount = 0;
		Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
		cameraCount = Camera.getNumberOfCameras(); // get cameras number

		for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
			Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
			if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
				// 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
				return camIdx;
			}
		}
		return -1;
	}

	/**
	 * 获取后置摄像头id
	 * 
	 * @return
	 */
	private static int findBackCamera() {
		int cameraCount = 0;
		Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
		cameraCount = Camera.getNumberOfCameras(); // get cameras number

		for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
			Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
			if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
				// 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
				return camIdx;
			}
		}
		return -1;
	}

	/**
	 * 获取相机，先获取后置摄像头，没有再获取前置摄像头
	 * 
	 * @return
	 */
	public static Camera getCamera() {
		int CammeraIndex = findBackCamera();
		if (CammeraIndex == -1) {
			CammeraIndex = findFrontCamera();
		}
		Camera camera = Camera.open(CammeraIndex);
		return camera;
	}
	
	/**
	 * 获取相机id，先获取后置摄像头，没有再获取前置摄像头
	 * 
	 * @return
	 */
	public static int getCameraId() {
		int CammeraIndex = findBackCamera();
		if (CammeraIndex == -1) {
			CammeraIndex = findFrontCamera();
		}
		return CammeraIndex;
	}
}
