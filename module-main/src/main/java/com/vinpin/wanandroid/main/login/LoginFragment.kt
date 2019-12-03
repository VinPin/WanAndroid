package com.vinpin.wanandroid.main.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vinpin.common.LoginOwner
import com.vinpin.common.RouterConstants
import com.vinpin.common.net.KcRetrofitUtils
import com.vinpin.common.net.tryCatchWithIo
import com.vinpin.common.util.UserInfoUtils
import com.vinpin.commonutils.ResourcesUtils
import com.vinpin.commonutils.SizeUtils
import com.vinpin.selectorhelper.ShapeHelper
import com.vinpin.selectorhelper.ShapeSelector
import com.vinpin.wanandroid.main.R
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/3 9:26
 *     desc  : 登陆Fragment
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
        v_logo.background = ShapeHelper.getInstance().solidColor(R.color.login_top)
            .tlRadius(SizeUtils.dp2px(20f).toFloat())
            .trRadius(SizeUtils.dp2px(20f).toFloat())
            .create()
        txt_login.background = ShapeSelector.getInstance().enabled(
            false, ShapeHelper.getInstance()
                .solidColor(R.color.login_button_disable)
                .cornerRadius(SizeUtils.dp2px(20f).toFloat())
                .create()
        ).defaultShape(
            ShapeHelper.getInstance()
                .solidColor(R.color.login_button)
                .cornerRadius(SizeUtils.dp2px(20f).toFloat())
                .create()
        ).create()
        txt_login.isEnabled = false
        initClickListener()
    }

    override fun onStart() {
        super.onStart()
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

    private fun initClickListener() {
        img_close.setOnClickListener {
            dismiss()
        }
        txt_login.setOnClickListener {
            val account = et_account.text.toString()
            val password = et_password.text.toString()
            login(account, password)
        }
        et_account.addTextChangedListener {
            if (it?.toString()?.isEmpty() == true) {
                txt_login.isEnabled = false
            } else {
                txt_login.isEnabled = et_password.text.toString().isNotEmpty()
            }
        }
        et_password.addTextChangedListener {
            if (it?.toString()?.isEmpty() == true) {
                txt_login.isEnabled = false
            } else {
                txt_login.isEnabled = et_account.text.toString().isNotEmpty()
            }
        }
    }

    private fun login(account: String, password: String) {
        launch {
            txt_login.isEnabled = false
            val apiResponse = tryCatchWithIo {
                KcRetrofitUtils.getApi().login(account, password)
            }
            txt_login.isEnabled = true
            apiResponse.getOrNull()?.let {
                UserInfoUtils.getInstance().saveUserInfo(it)
                dismiss()
            }
            apiResponse.exceptionOrNull()?.let {
                Toast.makeText(context, it.errorMsg, Toast.LENGTH_SHORT).show()
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

    companion object {
        private const val TAG = "login.LoginFragment"
    }
}