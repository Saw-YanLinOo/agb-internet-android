package com.agb.billing.customer.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.agb.billing.customer.BuildConfig
import com.agb.billing.customer.R
import com.agb.billing.customer.databinding.ActivityProfileBinding
import com.agb.billing.customer.networks.requests.UpdateProfileRequest
import com.agb.billing.customer.networks.responses.ProfileResponse
import com.agb.billing.customer.networks.responses.SuccessResponse
import com.agb.billing.customer.utils.DialogUtil
import com.agb.billing.customer.viewmodels.ProfileViewModel
import com.agb.billing.customer.views.ProfileView

class ProfileActivity : BaseActivity(), ProfileView {

    lateinit var binding: ActivityProfileBinding
    lateinit var mViewModel: ProfileViewModel
    private var mResponse: ProfileResponse? = null

    companion object {
        fun newInstance(mContext: Context): Intent {
            return Intent(mContext, ProfileActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        clickEvent()
        viewLayoutVisible(false)
        profileApiCall()
        editModeOnOff(false)
    }

    private fun initLayout() {
        mViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        mViewModel.setView(this)

        //test
//        val oneWord = "+5000 MMK"
//        val twoWord = "-100 USD"

//        binding.apply {
//            val spanable1 = SpannableString(oneWord)
//            spanable1.setSpan(ForegroundColorSpan(Color.BLUE),0,spanable1.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//            tvBalance.setText(spanable1)
//            tvBalance.append(", ")
//            val spanable2 = SpannableString(twoWord)
//            spanable2.setSpan(ForegroundColorSpan(resources.getColor(R.color.colorRed)),0,spanable2.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//            tvBalance.append(spanable2)
//
//        }
        val versionNo ="Version:" + BuildConfig.VERSION_NAME
        binding.tvVersionNo.text=versionNo
    }

    fun profileApiCall() {
        mViewModel.getProfile()
    }

    private fun clickEvent() {
        binding.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }

            btnEdit.setOnClickListener {
                editModeOnOff(true)
            }

            btnCancel.setOnClickListener {
                if (mResponse != null) {
                    setData(mResponse!!)
                }
                editModeOnOff(false)
            }

            btnSave.setOnClickListener {
                updateProfileApiCall()
            }
        }
    }

    private fun updateProfileApiCall() {
        viewLayoutVisible(false)
        binding.apply {
            val request = UpdateProfileRequest()
            request.viberno = tvViberNo.text.toString()
            request.email = tvEmail.text.toString()
            request.phone = tvPhone.text.toString()
            request.alterPhNos = tvAltPhone.text.toString()
            mViewModel.getProfileUpdate(request)
        }
    }

    private fun editModeOnOff(b: Boolean) {
        binding.apply {
            if (b) {
                tvPhone.setReadOnly(true)
                tvEmail.setReadOnly(true)
                tvViberNo.setReadOnly(true)
                tvAltPhone.setReadOnly(true)

                tvPhone.setSelection(tvPhone.text.toString().length)
                tvPhone.requestFocus()

                btnEdit.visibility = View.GONE
                btnCancel.visibility = View.VISIBLE
                btnSave.visibility = View.VISIBLE
            } else {

                tvPhone.setReadOnly(false)
                tvEmail.setReadOnly(false)
                tvViberNo.setReadOnly(false)
                tvAltPhone.setReadOnly(false)

                btnEdit.visibility = View.VISIBLE
                btnCancel.visibility = View.GONE
                btnSave.visibility = View.GONE
            }
        }
    }

    private fun viewLayoutVisible(b: Boolean) {
        binding.apply {
            lyError.layoutError.visibility = View.GONE
            if (b) {
                shimmerLoading.stopShimmer()
                cvProfile.visibility = View.VISIBLE
                lyEdit.visibility = View.VISIBLE
                shimmerLoading.visibility = View.GONE
            } else {
                shimmerLoading.startShimmer()
                cvProfile.visibility = View.GONE
                lyEdit.visibility = View.GONE
                shimmerLoading.visibility = View.VISIBLE
            }
        }
    }

    override fun setData(response: ProfileResponse) {
        viewLayoutVisible(true)
        mResponse = response
        if (response.data != null) {
            binding.apply {
                tvUserName.text = response.data?.username
                tvPhone.setText(response.data?.phone)
                tvViberNo.setText(response.data?.viberno)
                tvEmail.setText(response.data?.email)
                tvAltPhone.setText(response.data?.alter_phnumbers)
                tvActivationDate.text = response.data?.activationdate
                tvMembershipId.text = response.data?.memberid

                if (response.data?.viberno.isNullOrEmpty()) {
                    tvViberNo.setText("-")
                }

                if (response.data?.email.isNullOrEmpty()) {
                    tvEmail.setText("-")
                }

                if (response.data?.memberid.isNullOrEmpty()) {
                    tvMembershipId.text = "-"
                }

                if (response.data?.activationdate.isNullOrEmpty()) {
                    tvActivationDate.text = "-"
                }

                if (response.data?.alter_phnumbers.isNullOrEmpty()) {
                    tvAltPhone.setText("-")
                }
            }

            if (response.data?.balance!!.size > 0) {
                val list = response.data?.balance!!
                for (i in 0 until list.size) {
                    val spanable1 = SpannableString(list[i].value)
                    spanable1.setSpan(ForegroundColorSpan(Color.parseColor(list[i].colorcode.toString())),
                        0,
                        spanable1.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    if (i == list.size - 1) {
                        binding.tvBalance.append(spanable1)
                    } else {
                        if (i == 0) {
                            binding.tvBalance.setText(spanable1)
                            binding.tvBalance.append(", ")
                        } else {
                            binding.tvBalance.append(spanable1)
                            binding.tvBalance.append(", ")
                        }
                    }
                }
            }
        }
    }

    override fun setUpdateSuccess(response: SuccessResponse) {
        binding.apply {
            mResponse?.data?.phone = tvPhone.text.toString()
            mResponse?.data?.email = tvEmail.text.toString()
            mResponse?.data?.viberno = tvViberNo.text.toString()
            mResponse?.data?.alter_phnumbers = tvAltPhone.text.toString()
        }
        viewLayoutVisible(true)
        editModeOnOff(false)
        Toast.makeText(this, response.responseMessage.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun showError(message: String, code: String) {
        //  hideLoadingNetwork()
        showErrorMessage(message, code)
    }

    override fun showInvalidSession(message: String, code: String) {
        mInvalidSession(this, message)
    }

    override fun showNetworkFailed() {
        hideLoadingNetwork()
    }

    private fun hideLoadingError() {
        binding.apply {
            shimmerLoading.stopShimmer()
            lyMain.visibility = View.GONE
            lyError.layoutError.visibility = View.VISIBLE
//            lyError.ivError.setImageResource(R.drawable.ic_component_invoices)
//            lyError.tvError.text = resources.getString(R.string.error_invoice)
        }
    }

    private fun hideLoadingNetwork() {

        binding.apply {
            shimmerLoading.stopShimmer()
            lyMain.visibility = View.GONE
            lyError.layoutError.visibility = View.VISIBLE

        }

    }

    private fun showErrorMessage(message: String, code: String) {

        viewLayoutVisible(true)
        editModeOnOff(true)
        DialogUtil(this).showErrorDialog(getString(R.string.errorTitle), message)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.right_in, R.anim.right_out)
        finish()
    }

    private fun EditText.setReadOnly(value: Boolean) {
        this.isEnabled = value
        when (value) {
            true -> {
                this.gravity = Gravity.CENTER or Gravity.START
                this.setPadding(24, 4, 24, 4)
                this.setBackgroundResource(R.drawable.bg_text_profile)
            }
            false -> {
                this.gravity = Gravity.CENTER or Gravity.END
                this.setPadding(0, 4, 0, 4)
                this.setBackgroundResource(R.drawable.bg_et_search)
            }
        }
    }

}