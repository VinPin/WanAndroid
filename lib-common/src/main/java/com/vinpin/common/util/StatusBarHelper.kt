package com.vinpin.common.util

import android.app.Activity
import androidx.annotation.ColorInt
import com.vinpin.commonutils.StatusBarUtils

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/11/29 11:46
 *     desc  : 设置状态栏样式的帮助类
 * </pre>
 */
object StatusBarHelper {

    fun setLightMode(activity: Activity) {
        StatusBarUtils.setStatusBarLightMode(activity, true)
    }

    fun setDarkMode(activity: Activity) {
        StatusBarUtils.setStatusBarLightMode(activity, false)
    }

    fun setColor(activity: Activity, @ColorInt color: Int) {
        StatusBarUtils.setStatusBarColor(activity, color)
    }
}