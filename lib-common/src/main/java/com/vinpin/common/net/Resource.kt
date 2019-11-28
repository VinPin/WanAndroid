package com.vinpin.common.net

import com.vinpin.commonutils.NetworkUtils
import retrofit2.HttpException
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
class Resource<out T> constructor(val status: Status, val result: Result<T>?) {

    val isLoading: Boolean get() = status == Status.LOADING

    val isSuccess: Boolean get() = status == Status.SUCCESS

    val isFailure: Boolean get() = status == Status.FAILURE

    fun getOrNull(): T? = when {
        isLoading -> null
        else -> result?.getOrNull()
    }

    fun exceptionOrNull(): ApiException? = when {
        isLoading -> null
        else -> result?.exceptionOrNull()
    }

    companion object {

        fun <T : Any> loading() = Resource<T>(Status.LOADING, null)

        fun <T : Any> success(data: T) = Resource(Status.SUCCESS, Result.success(data))

        fun <T : Any> failure(exception: ApiException) = Resource<T>(Status.FAILURE, Result.failure(exception))
    }
}

/**
 * 一种表示数据网络状态的枚举类
 */
enum class Status {
    // 加载
    LOADING,
    // 成功
    SUCCESS,
    // 失败
    FAILURE
}

/**
 * 一个被区分的并集，其将成功结果封装为[T] 类型，或者将失败封装为任意[ApiException]异常。
 */
class Result<out T> constructor(val value: Any?) {
    /**
     * 如果此实例表示成功的结果，则返回“true”。
     */
    val isSuccess: Boolean get() = value !is Failure

    /**
     * 如果此实例表示失败的结果，则返回“true”。
     */
    val isFailure: Boolean get() = value is Failure

    /**
     * 如果此实例表示[success] [Result.isSuccess]，则返回封装的值；如果它是[failure] [Result.isFailure]，则返回null。
     */
    @Suppress("UNCHECKED_CAST")
    fun getOrNull(): T? = when {
        isFailure -> null
        else -> value as? T
    }

    /**
     * 如果此实例表示[failure] [isFailure]，则返回封装的异常；如果为[success] [isSuccess]，则返回null。
     */
    fun exceptionOrNull(): ApiException? = when (value) {
        is Failure -> value.exception
        else -> null
    }

    override fun toString(): String = when (value) {
        is Failure -> value.toString()
        else -> "Success($value)"
    }

    companion object {
        /**
         * 返回一个实例，该实例将给定的[value]封装为成功值。
         */
        fun <T> success(value: T): Result<T> = Result(value)

        /**
         * 返回一个实例，该实例将给定的[exception]封装为失败。
         */
        fun <T> failure(exception: ApiException): Result<T> = Result(Failure(exception))
    }

    internal class Failure(val exception: ApiException) {
        override fun equals(other: Any?): Boolean = other is Failure && exception == other.exception
        override fun hashCode(): Int = exception.hashCode()
        override fun toString(): String = "Failure($exception)"
    }
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