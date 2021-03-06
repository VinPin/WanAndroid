package com.vinpin.common.net

import androidx.annotation.Keep

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/20 16:19
 *     desc  : 网络请求返回数据类
 * </pre>
 */
@Keep
data class ApiResponse<T>(var data: T?, var errorCode: Int, var errorMsg: String) {

    constructor(apiException: ApiException) : this(
        null,
        apiException.errorCode,
        apiException.errorMsg
    )

    fun getOrNull(): T? = when (errorCode) {
        0 -> data
        else -> null
    }

    fun exceptionOrNull(): ApiException? = when (errorCode) {
        0 -> null
        else -> ApiException(errorCode, errorMsg)
    }

    fun notLogin(): Boolean = errorCode == -1001
}