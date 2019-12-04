package com.vinpin.common.util

import com.google.gson.Gson
import com.vinpin.common.LoginReceiver
import com.vinpin.common.vo.UserInfo

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/3 10:25
 *     desc  : 用户登陆信息工具类
 * </pre>
 */
object UserInfoUtils {

    private var mUserInfo: UserInfo? = null

    private const val KEY_LOGIN_USER = "KEY_LOGIN_USER"

    init {
        getUserInfo()
    }

    fun getUserInfo(): UserInfo? {
        if (mUserInfo == null) {
            try {
                val json = SPUtils[KEY_LOGIN_USER, ""]
                mUserInfo = Gson().fromJson<UserInfo>(json, UserInfo::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return mUserInfo
    }

    fun saveUserInfo(userInfo: UserInfo) {
        mUserInfo = userInfo
        SPUtils.put(KEY_LOGIN_USER, Gson().toJson(mUserInfo))
    }

    fun logout() {
        mUserInfo = null
        SPUtils.clear()
    }

    fun isLogin(): Boolean {
        val userInfo = getUserInfo() ?: return false
        return userInfo.id > 0
    }

    fun doIfLogin(): Boolean {
        return if (isLogin()) {
            true
        } else {
            LoginReceiver.sendNotLogin()
            false
        }
    }
}