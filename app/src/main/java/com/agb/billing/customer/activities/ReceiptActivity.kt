package com.agb.billing.customer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.agb.billing.customer.activities.BaseActivity
import com.agb.billing.customer.R
import com.agb.billing.customer.adapter.ReceiptAdapter
import com.agb.billing.customer.databinding.ActivityReceiptBinding
import com.agb.billing.customer.delegate.ReceiptDelegate
import com.agb.billing.customer.modelVO.ReceiptVO
import com.agb.billing.customer.networks.requests.ReceiptListRequest
import com.agb.billing.customer.networks.responses.ReceiptListResponse
import com.agb.billing.customer.viewmodels.ReceiptListViewModel
import com.agb.billing.customer.views.ReceiptListView
import com.google.gson.Gson

class ReceiptActivity : BaseActivity(), ReceiptDelegate, ReceiptListView {

    lateinit var binding: ActivityReceiptBinding
    lateinit var mAdapter: ReceiptAdapter
    lateinit var mViewModel: ReceiptListViewModel
    var mList: MutableList<ReceiptVO> = mutableListOf()

    var pageNo = 1
    var prePageNo = 2
    var sorting = 1//1 is descending and 2 ascending,init value is default

    companion object {
        fun newInstance(mContext: Context): Intent {
            return Intent(mContext, ReceiptActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        initViewModel()
        clickEvent()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(ReceiptListViewModel::class.java)
        mViewModel.setView(this)
    }

    private fun initLayout() {
        mAdapter = ReceiptAdapter(this)
        binding.apply {

            swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorSecondary))
            swipeRefreshLayout.setOnRefreshListener {
                viewLayoutVisible(false)
                etSearchReceipt.setText("")
                etSearchReceipt.clearFocus()
                ivFilter.setImageResource(R.drawable.ic_invoice_filter)
                sorting = 2
                pageNo = 1
                prePageNo = 2
                receiptListApiCall()
            }

            rvReceipt.layoutManager = LinearLayoutManager(this@ReceiptActivity)
            rvReceipt.setHasFixedSize(true)
            rvReceipt.adapter = mAdapter
//            mAdapter.setNewData(SampleData.receiptList())

            etSearchReceipt.addTextChangedListener(object : TextWatcher {
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
                                ) || it.paiddate!!.toLowerCase()
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

//            rvReceipt.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    if ((rvReceipt.layoutManager as LinearLayoutManager).itemCount >= 3) {
//                        if (((rvReceipt.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() + 1) == (rvReceipt.layoutManager as LinearLayoutManager).itemCount) {
//                            if (prePageNo >= pageNo) {
//                                if (prePageNo == pageNo) {
//                                    prePageNo += 1
//                                }
//                                showProgress()
//                                //api call
//                                receiptListApiCall()
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

            lyReceiptFilter.setOnClickListener {
                if (mList.size > 0) {
                    if (sorting == 2) {
                        sorting = 1
                        mList.sortByDescending { it.invnumber }
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

            lblReceipt.setOnClickListener {
                Toast.makeText(this@ReceiptActivity, "Got Ti", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewLayoutVisible(false)
        receiptListApiCall()
    }

    fun receiptListApiCall() {
        val request = ReceiptListRequest()
        request.pageNo = pageNo
        mViewModel.getReceiptList(request)
    }

    override fun onTapReceipt(data: ReceiptVO) {
        startActivity(ReceiptDetailActivity.newInstance(this, data.invnumber.toString()))
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
    }

    private fun viewLayoutVisible(b: Boolean) {
        binding.apply {
            if (b) {
                shimmerLoading.stopShimmer()
                cvReceipt.visibility = View.VISIBLE
                shimmerLoading.visibility = View.GONE
            } else {
                shimmerLoading.startShimmer()
                cvReceipt.visibility = View.GONE
                shimmerLoading.visibility = View.VISIBLE
            }
        }
    }

    override fun setData(response: ReceiptListResponse) {
        viewLayoutVisible(true)
        dismissProgress()
        binding.swipeRefreshLayout.isRefreshing = false

        if (response.data != null && response.data!!.size > 0) {
            Log.e("RECEIPT_LIST", Gson().toJson(response.data))

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

    private fun hideLoadingError() {
        binding.apply {
            shimmerLoading.stopShimmer()
            shimmerLoading.visibility = View.GONE
            cvReceipt.visibility = View.GONE
            lyError.layoutError.visibility = View.VISIBLE
            lyError.ivError.setImageResource(R.drawable.ic_component_receipts)
            lyError.tvError.text = resources.getString(R.string.error_receipt)
        }
    }

    private fun hideLoadingNetwork() {
        binding.apply {
            shimmerLoading.stopShimmer()
            shimmerLoading.visibility = View.GONE
            cvReceipt.visibility = View.GONE
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