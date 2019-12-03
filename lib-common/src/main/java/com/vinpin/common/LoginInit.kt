package com.vinpin.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.vinpin.commonutils.Utils

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/2 16:17
 *     desc  : 处理登陆回调初始化类
 * </pre>
 */
object LoginInit {

    var goLoginCallBack: ((activity: FragmentActivity) -> Unit)? = null

}

interface LoginOwner {

    fun show(manager: FragmentManager)
}

/**
 * 未登陆的广播接受者
 */
class LoginReceiver() : BroadcastReceiver() {

    private var localBroadcastManager: LocalBroadcastManager? = null
    private var listener: (() -> Unit)? = null

    constructor(
        localBroadcastManager: LocalBroadcastManager,
        listener: () -> Unit
    ) : this() {
        this.localBroadcastManager = localBroadcastManager
        this.listener = listener
    }

    companion object {

        private const val ACTION = "com.vinpin.common.LoginReceiver"

        fun sendNotLogin() {
            val manager = LocalBroadcastManager.getInstance(Utils.getApp())
            val intent = Intent()
            intent.action = ACTION
            manager.sendBroadcast(intent)
        }

        fun register(listener: () -> Unit): LoginReceiver {
            val localBroadcastManager = LocalBroadcastManager.getInstance(Utils.getApp())
            val filter = IntentFilter()
            filter.addAction(ACTION)
            val loginReceiver = LoginReceiver(localBroadcastManager, listener)
            localBroadcastManager.registerReceiver(loginReceiver, filter)
            return loginReceiver
        }
    }

    fun unregister() {
        localBroadcastManager?.unregisterReceiver(this)
        localBroadcastManager = null
        listener = null
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        listener?.invoke()
    }
}