2016-11-21
lucher
------------------
#爱丁公用题库lib，包括各种题型控件

##【使用该库注意事项】重要！！！
1.项目初始化的时候必须先给SubjectConstant.DATABASE_NAME赋值为实际数据库的名字，否则会出现异常
2.项目初始化的时候必须对ImageLoader进行初始化，可参考EduSubjectsLibDemo里的SubjectsApplication.initImageLoader()方法，否则会出现异常
3.该库提供图片预下载功能，为了更佳的体验，可以在程序启动的时候调用预加载方法：
SubjectImageLoader.getInstance(mContext).preloadAllPics();
【注：该方法必须在1和2之后执行，否则会出现异常】
------------------------------------------------------
添加一种题型需要做如下工作：
1.CommonSubjectDataDao
parseCursor方法，初始化题目数据
2.BaseSubjectTestDataDao
initTestData方法，初始化题目测试数据
如果该题支持子题模式，需要在initChildSubject方法里添加子题测试数据初始化
3.编写视图类，如果是基本类题型，可继承BasicSubjectView实现具体功能
4.SubjectViewPagerFragment
onCreateView方法，初始化对应题型的视图
5.CardGridItem
apply方法，加入答题卡支持
6.SubjectsAdapter
如果该题支持子题模式，需要在createSubjectView方法里加入字体视图初始化


单据题型操作说明：
键盘操作事件：
*所有模式：PgUp 下一页，PgDn 上一页
*答题模式下：left/up 上一空，right/down/tab/enter/pad_enter 下一空
*非答题模式下：left 上一页，right 下一页


***必须在测试模式下才支持全部提交，否则会出现逻辑混乱
-----
技术积累：
输入法软件盘可能会遮挡EditText，使用adjustpan可使布局上移，从而显示出EditText
但是，如果当前EditText(gravity is center or center_horizontal)已经获取焦点了，再点击该EditText弹出输入法软件盘时，布局不会上移，从而导致EditText被遮挡
这个问题是Android的已知bug，在stackoverflow上有人提出过，可通过如下方法解决：
http://stackoverflow.com/questions/15317157/android-adjustpan-not-working-after-the-first-time
问：Android adjustpan not working after the first time
答：The problem you're experiencing is now a known platform bug as described here: https://code.google.com/p/android/issues/detail?id=182191
The fix is not backported and so will not take effect until Android N. Until Android N is your minimum SDK, the following solution is a workaround.
You can create a custom class extending EditText and override the method onKeyPreIme(int keyCode, KeyEvent event) like this:
@Override
public boolean onKeyPreIme(int keyCode, KeyEvent event)
{
   if(keyCode == KeyEvent.KEYCODE_BACK)
     {
         clearFocus();
     }
return super.onKeyPreIme(keyCode, event);
}
You should also ensure a higher level layout has the ability to be focused so the EditText's focus may be cleared successfully. The following attributes may work for you:

android:descendantFocusability="beforeDescendants"
android:focusableInTouchMode="true"
Now when the back key is pressed, the EditText loses the focus. Then when you tap the button again, adjustpan will work as intended. (Posting as an answer since the edit was refused.)
