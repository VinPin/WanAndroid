package com.vinpin.libcommon.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.vinpin.commonutils.AppManager
import com.vinpin.commonutils.Utils

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/19 14:53
 *     desc  : Application基类
 * </pre>
 */
abstract class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            e.printStackTrace()
            AppManager.getInstance().AppExit()
        }
        Utils.init(this)
        registerActivityCallbacks()
        initSdk()
    }

    /** 子类的初始化操作 */
    abstract fun initSdk()

    private fun registerActivityCallbacks() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
                AppManager.getInstance().addActivity(activity)
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivitySaveInstanceState(activity: Activity?, bundle: Bundle?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
                AppManager.getInstance().removeActivity(activity)
            }
        })
    }
}