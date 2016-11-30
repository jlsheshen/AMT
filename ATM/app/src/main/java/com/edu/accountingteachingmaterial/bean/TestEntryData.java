package com.edu.accountingteachingmaterial.bean;

import com.edu.subject.data.BaseSubjectData;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.net.AnswerResult;

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
        this.subjectData = (SubjectEntryData) subjectData;
    }

    @Override
    public AnswerResult toResult() {
        return null;
    }
}
