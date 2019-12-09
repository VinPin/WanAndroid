package com.vinpin.wanandroid.main.knowledge

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.vinpin.common.RouterConstants
import com.vinpin.common.base.BaseFragment
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

    private val mViewModel: KnowledgeViewModel by lazy {
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(mActivity.application)
        ViewModelProviders.of(this, factory).get(KnowledgeViewModel::class.java)
    }

    override val layoutId: Int get() = R.layout.fragment_konwledge

    override fun getData() {
        title_bar.backIcon.visibility = View.GONE
        title_bar.title.text = "体系"

        mViewModel.treeList.observe(this, Observer {

        })

        recycerView.setEnableRefresh(false)
    }

    override fun getLazyData() {

    }
}