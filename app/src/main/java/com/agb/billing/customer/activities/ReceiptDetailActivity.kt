package com.agb.billing.customer.activities

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.agb.billing.customer.activities.BaseActivity
import com.agb.billing.customer.R
import com.agb.billing.customer.databinding.ActivityReceiptDetailBinding
import com.agb.billing.customer.networks.requests.ReceiptDetailRequest
import com.agb.billing.customer.networks.responses.InvoiceDetailResponse
import com.agb.billing.customer.utils.Constants
import com.agb.billing.customer.viewmodels.ReceiptDetailViewModel
import com.agb.billing.customer.views.ReceiptDetailView
import com.google.gson.Gson

class ReceiptDetailActivity : BaseActivity(),ReceiptDetailView {

    lateinit var binding : ActivityReceiptDetailBinding
    lateinit var mViewModel : ReceiptDetailViewModel
    lateinit var mManager : DownloadManager
    var downLoadUrl = ""

    companion object{
        var mInvNumber : String ?= ""
        fun newInstance(mContext : Context,mData : String) : Intent{
            mInvNumber = mData
            return Intent(mContext,ReceiptDetailActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiptDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        initViewModel()
        clickEvent()
        viewLayoutVisible(false)
        receiptDetailApiCall()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(ReceiptDetailViewModel::class.java)
        mViewModel.setView(this)
    }

    private fun receiptDetailApiCall() {
        val request = ReceiptDetailRequest()
        request.invnumber = mInvNumber
        request.invType = Constants.RECEIPT
        mViewModel.getReceiptDetail(request)
    }

    private fun initLayout() {

    }

    private fun clickEvent() {
        binding.apply {
            ivBack.setOnClickListener { onBackPressed() }

            btnDownload.setOnClickListener {
                if(downLoadUrl.isNotEmpty()) {
                    downLoadFunction(downLoadUrl)
                }else{
                    Toast.makeText(this@ReceiptDetailActivity,"Receipt Not Found!",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun viewLayoutVisible(b: Boolean) {
        binding.apply {
            if (b) {
                shimmerLoading.stopShimmer()
                cvReceipt.visibility = View.VISIBLE
                btnDownload.visibility = View.VISIBLE
                shimmerLoading.visibility = View.GONE
            } else {
                shimmerLoading.startShimmer()
                cvReceipt.visibility = View.GONE
                btnDownload.visibility = View.GONE
                shimmerLoading.visibility = View.VISIBLE
            }
        }
    }

    override fun setReceiptDetail(response: InvoiceDetailResponse) {
        viewLayoutVisible(true)
        if(response.data != null){
            val mData = response.data
            Log.e("RECEIPT",Gson().toJson(mData))
            downLoadUrl = mData?.pdfUrl.toString()
            binding.apply {
                tvReceiptId.text = mData?.receiptid
                tvAmount.text = mData?.totalcost
                tvPaidDate.text = mData?.paiddate
                tvPaymentType.text = mData?.payment_provider
                tvServiceId.text = mData?.invnumber
                tvTransactionId.text = mData?.transaction_id
            }
        }
    }

    override fun showError(message: String, code: String) {
        hideLoadingError()
    }

    override fun showInvalidSession(message: String, code: String) {
        mInvalidSession(this,message)
    }

    override fun showNetworkFailed() {
        hideLoadingNetwork()
    }

    private fun hideLoadingError() {
        binding.apply {
            shimmerLoading.stopShimmer()
            shimmerLoading.visibility = View.GONE
            cvReceipt.visibility = View.GONE
            btnDownload.visibility = View.GONE
            lyError.layoutError.visibility = View.VISIBLE
            lyError.ivError.setImageResource(R.drawable.ic_component_invoices)
            lyError.tvError.text = resources.getString(R.string.error_invoice)
        }
    }

    private fun hideLoadingNetwork() {
        binding.apply {
            shimmerLoading.stopShimmer()
            shimmerLoading.visibility = View.GONE
            cvReceipt.visibility = View.GONE
            btnDownload.visibility = View.GONE
            lyError.layoutError.visibility = View.VISIBLE
        }
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

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.right_in, R.anim.right_out)
        finish()
    }

    fun downLoadFunction(url : String){
        Toast.makeText(this,"Start downloading...",Toast.LENGTH_SHORT).show()
        mManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
//        val uri: Uri =
//            Uri.parse("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf")
        val uri: Uri =
            Uri.parse(url)
//        val stringArray = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf".split("/")
//        Toast.makeText(this,stringArray[stringArray.lastIndex],Toast.LENGTH_SHORT).show()
        val request = DownloadManager.Request(uri)
        request.setAllowedNetworkTypes(
            DownloadManager.Request.NETWORK_WIFI or
                    DownloadManager.Request.NETWORK_MOBILE
        )
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        val reference: Long = mManager.enqueue(request)
    }

}