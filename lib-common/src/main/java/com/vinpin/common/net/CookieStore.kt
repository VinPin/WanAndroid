package com.vinpin.common.net

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import okhttp3.Cookie

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/21 9:05
 *     desc  : Cookie存储管理类
 * </pre>
 */
object CookieStore {

    private const val FILLNAME = "cookies"

    private val mCookieSp: SharedPreferences by lazy {
        KcRetrofitUtils.getContext().getSharedPreferences(FILLNAME, Context.MODE_PRIVATE)
    }

    /**
     * 保存cookies
     */
    @Suppress("DEPRECATION")
    fun saveCookies(cookies: List<Cookie>) {
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)

        val editor = mCookieSp.edit()
        cookies.forEach {
            editor.putString(it.name(), it.value())
            cookieManager.setCookie(it.domain(), "${it.name()}=${it.value()}")
        }
        editor.apply()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush()
        } else {
            CookieSyncManager.createInstance(KcRetrofitUtils.getContext())
        }
    }

    /**
     * 获取cookies
     */
    fun getCookies() = ArrayList<Cookie>().apply {
        mCookieSp.all.keys.forEach {
            add(
                Cookie.Builder().domain("www.wanandroid.com")
                    .name(it)
                    .value(mCookieSp.getString(it, "")!!)
                    .build()
            )
        }
    }

    /**
     * 移除所有cookies
     */
    @Suppress("DEPRECATION")
    fun removeAllCookies() {
        mCookieSp.edit().clear().apply()

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookies(null)
            cookieManager.removeAllCookies(null)
            cookieManager.flush()
        } else {
            cookieManager.removeSessionCookie()
            cookieManager.removeAllCookie()
            CookieSyncManager.getInstance().sync()
        }
    }
}