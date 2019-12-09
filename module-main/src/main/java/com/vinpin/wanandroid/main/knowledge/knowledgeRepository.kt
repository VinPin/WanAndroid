package com.vinpin.wanandroid.main.knowledge

import com.vinpin.common.net.ApiResponse
import com.vinpin.common.net.KcRetrofitUtils
import com.vinpin.common.net.tryCatchWithIo
import com.vinpin.common.vo.ArticleList
import com.vinpin.common.vo.TreeInfo

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/7 11:37
 *     desc  : 知识体系Fragment的Repository
 * </pre>
 */
class KnowledgeRespository {

    suspend fun getTreeList(): ApiResponse<List<TreeInfo>> = tryCatchWithIo {
        val apiResponse = KcRetrofitUtils.getApi().getTreeList()
        apiResponse.getOrNull()?.let { list ->
            list.forEach {
                it.parent = true
            }
        }
        apiResponse
    }

    suspend fun getTreeList(page: Int, id: Int): ApiResponse<ArticleList> = tryCatchWithIo {
        KcRetrofitUtils.getApi().getTreeArticleList(page, id)
    }
}