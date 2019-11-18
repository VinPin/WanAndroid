package com.vinpin.libcommon.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/18 21:19
 *     desc  : Activity基类
 * </pre>
 */
@ExperimentalCoroutinesApi
abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        getData(savedInstanceState)
    }

    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }

    /** 返回布局Id */
    abstract fun getLayoutId(): Int

    /** 初始化数据 */
    abstract fun getData(savedInstanceState: Bundle?)
}