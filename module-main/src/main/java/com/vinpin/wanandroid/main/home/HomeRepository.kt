package com.vinpin.wanandroid.main.home

import com.vinpin.common.net.ApiResponse
import com.vinpin.common.net.KcRetrofitUtils
import com.vinpin.common.net.handlingExceptions
import com.vinpin.common.vo.ArticleList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/11/27 14:48
 *     desc  : 主页Fragment的Repository
 * </pre>
 */
class HomeRepository {

    suspend fun getArticleList(page: Int): ApiResponse<ArticleList> = withContext(Dispatchers.IO) {
        try {
            KcRetrofitUtils.getApi().getArticleList(page)
        } catch (e: Exception) {
            ApiResponse<ArticleList>(handlingExceptions(e))
        }
    }
}