package com.vinpin.libcommon.utils

import android.app.Application

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/19 14:53
 *     desc  : 工具类
 * </pre>
 */
class Utils {

    companion object {

        lateinit var mApplication: Application
            private set

        fun init(application: Application) {
            mApplication = application
        }
    }
}