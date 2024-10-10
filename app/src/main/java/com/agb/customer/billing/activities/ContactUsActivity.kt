package com.agb.customer.billing.activities

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.agb.customer.billing.R
import com.agb.customer.billing.adapter.PhoneAdapter
import com.agb.customer.billing.databinding.ActivityContactUsBinding
import com.agb.customer.billing.delegate.SupportDelegate
import com.agb.customer.billing.networks.responses.SupportResponse
import com.agb.customer.billing.viewmodels.SupportViewModel
import com.agb.customer.billing.views.SupportView

class ContactUsActivity : BaseActivity(), SupportView, SupportDelegate {

    lateinit var binding: ActivityContactUsBinding
    lateinit var mViewModel: SupportViewModel
    lateinit var mPhoneAdapter: PhoneAdapter

    companion object {
        fun newInstance(mContext: Context): Intent {
            return Intent(mContext, ContactUsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        initViewModel()
        clickEvent()
        viewLayoutVisible(false)
        apiCall()

    }

    private fun apiCall() {
        mViewModel.getSupport()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(SupportViewModel::class.java)
        mViewModel.setView(this)
    }

    private fun initLayout() {
        mPhoneAdapter = PhoneAdapter(this)
        binding.apply {
            swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorSecondary))

            swipeRefreshLayout.setOnRefreshListener {
                viewLayoutVisible(false)
                apiCall()
            }
            rvSupportPhone.layoutManager = LinearLayoutManager(this@ContactUsActivity)
            rvSupportPhone.setHasFixedSize(true)
            rvSupportPhone.adapter = mPhoneAdapter
        }
    }

    private fun clickEvent() {
        binding.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }

            tvMail.setOnClickListener {
                try {
                    val intent = Intent(Intent.ACTION_SENDTO)
                    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("${tvMail.text.toString()}"))
                    intent.putExtra(Intent.EXTRA_TEXT, "")
                    intent.setData(Uri.parse("mailto:"))
                    startActivity(intent)
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(
                        this@ContactUsActivity,
                        "There are no email client installed on your device.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun viewLayoutVisible(b: Boolean) {
        binding.apply {
            lyError.layoutError.visibility = View.GONE
            if (b) {
                shimmerLoading.stopShimmer()
                binding.lyContactUs.visibility = View.VISIBLE
                shimmerLoading.visibility = View.GONE
            } else {
                shimmerLoading.startShimmer()
                shimmerLoading.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.right_in, R.anim.right_out)
        finish()
    }
    private fun hideLoadingNetwork() {

        binding.apply {
            swipeRefreshLayout.isRefreshing = false
            shimmerLoading.stopShimmer()
            lyContactUs.visibility = View.INVISIBLE
            shimmerLoading.visibility = View.GONE
            lyError.layoutError.visibility = View.VISIBLE
        }
    }

    override fun setSupport(response: SupportResponse) {
        viewLayoutVisible(true)
        binding.swipeRefreshLayout.isRefreshing = false
        if (response.data != null) {
            binding.apply {
                tvMail.text = response.data?.mail
                tvAddress.text = response.data?.address
                if (response.data?.phoneNo.toString().contains(",")) {
                    val mPlist = response.data?.phoneNo.toString().split(",")
                    mPhoneAdapter.setNewData(mPlist.toMutableList())
                } else {
                    val list = mutableListOf<String>()
                    list.add(response.data?.phoneNo.toString())
                    mPhoneAdapter.setNewData(list)

                }
            }
        }
    }

    override fun showError(message: String, code: String) {
        hideLoadingNetwork()
    }

    override fun showInvalidSession(message: String, code: String) {
        binding.swipeRefreshLayout.isRefreshing = false
        mInvalidSession(this, message)
    }

    override fun showNetworkFailed() {
        hideLoadingNetwork()
    }

    override fun onTapPhone(data: String) {
        if (data != "") {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.setData(Uri.parse("tel:$data"))
            startActivity(intent)
        }
    }

}