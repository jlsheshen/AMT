package com.edu.library.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.util.Log;

/**
 * 随机工具类
 * 
 * @author lucher
 * 
 */
public class RandomUtil {

	private static final String TAG = "RandomUtil";

	/**
	 * 随机排序给定数组
	 * 
	 * @param data
	 */
	public static void randomSort(List<?> datas) {
		Collections.shuffle(datas);
	}

	/**
	 * 随机排序给定数组
	 * 
	 * @param data
	 */
	public static void randomSort(String[] datas) {
		randomSort(Arrays.asList(datas));
	}

	/**
	 * 获取指定范围的随机数,包括start和end
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getRandomData(int start, int end) {
		Random rand = new Random();
		return rand.nextInt(end - start + 1) + start;
	}

	/**
	 * 获取指定范围,指定个数的不相等的随机数
	 * 
	 * @param start
	 * @param end
	 * @param count
	 * @return
	 */
	public static List<Integer> getRandomDatas(int start, int end, int count) {
		Random rand = new Random();
		List<Integer> list = new ArrayList<Integer>();

		int i = 0;
		while (i < count) {
			int num = rand.nextInt(end - start + 1) + start;

			if (!list.contains(num)) {
				list.add(num);
				i++;
			}
		}

		return list;
	}

	/**
	 * 获取指定范围内除了某个数的随机数
	 * 
	 * @param start
	 *            起始数
	 * @param end
	 *            终止数
	 * @param except
	 *            除外的数
	 * @return
	 */
	public static int getRandomData(int start, int end, int except) {
		int result = except - 1;
		Random rand = new Random();
		do {
			result = rand.nextInt(end - start + 1) + start;
		} while (except == result);
		return result;
	}

	/**
	 * 随机获取数据，在ids里，根据种子获取指定数量的随机数，要求每个种子对应的随机数不能存在重复的数
	 * 
	 * @param ids
	 *            待随机的id-list
	 * @param count
	 *            数量
	 * @param seed
	 *            种子
	 * @param index
	 *            取第机组，从1开始计算，必须大于1
	 * @return 返回随机后的数据list，如果参数不符和要求将返回null
	 */
	public static List<Integer> getRandomDatas(List<Integer> ids, int count, int seed, int index) {
		int size = ids.size() / count;// 指定范围的数据能够分成不重复数据的组数
		if (size < index || index < 1) {// index超出范围，返回null
			Log.e(TAG, "getRandomDatas: index is illegal");
			return null;
		}
		if (count <= 0) {// 数量小于0，返回null
			Log.e(TAG, "getRandomDatas: count is illegal");
			return null;
		}

		List<Integer> datas = new ArrayList<Integer>(size);// 所有不重复的组合情况
		Random random = new Random(seed);// 随机数生成器
		while (datas.size() < index * count) {// 不重复的组合情况一共有size种，但是当找到需要的index对应的组合时就可以结束计算了
			int id = ids.get(random.nextInt(ids.size()));
			if (!datas.contains(id)) {
				datas.add(id);
			}
		}
		Log.d(TAG, datas.toString());

		return datas.subList((index - 1) * count, index * count);// 截取index对应的组合
	}

	/**
	 * 随机获取数据，在start-end范围里，根据种子获取指定数量的随机数，要求每个种子对应的随机数不能存在重复的数
	 * 
	 * @param start
	 *            起始值
	 * @param end
	 *            终止值
	 * @param seed
	 *            种子
	 * @param index
	 *            取第机组，从1开始计算，必须大于1
	 * @return 返回随机后的数据list，如果参数不符和要求将返回null
	 */
	public static List<Integer> getRandomDatas(int start, int end, int count, int seed, int index) {
		int size = (end - start + 1) / count;// 指定范围的数据能够分成不重复数据的组数
		if (size < index || index < 1) {// index超出范围，返回null
			Log.e(TAG, "getRandomDatas: index is illegal");
			return null;
		}
		if (count <= 0) {// 数量小于0，返回null
			Log.e(TAG, "getRandomDatas: count is illegal");
			return null;
		}

		List<Integer> datas = new ArrayList<Integer>(size);// 所有不重复的组合情况
		Random random = new Random(seed);// 随机数生成器
		while (datas.size() < index * count) {// 不重复的组合情况一共有size种，但是当找到需要的index对应的组合时就可以结束计算了
			int rand = random.nextInt(end - start + 1) + start;
			if (!datas.contains(rand)) {
				datas.add(rand);
			}
		}
		Log.d(TAG, datas.toString());

		return datas.subList((index - 1) * count, index * count);// 截取index对应的组合
	}

}