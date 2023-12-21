package com.agb.billing.customer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.agb.billing.customer.activities.BaseActivity
import com.agb.billing.customer.R
import com.agb.billing.customer.adapter.InvoiceAdapter
import com.agb.billing.customer.databinding.ActivityInvoicesBinding
import com.agb.billing.customer.delegate.InvoiceDelegate
import com.agb.billing.customer.modelVO.InvoiceVO
import com.agb.billing.customer.networks.requests.InvoiceListRequest
import com.agb.billing.customer.networks.responses.InvoiceListResponse
import com.google.gson.Gson
import com.agb.billing.customer.viewmodels.InvoiceListViewModel
import com.agb.billing.customer.views.InvoiceListView

class InvoiceActivity : BaseActivity(), InvoiceDelegate, InvoiceListView {

    lateinit var binding: ActivityInvoicesBinding
    lateinit var mAdapter: InvoiceAdapter
    lateinit var mViewModel: InvoiceListViewModel
    var pageNo = 1
    var prePageNo = 2
    var mList: MutableList<InvoiceVO> = mutableListOf()
    var sorting = 1//1 is descending and 2 ascending,init value is default

    companion object {
        fun newInstance(mContext: Context): Intent {
            return Intent(mContext, InvoiceActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvoicesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        initViewModel()
        clickEvent()
//        viewLayoutVisible(false)
//        invoiceListApiCall()
    }

    override fun onResume() {
        super.onResume()
        viewLayoutVisible(false)
        invoiceListApiCall()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(InvoiceListViewModel::class.java)
        mViewModel.setView(this)
    }

    fun invoiceListApiCall() {
        val request = InvoiceListRequest()
        request.pageNo = pageNo
        mViewModel.getInvoiceList(request)
    }

    private fun initLayout() {
        mAdapter = InvoiceAdapter(this)
        binding.apply {

            swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorSecondary))
            swipeRefreshLayout.setOnRefreshListener {
                viewLayoutVisible(false)
                etSearchInvoices.setText("")
                etSearchInvoices.clearFocus()
                ivFilter.setImageResource(R.drawable.ic_invoice_filter)
                sorting = 2
                pageNo = 1
                prePageNo = 2
                invoiceListApiCall()
            }

            rvInvoices.layoutManager = LinearLayoutManager(this@InvoiceActivity)
            rvInvoices.setHasFixedSize(true)
            rvInvoices.adapter = mAdapter
//            mAdapter.setNewData(SampleData.invoiceList())

            etSearchInvoices.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (p0.toString().isNotEmpty()) {
                        if (mList.size > 0) {
                            val list = mList.filter {
                                it.invnumber!!.toLowerCase().contains(
                                    p0.toString().toLowerCase()
                                ) || it.specialcode!!.toLowerCase().contains(
                                    p0.toString().toLowerCase()
                                ) || it.expirationdate!!.toLowerCase()
                                    .contains(p0.toString().toLowerCase())
                            }
                            mAdapter.setNewData(list.toMutableList())
                        }
                    } else {
                        mAdapter.setNewData(mList)
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

//            rvInvoices.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    if ((rvInvoices.layoutManager as LinearLayoutManager).itemCount >= 3) {
//                        if (((rvInvoices.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() + 1) == (rvInvoices.layoutManager as LinearLayoutManager).itemCount) {
//                            if (prePageNo >= pageNo) {
//                                if (prePageNo == pageNo) {
//                                    prePageNo += 1
//                                }
//                                showProgress()
//                                //api call
//                                invoiceListApiCall()
//                            }
//                        }
//                    }
//                    super.onScrolled(recyclerView, dx, dy)
//                }
//
//            })
        }
    }

    private fun clickEvent() {
        binding.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }

            lyFilter.setOnClickListener {
                if (mList.size > 0) {
                    if (sorting == 2) {
                        sorting = 1
                        mList.sortByDescending { it.issueDate }
                        mAdapter.setNewData(mList)
                        ivFilter.setImageResource(R.drawable.ic_invoice_filter)
                    } else {
                        sorting = 2
                        mList.sortBy { it.invnumber }
                        mAdapter.setNewData(mList)
                        ivFilter.setImageResource(R.drawable.ic_vector__accending)
                    }
                }
            }
        }
    }

    override fun onTapInvoice(data: InvoiceVO) {
        startActivity(InvoiceDetailActivity.newInstance(this, data))
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
    }

    private fun viewLayoutVisible(b: Boolean) {
        binding.apply {
            if (b) {
                shimmerLoading.stopShimmer()
                cvInvoices.visibility = View.VISIBLE
                shimmerLoading.visibility = View.GONE
            } else {
                shimmerLoading.startShimmer()
                cvInvoices.visibility = View.GONE
                shimmerLoading.visibility = View.VISIBLE
            }
        }
    }

    override fun setData(response: InvoiceListResponse) {
        viewLayoutVisible(true)
        dismissProgress()
        binding.swipeRefreshLayout.isRefreshing = false

        if (response.data != null && response.data!!.size > 0) {
            Log.e("INVOICE_LIST", Gson().toJson(response.data))
            mList = response.data!!
            mAdapter.setNewData(mList)
        } else {
            hideLoadingError()
        }
    }

    override fun showError(message: String, code: String) {
        hideLoadingNetwork()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    override fun showInvalidSession(message: String, code: String) {
        mInvalidSession(this, message)
        binding.swipeRefreshLayout.isRefreshing = false
    }

    override fun showNetworkFailed() {
        hideLoadingNetwork()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    override fun showVersionUpdate(message: String, storeUrl: String) {
        showVersionUpdateDialog(message,storeUrl)
        viewLayoutVisible(true)
        dismissProgress()

    }


    private fun hideLoadingError() {
        binding.apply {
            shimmerLoading.stopShimmer()
            shimmerLoading.visibility = View.GONE
            cvInvoices.visibility = View.GONE
            lyError.layoutError.visibility = View.VISIBLE
            lyError.ivError.setImageResource(R.drawable.ic_component_invoices)
            lyError.tvError.text = resources.getString(R.string.error_invoice)
        }
    }

    private fun hideLoadingNetwork() {
        binding.apply {
            shimmerLoading.stopShimmer()
            shimmerLoading.visibility = View.GONE
            cvInvoices.visibility = View.GONE
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

}