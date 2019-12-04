package com.vinpin.wanandroid.main.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vinpin.common.LoginOwner
import com.vinpin.common.RouterConstants
import com.vinpin.commonutils.KeyboardUtils
import com.vinpin.commonutils.ResourcesUtils
import com.vinpin.commonutils.SizeUtils
import com.vinpin.selectorhelper.ShapeHelper
import com.vinpin.wanandroid.main.R
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/3 9:26
 *     desc  : 登录BottomSheetDialogFragment
 * </pre>
 */
@Route(path = RouterConstants.MAIN_LOGINFRAGMENT)
class LoginFragment : BottomSheetDialogFragment(), LoginOwner, CoroutineScope by MainScope() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return View.inflate(context, R.layout.fragment_login, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.setOnClickListener {
            KeyboardUtils.hideSoftInput(it)
        }
        img_close.setOnClickListener {
            dismiss()
        }
        v_top.background = ShapeHelper.getInstance().solidColor(R.color.login_top)
            .tlRadius(SizeUtils.dp2px(20f).toFloat())
            .trRadius(SizeUtils.dp2px(20f).toFloat())
            .create()

        val fragments = listOf(
            DengLuFragment(),
            RegisterFragment()
        )
        viewPager.offscreenPageLimit = 2
        viewPager.isUserInputEnabled = false
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = fragments.size

            override fun createFragment(position: Int): Fragment = fragments[position]
        }
    }

    override fun onStart() {
        super.onStart()
        // 设置全屏
        dialog?.let {
            val bottomSheet =
                it.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
        view?.let {
            it.post {
                val parent = it.parent as View
                val params = parent.layoutParams as CoordinatorLayout.LayoutParams
                val bottomSheetBehavior = params.behavior as BottomSheetBehavior<*>
                bottomSheetBehavior.peekHeight = it.measuredHeight
                parent.setBackgroundColor(ResourcesUtils.getColor(R.color.transparent))
            }
        }
    }

    override fun show(manager: FragmentManager) {
        manager.findFragmentByTag(TAG)?.let {
            try {
                manager.beginTransaction().remove(it).commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        try {
            show(manager, TAG)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun dismiss() {
        try {
            super.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun switch(position: Int) {
        viewPager?.setCurrentItem(position, true)
    }

    companion object {
        private const val TAG = "login.LoginFragment"
    }
}