package com.vinpin.wanandroid.main.knowledge

import com.alibaba.android.arouter.facade.annotation.Route
import com.vinpin.common.RouterConstants
import com.vinpin.common.base.BaseFragment
import com.vinpin.wanandroid.main.R

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/11/24 11:04
 *     desc  : 知识体系
 * </pre>
 */
@Route(path = RouterConstants.MAIN_KNOWLEDGEFRAGMENT)
class KnowledgeFragment : BaseFragment() {

    override val layoutId: Int get() = R.layout.fragment_konwledge

    override fun getData() {

    }

    override fun getLazyData() {

    }
}