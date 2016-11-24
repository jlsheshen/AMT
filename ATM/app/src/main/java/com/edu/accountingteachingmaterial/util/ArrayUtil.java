package com.edu.accountingteachingmaterial.util;


import com.edu.accountingteachingmaterial.subject.view.EntryView;

import java.util.ArrayList;
import java.util.List;

/**
 * Array操作工具类
 *
 * @author lucher
 */
public class ArrayUtil {

    /**
     * 查找target在array中的索引
     *
     * @param array
     * @param target
     * @return
     */
    public static int searchIndex(String[] array, String target) {
        int index = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                index = i;
                break;
            }
        }

        return index;
    }

    /**
     * 将list反序排列，返回一个新的list
     *
     * @param list
     * @return
     */
    public static List<EntryView.EntryItem> reverse(List<EntryView.EntryItem> list) {
        int size = list.size();
        List<EntryView.EntryItem> result = new ArrayList<EntryView.EntryItem>(size);
        for (int i = 0; i < size; i++) {
            result.add(list.get(size - i - 1));
        }

        return result;
    }


    /**
     * 将list反序排列，返回一个新的list
     *
     * @param list
     * @return
     */
    public static List<EntryView.EntryItem> reverseT(List<EntryView.EntryItem> list) {
        int size = list.size();
        List<EntryView.EntryItem> result = new ArrayList<EntryView.EntryItem>(size);
        for (int i = 0; i < size; i++) {
            result.add(list.get(size - i - 1));
        }

        return result;
    }
}
