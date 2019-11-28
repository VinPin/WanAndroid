package com.vinpin.wanandroid.main.home

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.vinpin.common.RouterConstants
import com.vinpin.common.base.BaseFragment
import com.vinpin.wanandroid.main.R
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/11/24 11:00
 *     desc  : 主页
 * </pre>
 */
@Route(path = RouterConstants.MAIN_HOMEFRAGMENT)
class HomeFragment : BaseFragment() {

    private val mViewModel: HomeViewModel by lazy {
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(mActivity.application)
        ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
    }

    override val layoutId: Int get() = R.layout.fragment_home

    override fun getData() {
        mViewModel.articleList.observe(this, Observer {
            it.getOrNull()?.let {
                recycerView.closeHeaderOrFooter()
            }
            it.exceptionOrNull()?.let {
                recycerView.closeHeaderOrFooter()
            }
        })
        recycerView.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mViewModel.getArticleList(true)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mViewModel.getArticleList()
            }
        })
        recycerView.autoRefresh()
    }

    override fun getLazyData() {
    }
}