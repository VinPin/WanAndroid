package com.vinpin.common.util

import com.google.gson.Gson
import com.vinpin.common.vo.UserInfo

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/3 10:25
 *     desc  : 用户登陆信息工具类
 * </pre>
 */
class UserInfoUtils private constructor() {

    private var mUserInfo: UserInfo? = null

    companion object {

        private var instance: UserInfoUtils? = null

        private const val KEY_LOGIN_USER = "KEY_LOGIN_USER"

        fun getInstance(): UserInfoUtils {
            if (instance == null) {
                synchronized(UserInfoUtils::class) {
                    if (instance == null) {
                        instance = UserInfoUtils()
                    }
                }
            }
            return instance!!
        }
    }

    init {
        try {
            val json = SPUtils[KEY_LOGIN_USER, ""]
            mUserInfo = Gson().fromJson<UserInfo>(json, UserInfo::class.java)
        } catch (e: Exception) {
        }
    }

    fun saveUserInfo(userInfo: UserInfo) {
        mUserInfo = userInfo
        SPUtils.put(KEY_LOGIN_USER, Gson().toJson(mUserInfo))
    }
}