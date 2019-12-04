package com.vinpin.common.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.vinpin.common.BuildConfig
import com.vinpin.common.LoginInit
import com.vinpin.common.LoginOwner
import com.vinpin.common.RouterConstants
import com.vinpin.commonutils.AppManager
import com.vinpin.commonutils.Utils
import com.vinpin.selectorhelper.SelectorHelper

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/19 14:53
 *     desc  : Application基类
 * </pre>
 */
abstract class BaseApplication : Application() {

    init {
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer { context, layout ->

        }
        //全局设置默认的 Header
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            ClassicsHeader(context)
        }
        //全局设置默认的 Footer
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            ClassicsFooter(context)
        }
    }

    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            e.printStackTrace()
            AppManager.getInstance().AppExit()
        }
        Utils.init(this)
        SelectorHelper.init(this)
        registerActivityCallbacks()
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
        LoginInit.goLoginCallBack = {
            val loginOwner =
                ARouter.getInstance().build(RouterConstants.MAIN_LOGINFRAGMENT).navigation() as LoginOwner
            loginOwner.show(it.supportFragmentManager)
        }
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