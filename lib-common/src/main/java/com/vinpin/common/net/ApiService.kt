package com.vinpin.common.net

import com.vinpin.common.vo.UserInfo
import retrofit2.http.POST
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

}