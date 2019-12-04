package com.vinpin.wanandroid.main.home

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.vinpin.adapter.MultiItemTypeAdapter
import com.vinpin.common.NotifyItem
import com.vinpin.common.RouterConstants
import com.vinpin.common.base.BaseFragment
import com.vinpin.common.util.UserInfoUtils
import com.vinpin.common.vo.Article
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

    private var mAdapter: ArticleAdapter? = null

    private val mViewModel: HomeViewModel by lazy {
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(mActivity.application)
        ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
    }

    override val layoutId: Int get() = R.layout.fragment_home

    override fun getData() {
        title_bar.backIcon.visibility = View.GONE
        title_bar.title.text = "首页"
        mViewModel.articleList.observe(this, Observer { resource ->
            resource.getOrNull()?.let {
                recycerView.closeHeaderOrFooter()
                setRecyclerViewData(it)
            }
            resource.exceptionOrNull()?.let {
                recycerView.closeHeaderOrFooter()
            }
        })
        mViewModel.notifyItem.observe(this, Observer {
            when (it.type) {
                NotifyItem.Type.CHANGE -> {
                    mAdapter?.notifyItemChanged(it.position)
                }
                else -> {
                }
            }
        })
        recycerView.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mViewModel.getArticleListMore()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mViewModel.getArticleList()
            }
        })
        recycerView.autoRefresh()
    }

    override fun getLazyData() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mAdapter = null
    }

    private fun setRecyclerViewData(infos: List<Article>) {
        if (mAdapter == null) {
            mAdapter = ArticleAdapter(mContext, infos)
            mAdapter?.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {

                override fun onItemClick(
                    view: View?,
                    holder: RecyclerView.ViewHolder?,
                    position: Int
                ) {

                }

                override fun onItemLongClick(
                    view: View?,
                    holder: RecyclerView.ViewHolder?,
                    position: Int
                ): Boolean {
                    return false
                }
            })
            mAdapter?.setOnCollectClickListener { _, item, position ->
                if (UserInfoUtils.doIfLogin()) {
                    mViewModel.collectClicked(item, position)
                }
            }
            recycerView.setAdapter(mAdapter!!)
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }
}