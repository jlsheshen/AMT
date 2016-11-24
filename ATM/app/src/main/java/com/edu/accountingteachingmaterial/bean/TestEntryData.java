package com.edu.accountingteachingmaterial.bean;

import com.edu.subject.data.BaseSubjectData;
import com.edu.subject.data.BaseTestData;

/**
 * Created by Administrator on 2016/11/24.
 */

public class TestEntryData extends BaseTestData {
    private SubjectEntryData subjectData;

    @Override
    public BaseSubjectData getSubjectData() {
        return subjectData;
    }

    @Override
    public void setSubjectData(BaseSubjectData subjectData) {

    }
}
