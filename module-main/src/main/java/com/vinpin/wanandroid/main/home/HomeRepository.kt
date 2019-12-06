package com.vinpin.wanandroid.main.home

import com.vinpin.common.net.ApiResponse
import com.vinpin.common.net.KcRetrofitUtils
import com.vinpin.common.net.tryCatchWithIo
import com.vinpin.common.vo.Article
import com.vinpin.common.vo.ArticleList
import com.vinpin.common.vo.BannerInfo

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/11/27 14:48
 *     desc  : 主页Fragment的Repository
 * </pre>
 */
class HomeRepository {

    suspend fun getTopArticleList(): ApiResponse<List<Article>> = tryCatchWithIo {
        val apiResponse = KcRetrofitUtils.getApi().getTopArticleList()
        apiResponse.getOrNull()?.let { list ->
            list.forEach {
                it.top = true
            }
        }
        apiResponse
    }

    suspend fun getArticleList(page: Int): ApiResponse<ArticleList> = tryCatchWithIo {
        KcRetrofitUtils.getApi().getArticleList(page)
    }

    suspend fun getBanner(): ApiResponse<List<BannerInfo>> = tryCatchWithIo {
        KcRetrofitUtils.getApi().getBanner()
    }

    suspend fun collect(id: Int): ApiResponse<String> = tryCatchWithIo {
        KcRetrofitUtils.getApi().collect(id)
    }

    suspend fun uncollect(id: Int): ApiResponse<String> = tryCatchWithIo {
        KcRetrofitUtils.getApi().uncollect(id)
    }
}