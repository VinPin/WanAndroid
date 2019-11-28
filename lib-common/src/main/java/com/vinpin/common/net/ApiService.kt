package com.vinpin.common.net

import com.vinpin.common.vo.ArticleList
import com.vinpin.common.vo.UserInfo
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/20 15:50
 *     desc  : Api 接口
 * </pre>
 */
interface ApiService {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com/"
    }

    /**
     * 登录
     */
    @POST("user/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): ApiResponse<UserInfo>

    /**
     * 首页文章列表
     */
    @GET("article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ApiResponse<ArticleList>

}