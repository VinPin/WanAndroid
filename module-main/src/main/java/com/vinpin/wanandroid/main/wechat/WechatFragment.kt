package com.vinpin.wanandroid.main.wechat

import com.alibaba.android.arouter.facade.annotation.Route
import com.vinpin.common.RouterConstants
import com.vinpin.common.base.BaseFragment

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/11/24 11:08
 *     desc  : 微信公众号
 * </pre>
 */
@Route(path = RouterConstants.MIAN_WECHATFRAGMENT)
class WechatFragment : BaseFragment() {

    override val layoutId: Int
        get() = 0

    override fun getData() {

    }

    override fun getLazyData() {

    }
}