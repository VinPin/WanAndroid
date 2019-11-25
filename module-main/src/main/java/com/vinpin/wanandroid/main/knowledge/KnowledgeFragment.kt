package com.vinpin.wanandroid.main.knowledge

import com.alibaba.android.arouter.facade.annotation.Route
import com.vinpin.common.RouterConstants
import com.vinpin.common.base.BaseFragment

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/11/24 11:04
 *     desc  : 知识体系
 * </pre>
 */
@Route(path = RouterConstants.MAIN_KNOWLEDGEFRAGMENT)
class KnowledgeFragment : BaseFragment() {

    override val layoutId: Int
        get() = 0

    override fun getData() {

    }

    override fun getLazyData() {

    }
}