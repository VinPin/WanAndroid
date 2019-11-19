package com.vinpin.libcommon.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/18 21:19
 *     desc  : Fragment基类
 * </pre>
 */
@ExperimentalCoroutinesApi
abstract class BaseFragment : Fragment(), CoroutineScope by MainScope() {

    lateinit var mContext: Context
        private set
    lateinit var mActivity: BaseActivity
        private set
    lateinit var mContentView: View
        private set

    /** 标识第一次加载数据 */
    private var isFirstLoad: Boolean = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as BaseActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContentView = View.inflate(mContext, getLayoutId(), null)
        return mContentView
    }

    override fun onResume() {
        super.onResume()
        if (isFirstLoad) {
            isFirstLoad = false
            getData()
        } else {
            getLazyData()
        }
    }

    override fun onDestroyView() {
        cancel()
        super.onDestroyView()
    }

    /** 返回布局的资源id */
    abstract fun getLayoutId(): Int

    /** 初始化数据 */
    abstract fun getData()

    /** 对用户可见时懒加载数据 */
    abstract fun getLazyData()
}