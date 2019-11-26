package com.vinpin.wanandroid.main.mine

import com.alibaba.android.arouter.facade.annotation.Route
import com.vinpin.common.RouterConstants
import com.vinpin.common.base.BaseFragment
import com.vinpin.wanandroid.main.R

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/11/24 11:08
 *     desc  : 我的
 * </pre>
 */
@Route(path = RouterConstants.MAIN_MINEFRAGMENT)
class MineFragment : BaseFragment() {

    override val layoutId: Int get() = R.layout.fragment_mine

    override fun getData() {

    }

    override fun getLazyData() {

    }
}