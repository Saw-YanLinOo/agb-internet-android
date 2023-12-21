package com.agb.billing.customer.activities

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.agb.billing.customer.activities.BaseActivity
import com.agb.billing.customer.R
import com.agb.billing.customer.adapter.PhoneAdapter
import com.agb.billing.customer.databinding.ActivitySupportBinding
import com.agb.billing.customer.delegate.SupportDelegate
import com.agb.billing.customer.networks.responses.SupportResponse
import com.agb.billing.customer.viewmodels.SupportViewModel
import com.agb.billing.customer.views.SupportView

class SupportActivity : BaseActivity(),SupportView,SupportDelegate {

    lateinit var binding : ActivitySupportBinding
    lateinit var mViewModel : SupportViewModel
    lateinit var mPhoneAdapter : PhoneAdapter

    companion object{
        fun newInstance(mContext : Context) : Intent{
            return Intent(mContext,SupportActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupportBinding.inflate(layoutInflater)
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
            rvSupportPhone.layoutManager = LinearLayoutManager(this@SupportActivity)
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
                    intent.putExtra(Intent.EXTRA_TEXT,"")
                    intent.setData(Uri.parse("mailto:"))
                    startActivity(intent)
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(this@SupportActivity,"There are no email client installed on your device.",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun viewLayoutVisible(b: Boolean) {
        binding.apply {
            lyError.layoutError.visibility = View.GONE
            if (b) {
                shimmerLoading.stopShimmer()
                cvSupport.visibility = View.VISIBLE
                shimmerLoading.visibility = View.GONE
            } else {
                shimmerLoading.startShimmer()
                cvSupport.visibility = View.GONE
                shimmerLoading.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.right_in, R.anim.right_out)
        finish()
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

    override fun setSupport(response: SupportResponse) {
        viewLayoutVisible(true)
        if(response.data != null){
            binding.apply {
                tvMail.text = response.data?.mail
                tvAddress.text = response.data?.address
                if(response.data?.phoneNo.toString().contains(",")){
                    val mPlist = response.data?.phoneNo.toString().split(",")
                    mPhoneAdapter.setNewData(mPlist.toMutableList())
                }else{
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
        mInvalidSession(this,message)
    }

    override fun showNetworkFailed() {
        hideLoadingNetwork()
    }

    override fun onTapPhone(data: String) {
        if(data != "") {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.setData(Uri.parse("tel:$data"))
            startActivity(intent)
        }
    }

}