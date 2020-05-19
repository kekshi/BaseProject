package com.kekshi.baseproject.widget.behavior

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout

class MyBehavior : CoordinatorLayout.Behavior<View>() {

    /**
     * @param child  是要判断的主角
     * @param dependency 是宾角
     * @return true,表示依赖成立，反之不成立.只有返回为 true 时，后面的 onDependentViewChanged() 和 onDependentViewRemoved() 才会被调用。
     */
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is DependencyView
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val bottom = dependency.bottom
        child.y = bottom + 50.toFloat()
        child.x = dependency.left.toFloat()
        return true
    }
}