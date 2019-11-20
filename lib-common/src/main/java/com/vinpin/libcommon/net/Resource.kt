package com.vinpin.libcommon.net

import com.vinpin.commonutils.NetworkUtils
import retrofit2.HttpException
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/20 15:55
 *     desc  : 封装数据及其状态的 Resource 类来公开网络状态
 * </pre>
 */
class Resource<T> constructor(val status: Status, val apiResponse: ApiResponse<T>?) {

    val isLoading: Boolean get() = status == Status.LOADING

    val isSuccess: Boolean get() = status == Status.SUCCESS

    fun getOrNull(): T? = when {
        isLoading -> null
        else -> apiResponse?.getOrNull()
    }

    fun exceptionOrNull(): ApiException? = when {
        isLoading -> null
        else -> apiResponse?.exceptionOrNull()
    }

    companion object {

        fun <T : Any> loading() = Resource<T>(Status.LOADING, null)

        fun <T : Any> success(apiResponse: ApiResponse<T>) = Resource(Status.SUCCESS, apiResponse)
    }
}

/**
 * 一种表示数据网络状态的枚举类
 */
enum class Status {
    // 加载中还未返回结果
    LOADING,
    // 成功返回结果
    SUCCESS
}

/**
 * 封装的异常类
 */
class ApiException(val errorCode: Int, val errorMsg: String) : Exception(errorMsg) {

    var error: Throwable? = null

    companion object {
        /** 创建无法连接服务器的异常 */
        fun noService() = ApiException(ErrorCodeConstants.NOSERVICE, "无法连接服务器")

        /** 创建网络连接失败的异常 */
        fun noNetwork() = ApiException(ErrorCodeConstants.NONETWORK, "网络已断开")
    }
}

/**
 * 处理请求层的错误，对可能的已知的错误进行处理。
 */
fun handlingExceptions(e: Throwable): ApiException {
    if (e is ApiException) {
        return e
    }
    var apiException = when (e) {
        is HttpException -> ApiException.noService()
        is ConnectException -> ApiException.noNetwork()
        is UnknownHostException -> ApiException.noNetwork()
        is SocketTimeoutException -> ApiException.noService()
        else -> {
            val exception = ApiException.noService()
            exception.error = e
            exception
        }
    }
    // 网络断开
    if (!NetworkUtils.isConnected()) {
        apiException = ApiException.noNetwork()
    }
    return apiException
}