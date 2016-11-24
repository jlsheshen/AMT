package com.edu.library.common;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * 动画帮助类
 * 
 * @author lucher
 * 
 */
public class EduAnimation {

	/**
	 * 动画的方式隐藏某个视图
	 * 
	 * @param view
	 *            待隐藏视图
	 * @param duration
	 *            动画时间
	 * @return
	 */
	public static ObjectAnimator fadeOutView(View view, int duration) {
		ObjectAnimator anim = ObjectAnimator.ofFloat(view, View.ALPHA, 1, 0);
		anim.setDuration(duration);
		anim.setInterpolator(new DecelerateInterpolator());
		anim.start();

		return anim;
	}

	/**
	 * 动画的方式把view移动到target
	 * 
	 * @param view
	 *            待移动视图
	 * @param target
	 *            移动的目标位置视图
	 * @param duration
	 *            动画时长
	 * @return
	 */
	public static AnimatorSet animationMove(View view, View target, long duration) {
		Rect startBounds = new Rect();
		view.getGlobalVisibleRect(startBounds);
		// 最终坐标
		Rect finalBounds = new Rect();
		target.getGlobalVisibleRect(finalBounds);

		AnimatorSet set = new AnimatorSet();
		set.play(ObjectAnimator.ofFloat(view, View.X, startBounds.left, finalBounds.left + (finalBounds.width() - startBounds.width()) / 2)).with(
				ObjectAnimator.ofFloat(view, View.Y, startBounds.top, finalBounds.top));
		set.setDuration(duration);
		set.start();
		return set;
	}

}
