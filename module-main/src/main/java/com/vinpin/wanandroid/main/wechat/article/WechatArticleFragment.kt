package com.vinpin.wanandroid.main.wechat.article

import com.alibaba.android.arouter.facade.annotation.Route
import com.vinpin.common.RouterConstants
import com.vinpin.common.base.BaseFragment
import com.vinpin.wanandroid.main.R

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/12 14:37
 *     desc  : 微信公众号的文章
 * </pre>
 */
@Route(path = RouterConstants.MAIN_WECHATARTICLEFRAGMENT)
class WechatArticleFragment : BaseFragment() {

    override val layoutId: Int get() = R.layout.fragment_wechat_article

    override fun getData() {

    }

    override fun getLazyData() {

    }
}