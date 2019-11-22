package com.vinpin.common.bus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/22 15:03
 *     desc  : 基于LiveData实现的消息总线
 * </pre>
 */
object LiveDataBus {

    private val mBus = HashMap<String, BusLiveData<*>>()

    fun <T> get(name: String): BusLiveData<T>? {
        if (!mBus.containsKey(name)) {
            mBus[name] = BusLiveData<T>()
        }
        return mBus[name] as? BusLiveData<T>
    }

}

class BusLiveData<T> : MutableLiveData<T>() {

    var mCurrentVersion = -1

    override fun setValue(value: T) {
        mCurrentVersion++
        super.setValue(value)
    }

    override fun postValue(value: T) {
        mCurrentVersion++
        super.postValue(value)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, ObserverWrapper(observer, mCurrentVersion, this))
    }

    fun observeSticky(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, ObserverWrapper(observer, -1, this))
    }
}

class ObserverWrapper<T>(
    private val observer: Observer<in T>,
    private val version: Int,
    private val liveData: BusLiveData<T>
) : Observer<T> {

    override fun onChanged(t: T) {
        if (liveData.mCurrentVersion > version) {
            observer.onChanged(t)
        }
    }
}
