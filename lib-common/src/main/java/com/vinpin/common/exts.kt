package com.vinpin.common

import android.content.Context
import com.vinpin.common.util.ToastUtils

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/4 9:46
 *     desc  : 扩展函数
 * </pre>
 */

fun String?.shortToast(context: Context) {
    this?.let {
        ToastUtils.shortToast(context, it)
    }
}

fun String?.longToast(context: Context) {
    this?.let {
        ToastUtils.longToast(context, it)
    }
}