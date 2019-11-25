package com.vinpin.wanandroid.main.project

import com.alibaba.android.arouter.facade.annotation.Route
import com.vinpin.common.RouterConstants
import com.vinpin.common.base.BaseFragment

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/11/24 11:09
 *     desc  : 项目
 * </pre>
 */
@Route(path = RouterConstants.MAIN_PROJECTFRAGMENT)
class ProjectFragment : BaseFragment() {

    override val layoutId: Int
        get() = 0

    override fun getData() {

    }

    override fun getLazyData() {

    }
}