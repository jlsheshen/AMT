package com.edu.subject.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.edu.subject.basic.OptionData;
import com.edu.subject.basic.SelectItemAdapter;
import com.edu.subject.data.BaseTestData;
import com.edu.subject.data.SubjectSelectData;
import com.edu.subject.data.body.SelectBodyData;

/**
 * 多选题
 * 
 * @author lucher
 * 
 */
public class MultiSelectView extends BaseSelectView {

	public MultiSelectView(Context context, BaseTestData data) {
		super(context, data, "多选");
	}

	@Override
	protected SelectItemAdapter initAdapter() {
		List<OptionData> datas = new ArrayList<OptionData>();
		if (((SubjectSelectData) mSubjectData).getOptions() != null) {
			for (SelectBodyData option : ((SubjectSelectData) mSubjectData).getOptions()) {
				datas.add(new OptionData(option));
			}
		}
		return new SelectItemAdapter(mContext, datas, true).setMulti(true);
	}

	@Override
	public void onItemSelected(OptionData data) {
		saveAnswer();
	}
}
