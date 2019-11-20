package com.vinpin.libcommon.util

import android.util.Log

import com.vinpin.libcommon.BuildConfig

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/20 17:04
 *     desc  : 日志输出控制类
 * </pre>
 */
object LogUtils {

    /** 是否允许输出log  */
    private val mDebuggable = BuildConfig.DEBUG

    /** 以级别为 d 的形式输出LOG  */
    fun v(tag: String, msg: String) {
        if (mDebuggable) {
            Log.v(tag, msg)
        }
    }

    /** 以级别为 d 的形式输出LOG  */
    fun d(tag: String, msg: String) {
        if (mDebuggable) {
            Log.d(tag, msg)
        }
    }

    /** 以级别为 d 的形式输出LOG  */
    fun d(tag: String, tr: Throwable) {
        if (mDebuggable) {
            Log.d(tag, "", tr)
        }
    }

    /** 以级别为 i 的形式输出LOG  */
    fun i(tag: String, msg: String) {
        if (mDebuggable) {
            Log.i(tag, msg)
        }
    }

    /** 以级别为 w 的形式输出LOG  */
    fun w(tr: Throwable) {
        w(null, tr)
    }

    /** 以级别为 w 的形式输出LOG  */
    fun w(tag: String, msg: String) {
        if (mDebuggable) {
            Log.w(tag, msg)
        }
    }

    /** 以级别为 w 的形式输出Throwable  */
    fun w(tag: String?, tr: Throwable) {
        if (mDebuggable) {
            Log.w(tag, "", tr)
        }
    }

    /** 以级别为 w 的形式输出LOG信息和Throwable  */
    fun w(tag: String, msg: String?, tr: Throwable) {
        if (mDebuggable && null != msg) {
            Log.w(tag, msg, tr)
        }
    }

    /** 以级别为 e 的形式输出LOG  */
    fun e(tag: String, msg: String) {
        if (mDebuggable) {
            Log.e(tag, msg)
        }
    }

    /** 以级别为 e 的形式输出Throwable  */
    fun e(tag: String, tr: Throwable) {
        if (mDebuggable) {
            Log.e(tag, "", tr)
        }
    }

    /** 以级别为 e 的形式输出Throwable  */
    fun e(msg: String) {
        if (mDebuggable) {
            Log.e(null, msg)
        }
    }

    /** 以级别为 e 的形式输出LOG信息和Throwable  */
    fun e(tag: String, msg: String?, tr: Throwable) {
        if (mDebuggable && null != msg) {
            Log.e(tag, msg, tr)
        }
    }
}
