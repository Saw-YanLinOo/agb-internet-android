package com.agb.customer.billing.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.agb.customer.billing.activities.BaseActivity
import com.agb.customer.billing.R
import com.agb.customer.billing.databinding.ActivityConfirmPasswordBinding
import com.agb.customer.billing.networks.requests.ResetPasswordRequest
import com.agb.customer.billing.networks.responses.LoginResponse
import com.agb.customer.billing.utils.AsteriskPasswordTransformationMethod
import com.agb.customer.billing.utils.Constants
import com.agb.customer.billing.utils.DialogUtil
import com.agb.customer.billing.utils.PreferenceUtils
import com.agb.customer.billing.viewmodels.ResetPasswordViewModel
import com.agb.customer.billing.views.ResetPasswordView

class ConfirmPasswordActivity : BaseActivity(),ResetPasswordView {

    lateinit var binding : ActivityConfirmPasswordBinding
    lateinit var mViewModel : ResetPasswordViewModel
    var newPassStatus = true
    var comPassStatus = true

    companion object{
        var userName = ""
        fun newInstance(mContext : Context,mUser : String) : Intent{
            userName = mUser
            return Intent(mContext,ConfirmPasswordActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        clickEvent()
    }

    private fun initLayout() {
        mViewModel = ViewModelProvider(this).get(ResetPasswordViewModel::class.java)
        mViewModel.setView(this)

        binding.apply {
            etNewPassword.transformationMethod = AsteriskPasswordTransformationMethod
            etConfirmPassword.transformationMethod = AsteriskPasswordTransformationMethod
        }
    }

    private fun clickEvent() {
        binding.apply {
            ivBack.setOnClickListener {
                finish()
            }

            btnConfirm.setOnClickListener {
                confirmPasswordCheck()
            }

            ivNewPassword.setOnClickListener {
                if (newPassStatus) {
                    newPassStatus = false
                    etNewPassword.transformationMethod = null
                    ivNewPassword.setImageResource(R.drawable.ic_baseline_visibility_24)
                } else {
                    newPassStatus = true
                    etNewPassword.transformationMethod = AsteriskPasswordTransformationMethod
                    ivNewPassword.setImageResource(R.drawable.ic_baseline_visibility_off_24)
                }
                etNewPassword.setSelection(etNewPassword.length())
            }

            ivConfirmPassword.setOnClickListener {
                if (comPassStatus) {
                    comPassStatus = false
                    etConfirmPassword.transformationMethod = null
                    ivConfirmPassword.setImageResource(R.drawable.ic_baseline_visibility_24)
                } else {
                    comPassStatus = true
                    etConfirmPassword.transformationMethod = AsteriskPasswordTransformationMethod
                    ivConfirmPassword.setImageResource(R.drawable.ic_baseline_visibility_off_24)
                }
                etConfirmPassword.setSelection(etConfirmPassword.length())
            }

        }
    }

    private fun confirmPasswordCheck() {
        binding.apply {
            val newPass = etNewPassword.text.toString()
            val conPass = etConfirmPassword.text.toString()
            if(newPass.isNotEmpty() && conPass.isNotEmpty()) {
                if (newPass == conPass) {
                    resetPasswordApiCall(newPass, conPass)
                } else {
                    Toast.makeText(
                        this@ConfirmPasswordActivity,
                        "Password does not match so please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else if(newPass.isEmpty()){
                etNewPassword.error = getString(R.string.require)
            }else{
                etConfirmPassword.error = getString(R.string.require)
            }
        }
    }

    fun resetPasswordApiCall(newPass : String,confPass : String){
        showProgress()
        val request = ResetPasswordRequest()
        request.confirmPassword = confPass
        request.newPassword = newPass
        request.username = userName
        request.deviceToken = "zzz"
        mViewModel.getResetPassword(request)
    }

    override fun setData(response: LoginResponse) {
        dismissProgress()
        PreferenceUtils.setUser(response.data!!)
        gotoMainActivity()
    }

    fun gotoMainActivity(){
        Toast.makeText(this,"Change Password Success!",Toast.LENGTH_SHORT).show()
        startActivity(MainActivity.newInstance(this@ConfirmPasswordActivity))
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out)
    }

    override fun showError(message: String, code: String) {
        dismissProgress()
            DialogUtil(this).showErrorDialog(getString(R.string.errorTitle), message)
    }

    override fun showInvalidSession(message: String, code: String) {
        dismissProgress()
        mInvalidSession(this,message)
    }

    override fun showNetworkFailed() {
        dismissProgress()
        DialogUtil(this).showErrorDialog(getString(R.string.errorTitle), Constants.CONNECTION_FAIL)
    }

    private fun showProgress() {
        try {
            showProgressDialog?.show()
        } catch (ex: Exception) {

        }

    }

    private fun dismissProgress() {
        try {
            showProgressDialog?.dismiss()
        } catch (ex: Exception) {

        }
    }

}