package com.vinpin.common.net

import android.content.Context
import com.vinpin.commonutils.Utils
import com.vinpin.common.BuildConfig
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient

/**
 * author : vinpin
 * e-mail : hearzwp@163.com
 * time   : 2019/11/20 13:55
 * desc   : 获取KcRetrofit实例的工具类
 */
object KcRetrofitUtils {

    private var defaultKcRetrofit: KcRetrofit? = null

    fun getContext(): Context = Utils.getApp()

    /**
     * 获取默认的KcRetrofit实例，该实例只会被创建一次。可以作为全局使用，一般不再修改其属性。
     */
    fun getDefault(): KcRetrofit {
        if (defaultKcRetrofit == null) {
            defaultKcRetrofit = newInstance()
        }
        return defaultKcRetrofit!!
    }

    /**
     * 创建新的KcRetrofit实例
     */
    fun newInstance(): KcRetrofit {
        return KcRetrofit()
    }

    /**
     * 使用默认的KcRetrofit实例创建指定的API接口
     */
    fun <T> create(cls: Class<T>): T {
        return getDefault().create(cls)
    }

    /**
     * 使用默认的KcRetrofit实例创建ApiService接口
     */
    fun getApi() = create(ApiService::class.java)

    /**
     * 创建默认的OkHttpClient实例
     */
    fun defaultOkHttpClient(): OkHttpClient {
        return defaultKcOkHttpClient().debugLog(BuildConfig.DEBUG).build()
    }

    /**
     * 创建默认的KcOkHttpClient实例
     */
    fun defaultKcOkHttpClient(): KcOkHttpClient {
        return KcOkHttpClient()
            .readTimeout(30)
            .writeTimeout(30)
            .connectTimeout(30)
            .cookieJar(object : CookieJar {
                override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
                    if (url.toString().startsWith("https://www.wanandroid.com/user/login?")) {
                        CookieStore.saveCookies(cookies)
                    }
                }

                override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
                    return CookieStore.getCookies()
                }
            })
    }
}