package com.vinpin.wanandroid.main

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.vinpin.common.RouterConstants
import com.vinpin.common.base.BaseActivity

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/22 16:15
 *     desc  : 主界面
 * </pre>
 */
@Route(path = RouterConstants.MAIN_MAINACTIVITY)
class MainActivity : BaseActivity() {

    override val layoutId: Int get() = R.layout.activity_main

    override fun getData(savedInstanceState: Bundle?) {

    }
}