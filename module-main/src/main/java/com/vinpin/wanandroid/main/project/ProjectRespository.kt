package com.vinpin.wanandroid.main.project

import com.vinpin.common.net.ApiResponse
import com.vinpin.common.net.KcRetrofitUtils
import com.vinpin.common.net.tryCatchWithIo
import com.vinpin.common.vo.ArticleList
import com.vinpin.common.vo.ChapterInfo

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/13 10:55
 *     desc  : 项目Fragment的Repository
 * </pre>
 */
class ProjectRespository {

    suspend fun getProjectChapters(): ApiResponse<List<ChapterInfo>> = tryCatchWithIo {
        KcRetrofitUtils.getApi().getProjectChapters()
    }

    suspend fun getProjectArticleList(cid: Int, page: Int): ApiResponse<ArticleList> = tryCatchWithIo {
        KcRetrofitUtils.getApi().getProjectArticleList(page, cid)
    }
}