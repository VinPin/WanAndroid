package com.vinpin.common.util

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.ViewCompat
import com.vinpin.common.R
import com.vinpin.commonutils.ResourcesUtils
import com.vinpin.commonutils.SizeUtils
import com.vinpin.selectorhelper.ShapeHelper

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/4 9:20
 *     desc  : Toast工具类，解决多次重复弹出信息的问题。
 * </pre>
 */
object ToastUtils {

    const val LENGTH_SHORT = 0
    const val LENGTH_LONG = 1

    private var toast: Toast? = null
    private val id: Int = ViewCompat.generateViewId()

    fun makeText(context: Context, text: String, duration: Int): Toast {
        if (toast == null) {
            toast = Toast(context.applicationContext)
            val root = LinearLayout(context.applicationContext)
            root.layoutParams = ViewGroup.LayoutParams(
                SizeUtils.dp2px(300f),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            root.setPadding(
                SizeUtils.dp2px(30f),
                SizeUtils.dp2px(10f),
                SizeUtils.dp2px(30f),
                SizeUtils.dp2px(10f)
            )
            root.gravity = Gravity.CENTER
            root.orientation = LinearLayout.VERTICAL
            root.background = ShapeHelper.getInstance().solidColor("#C8404040")
                .cornerRadius(SizeUtils.dp2px(10f).toFloat())
                .create()
            val toastText = TextView(context.applicationContext)
            toastText.id = id
            toastText.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            toastText.gravity = Gravity.CENTER
            toastText.textSize = 14f
            toastText.setTextColor(ResourcesUtils.getColor(R.color.white))
            root.addView(toastText)
            toast?.view = root
        }
        toast!!.view.findViewById<TextView>(id)?.let {
            it.text = text
        }
        toast!!.duration = if (duration == LENGTH_LONG) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        return toast!!
    }

    fun makeText(context: Context, @StringRes resId: Int, duration: Int): Toast {
        return makeText(context, ResourcesUtils.getString(resId), duration)
    }

    fun shortToast(context: Context, text: String) {
        makeText(context, text, LENGTH_SHORT).show()
    }

    fun shortToast(context: Context, @StringRes resId: Int) {
        makeText(context, resId, LENGTH_SHORT).show()
    }

    fun longToast(context: Context, text: String) {
        makeText(context, text, LENGTH_LONG).show()
    }

    fun longToast(context: Context, @StringRes resId: Int) {
        makeText(context, resId, LENGTH_LONG).show()
    }
}