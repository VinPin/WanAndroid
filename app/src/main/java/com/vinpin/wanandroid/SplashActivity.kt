package com.vinpin.wanandroid

import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.alibaba.android.arouter.launcher.ARouter
import com.vinpin.common.RouterConstants
import com.vinpin.common.base.BaseActivity

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/21 14:57
 *     desc  : 闪屏界面
 * </pre>
 */
class SplashActivity : BaseActivity() {

    override val layoutId: Int get() = R.layout.activity_splash

    override fun getData(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler().postDelayed({
            ARouter.getInstance().build(RouterConstants.MAIN_MAINACTIVITY).navigation()
        }, 3000)
    }

    override fun onBackPressed() {

    }
}
