package com.edu.subject;

/**
 * Created by Administrator on 2016/12/16.
 */

public class BASE_URL {
    public static String BASE_URL = "http://192.168.1.143:8080";

    public static String TEMP_URL;

    public static String BASE_IMAGE_URL;

    public static String getBaseImageUrl() {
        return BASE_URL + "/eduExam";
    }
}
