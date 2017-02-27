package com.edu.accountingteachingmaterial.newsubject;

import com.edu.library.EduCrashApplication;
import com.edu.library.util.DBCopyUtil;
import com.edu.subject.SubjectConstant;
import com.edu.subject.net.SubjectImageLoader;
import com.edu.subject.util.SoundPoolUtil;

import com.lucher.net.req.LogUtil;

/**
 * 演示示例Application类，可在这儿加入初始化操作
 * 
 * @author lucher
 * 
 */
public class SubjectsApplication extends EduCrashApplication {

	@Override
	public void onCreate() {
		super.onCreate();

		// 检测数据库是否已拷贝
		DBCopyUtil fileCopyUtil = new DBCopyUtil(this);
		fileCopyUtil.checkDBVersion(Constant.DATABASE_NAME);
		// 初始化声音播放工具，如果不初始化，盖章没声
		SoundPoolUtil.getInstance().init(this);

		// 预加载网络图片
		SubjectImageLoader.getInstance(this).preloadAllPics();
		//需要对库里的数据库名字赋值为实际项目中的数据库名字
		SubjectConstant.DATABASE_NAME = Constant.DATABASE_NAME;

		// 允许打印网络请求日志
		LogUtil.LOG_ENABLED = true;
		// 下载题目示例
//		String url = Constant.SERVER_URL + "findExamPaperTopicById/1195";
//		SubjectsDownloadManager.newInstance(this).getSubjects(url);
		
		//发送成绩
//		UploadResultsManager.getSingleton(this).setResults(mSubjectAdapter.getDatas());
//		UploadResultsManager.getSingleton(this).uploadResult(6015, 1131, 10000);
	}
}
