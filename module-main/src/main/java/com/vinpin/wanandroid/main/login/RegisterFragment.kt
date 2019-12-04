package com.vinpin.wanandroid.main.login

import androidx.core.widget.addTextChangedListener
import com.vinpin.common.LoginOwner
import com.vinpin.common.base.BaseFragment
import com.vinpin.common.net.KcRetrofitUtils
import com.vinpin.common.net.tryCatchWithIo
import com.vinpin.common.shortToast
import com.vinpin.common.util.UserInfoUtils
import com.vinpin.commonutils.KeyboardUtils
import com.vinpin.commonutils.SizeUtils
import com.vinpin.selectorhelper.ShapeHelper
import com.vinpin.selectorhelper.ShapeSelector
import com.vinpin.wanandroid.main.R
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.launch

/**
 * <pre>
 *     author: VinPin
 *     time  : 2019/12/4 13:34
 *     desc  : 注册Fragment
 * </pre>
 */
class RegisterFragment : BaseFragment() {

    override val layoutId: Int get() = R.layout.fragment_register

    override fun getData() {
        et_account.background = ShapeHelper.getInstance()
            .solidColor(R.color.login_input)
            .cornerRadius(SizeUtils.dp2px(5f).toFloat())
            .create()

        et_password.background = ShapeHelper.getInstance()
            .solidColor(R.color.login_input)
            .cornerRadius(SizeUtils.dp2px(5f).toFloat())
            .create()

        et_repassword.background = ShapeHelper.getInstance()
            .solidColor(R.color.login_input)
            .cornerRadius(SizeUtils.dp2px(5f).toFloat())
            .create()

        txt_register.background = ShapeSelector.getInstance().enabled(
            false, ShapeHelper.getInstance()
                .solidColor(R.color.login_button_disable)
                .cornerRadius(SizeUtils.dp2px(23f).toFloat())
                .create()
        ).defaultShape(
            ShapeHelper.getInstance()
                .solidColor(R.color.login_button)
                .cornerRadius(SizeUtils.dp2px(23f).toFloat())
                .create()
        ).create()

        initClickListener()
    }

    override fun getLazyData() {
    }

    private fun initClickListener() {
        txt_to_login.setOnClickListener {
            toLogin()
        }
        txt_register.setOnClickListener {
            val account = et_account.text.trim().toString()
            val password = et_password.text.trim().toString()
            val repassword = et_repassword.text.trim().toString()
            if (password != repassword) {
                "两次密码不一致".shortToast(context!!)
                return@setOnClickListener
            }
            register(account, password)
        }
        et_account.addTextChangedListener {
            checkButtonEnable()
        }
        et_password.addTextChangedListener {
            checkButtonEnable()
        }
        et_repassword.addTextChangedListener {
            checkButtonEnable()
        }
    }

    private fun checkButtonEnable() {
        val account = et_account.text.trim().toString()
        val password = et_password.text.trim().toString()
        val repassword = et_repassword.text.trim().toString()
        txt_register.isEnabled =
            account.isNotEmpty() && password.isNotEmpty() && repassword.isNotEmpty()
    }

    private fun register(account: String, password: String) {
        launch {
            txt_register.isEnabled = false
            KeyboardUtils.hideSoftInput(et_account)
            val apiResponse = tryCatchWithIo {
                KcRetrofitUtils.getApi().register(account, password, password)
            }
            txt_register.isEnabled = true
            apiResponse.getOrNull()?.let {
                UserInfoUtils.saveUserInfo(it)
                dismiss()
            }
            apiResponse.exceptionOrNull()?.errorMsg.shortToast(context!!)
        }
    }

    private fun dismiss() {
        parentFragment?.let {
            (it as? LoginOwner)?.dismiss()
        }
        activity?.let {
            (it as? LoginOwner)?.dismiss()
        }
    }

    private fun toLogin() {
        parentFragment?.let {
            (it as? LoginOwner)?.switch(0)
        }
        activity?.let {
            (it as? LoginOwner)?.switch(0)
        }
    }
}