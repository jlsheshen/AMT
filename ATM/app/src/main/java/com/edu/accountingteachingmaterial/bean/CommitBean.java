package com.edu.accountingteachingmaterial.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */

public class CommitBean {

    int ExanID;
    List <Anuwer> baseTestDataList;

    private class Anuwer {
        int flag;
        String uAnswer;
        String uSign;
        float uScore;
        int type;



    }
}
