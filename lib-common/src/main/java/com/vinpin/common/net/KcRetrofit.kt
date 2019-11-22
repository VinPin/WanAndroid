package com.vinpin.common.net

import com.vinpin.common.util.LogUtils
import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/20 17:04
 *     desc  : Retrofit 封装类
 * </pre>
 */
class KcRetrofit {

    private val mRetrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
    }

    var baseUrl: String = ApiService.BASE_URL
        set(value) {
            field = value
            mRetrofitBuilder.baseUrl(value)
        }

    private var mOkHttpClient: OkHttpClient? = null

    fun client(client: OkHttpClient): KcRetrofit {
        mOkHttpClient = client
        return this
    }

    fun build(): Retrofit {
        if (mOkHttpClient == null) {
            mOkHttpClient = KcRetrofitUtils.defaultOkHttpClient()
        }
        return mRetrofitBuilder.client(mOkHttpClient!!).build()
    }

    fun <T> create(cls: Class<T>): T {
        return build().create(cls)
    }
}

/**
 * author : vinpin
 * e-mail : hearzwp@163.com
 * time   : 2019/11/20 13:12
 * desc   : OkHttpClient 封装类
 */
class KcOkHttpClient {

    private val mOkHttpBuilder: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder().proxy(Proxy.NO_PROXY)
    }

    fun readTimeout(second: Long): KcOkHttpClient {
        mOkHttpBuilder.readTimeout(second, TimeUnit.SECONDS)
        return this
    }

    fun writeTimeout(second: Long): KcOkHttpClient {
        mOkHttpBuilder.writeTimeout(second, TimeUnit.SECONDS)
        return this
    }

    fun connectTimeout(second: Long): KcOkHttpClient {
        mOkHttpBuilder.connectTimeout(second, TimeUnit.SECONDS)
        return this
    }

    fun addInterceptor(interceptor: Interceptor): KcOkHttpClient {
        mOkHttpBuilder.addInterceptor(interceptor)
        return this
    }

    fun cookieJar(cookieJar: CookieJar): KcOkHttpClient {
        mOkHttpBuilder.cookieJar(cookieJar)
        return this
    }

    fun debugLog(enable: Boolean): KcOkHttpClient {
        if (enable) {
            val interceptor = HttpLoggingInterceptor { msg -> LogUtils.d("RcRetrofitUtils", msg) }
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            mOkHttpBuilder.addInterceptor(interceptor)
        }
        return this
    }

    fun build(): OkHttpClient {
        return mOkHttpBuilder.build()
    }
}