package com.vinpin.wanandroid.main.knowledge

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.vinpin.adapter.MultiItemTypeAdapter
import com.vinpin.common.RouterConstants
import com.vinpin.common.base.BaseFragment
import com.vinpin.common.shortToast
import com.vinpin.common.vo.ChapterInfo
import com.vinpin.wanandroid.main.R
import kotlinx.android.synthetic.main.fragment_konwledge.*

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/11/24 11:04
 *     desc  : 知识体系
 * </pre>
 */
@Route(path = RouterConstants.MAIN_KNOWLEDGEFRAGMENT)
class KnowledgeFragment : BaseFragment() {

    private var mAdapter: KnowledgeAdapter? = null

    private val mViewModel: KnowledgeViewModel by lazy {
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(mActivity.application)
        ViewModelProviders.of(this, factory).get(KnowledgeViewModel::class.java)
    }

    override val layoutId: Int get() = R.layout.fragment_konwledge

    override fun getData() {
        title_bar.backIcon.visibility = View.GONE
        title_bar.title.text = "体系"
        recycerView.setEnableRefresh(false)
        recycerView.setEnableLoadMore(false)
        recycerView.setEnableOverScrollDrag(true)

        mViewModel.chapterList.observe(this, Observer { resource ->
            resource.getOrNull()?.let {
                setRecyclerViewData(it)
            }
            resource.exceptionOrNull()?.let {

            }
        })
        mViewModel.getTreeList()
    }

    override fun getLazyData() {

    }

    private fun setRecyclerViewData(infos: List<ChapterInfo>) {
        if (mAdapter == null) {
            mAdapter = KnowledgeAdapter(mContext, infos)
            mAdapter?.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                override fun onItemLongClick(
                    view: View?,
                    holder: RecyclerView.ViewHolder?,
                    position: Int
                ): Boolean {
                    return false
                }

                override fun onItemClick(
                    view: View?,
                    holder: RecyclerView.ViewHolder?,
                    position: Int
                ) {
                    if (infos.size > position) {
                        onChildItemClicked(infos[position], 0)
                    }
                }
            })
            mAdapter?.setOnChildItemClickListener { _, item, position ->
                onChildItemClicked(item, position)
            }
            recycerView.setAdapter(mAdapter!!)
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    private fun onChildItemClicked(item: ChapterInfo, position: Int) {
        //todo 打开页面
        "正在研发中，敬请期待".shortToast(mContext)
    }
}