package com.vinpin.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/18 21:19
 *     desc  : Activity基类
 * </pre>
 */
abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    /** 标识需要[window]默认的背景色，默认不需要。避免不必要的背景色造成过度绘制 */
    var mNeedWindowBackgroud: Boolean = false

    var isActive: Boolean = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        getData(savedInstanceState)
        if (!mNeedWindowBackgroud) {
            window.setBackgroundDrawable(null)
        }
    }

    override fun onResume() {
        super.onResume()
        isActive = true
    }

    override fun onStop() {
        isActive = false
        super.onStop()
    }

    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }

    /** 返回布局Id */
    abstract val layoutId: Int

    /** 初始化数据 */
    abstract fun getData(savedInstanceState: Bundle?)
}