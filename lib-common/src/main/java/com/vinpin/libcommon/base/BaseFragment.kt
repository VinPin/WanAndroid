package com.vinpin.libcommon.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * <pre>
 *     author: vinpin
 *     time  : 2019/11/18 21:19
 *     desc  : Fragment基类
 * </pre>
 */
@ExperimentalCoroutinesApi
class BaseFragment : Fragment(), CoroutineScope by MainScope() {

    override fun onDestroyView() {
        cancel()
        super.onDestroyView()
    }
}