package com.vinpin.wanandroid.main.home

import com.alibaba.android.arouter.facade.annotation.Route
import com.vinpin.common.RouterConstants
import com.vinpin.common.base.BaseFragment

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/11/24 11:00
 *     desc  : 主页
 * </pre>
 */
@Route(path = RouterConstants.MAIN_HOMEFRAGMENT)
class HomeFragment : BaseFragment() {

    override val layoutId: Int get() = 0

    override fun getData() {
    }

    override fun getLazyData() {
    }
}