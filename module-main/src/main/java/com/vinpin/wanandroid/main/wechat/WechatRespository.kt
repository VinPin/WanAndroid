package com.vinpin.wanandroid.main.wechat

import com.vinpin.common.net.ApiResponse
import com.vinpin.common.net.KcRetrofitUtils
import com.vinpin.common.net.tryCatchWithIo
import com.vinpin.common.vo.ArticleList
import com.vinpin.common.vo.ChapterInfo

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/12 14:29
 *     desc  : 公众号Fragment的Repository
 * </pre>
 */
class WechatRespository {

    suspend fun getWxArticleChapters(): ApiResponse<List<ChapterInfo>> = tryCatchWithIo {
        KcRetrofitUtils.getApi().getWxArticleChapters()
    }

    suspend fun getWxArticleList(id: Int, page: Int): ApiResponse<ArticleList> = tryCatchWithIo {
        KcRetrofitUtils.getApi().getWxArticleList(id, page)
    }
}