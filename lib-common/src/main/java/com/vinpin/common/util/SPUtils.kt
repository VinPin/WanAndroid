package com.vinpin.common.util

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.vinpin.commonutils.Utils

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/3 10:25
 *     desc  : 使用SharedPreferences保存通用的数据
 * </pre>
 */
object SPUtils {

    private var sp: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(Utils.getApp())

    fun clear(): SPUtils {
        sp.edit().clear().apply()
        return this
    }

    fun remove(key: String): SPUtils {
        sp.edit().remove(key).apply()
        return this
    }

    fun <T> put(key: String, value: T?): SPUtils {
        val editor = sp.edit()
        when (value) {
            null -> editor.remove(key).apply()
            is String -> editor.putString(key, value as String).apply()
            is Int -> editor.putInt(key, value as Int).apply()
            is Boolean -> editor.putBoolean(key, value as Boolean).apply()
            is Long -> editor.putLong(key, value as Long).apply()
            is Float -> editor.putFloat(key, value as Float).apply()
        }
        return this
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: String, defValue: T): T = when (defValue) {
        is String -> sp.getString(key, defValue as String) as T
        is Int -> sp.getInt(key, defValue as Int) as T
        is Long -> sp.getLong(key, defValue as Long) as T
        is Float -> sp.getFloat(key, defValue as Float) as T
        is Boolean -> sp.getBoolean(key, defValue as Boolean) as T
        else -> defValue
    }
}
