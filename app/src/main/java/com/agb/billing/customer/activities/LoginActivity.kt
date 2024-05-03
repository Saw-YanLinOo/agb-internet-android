package com.agb.billing.customer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.agb.billing.customer.activities.BaseActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.agb.billing.customer.R
import com.agb.billing.customer.databinding.ActivityLoginBinding
import com.agb.billing.customer.localizations.LocaleManager
import com.agb.billing.customer.networks.requests.LoginRequest
import com.agb.billing.customer.networks.responses.LoginResponse
import com.agb.billing.customer.utils.AsteriskPasswordTransformationMethod
import com.agb.billing.customer.utils.Constants
import com.agb.billing.customer.utils.DialogUtil
import com.agb.billing.customer.utils.PreferenceUtils
import com.agb.billing.customer.viewmodels.LoginViewModel
import com.agb.billing.customer.views.LoginView

class LoginActivity : BaseActivity(), LoginView {

    lateinit var binding: ActivityLoginBinding
    lateinit var mViewModel: LoginViewModel
    private var userName = ""
    private var passwordStatus = true
    private var mToken: String? = ""

    companion object {
        fun newInstance(mContext: Context): Intent {
            return Intent(mContext, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        LocaleManager(this).setLocale(this)
        setContentView(binding.root)

        getFirebaseToken()
        initLayout()
        initViewModel()
        clickEvent()
        checkLogin()
    }

    private fun checkLogin() {
        val dataLogin = PreferenceUtils.getUser()
        if (dataLogin.sessionId != "") {
            gotoMainActivity()
        }
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        mViewModel.setView(this)
    }

    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            mToken = token.toString()
            // Log and toast
            Log.e("TOKEN", mToken.toString())
        })
    }

    fun loginApiCall() {
        showProgress()
        userName = binding.etUsername.text.toString()
        val request = LoginRequest()
        request.username = binding.etUsername.text.toString()
        request.password = binding.etPassword.text.toString()
        request.deviceToken = mToken
        mViewModel.getLogin(request)
    }

    private fun clickEvent() {
        binding.apply {
            btnLogin.setOnClickListener {
                if (binding.etUsername.text.toString()
                        .isNotEmpty() && binding.etPassword.text.toString().isNotEmpty()
                ) {
                    loginApiCall()
                } else if (binding.etUsername.text.toString().isEmpty()) {
                    binding.etUsername.error = getString(R.string.require)
                } else {
                    binding.etPassword.error = getString(R.string.require)
                }
            }

            ivPassword.setOnClickListener {
                if (passwordStatus) {
                    passwordStatus = false
                    etPassword.transformationMethod = null
                    ivPassword.setImageResource(R.drawable.ic_baseline_visibility_24)
                } else {
                    passwordStatus = true
                    etPassword.transformationMethod = AsteriskPasswordTransformationMethod
                    ivPassword.setImageResource(R.drawable.ic_baseline_visibility_off_24)
                }
                etPassword.setSelection(etPassword.length())
            }
        }
    }

    private fun initLayout() {
        binding.etPassword.transformationMethod = AsteriskPasswordTransformationMethod
    }

    private fun gotoMainActivity() {
        startActivity(MainActivity.newInstance(this@LoginActivity))
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out)
    }

    private fun gotoConfirmPasswordActivity() {
        startActivity(ConfirmPasswordActivity.newInstance(this@LoginActivity, userName))
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
        finish()
    }

    override fun setData(response: LoginResponse) {
        dismissProgress()
        PreferenceUtils.setUser(response.data!!)
        gotoMainActivity()
    }

    override fun showError(message: String, code: String) {
        Log.e("LOGIN_ERROR", "$code-----$message")
        dismissProgress()
        if (code == Constants.FIRST_TIME_LOGIN_CODE) {
            gotoConfirmPasswordActivity()
        } else {
            DialogUtil(this).showErrorDialog(getString(R.string.errorTitle), message)
        }
    }

    override fun showInvalidSession(message: String, code: String) {
        dismissProgress()
        mInvalidSession(this, message)
    }

    override fun showNetworkFailed() {
        dismissProgress()
        DialogUtil(this).showErrorDialog(getString(R.string.errorTitle), Constants.CONNECTION_FAIL)
    }

    private fun showProgress() {
        try {
            showProgressDialog?.show()
        } catch (_: Exception) {

        }

    }

    private fun dismissProgress() {
        try {
            showProgressDialog?.dismiss()
        } catch (_: Exception) {

        }
    }

}