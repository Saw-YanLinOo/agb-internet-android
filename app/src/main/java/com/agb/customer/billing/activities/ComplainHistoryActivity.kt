package com.agb.customer.billing.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.agb.customer.billing.R
import com.agb.customer.billing.adapter.ComplainAdapter
import com.agb.customer.billing.databinding.ActivityComplainHistoryBinding
import com.agb.customer.billing.delegate.ComplainDelegate
import com.agb.customer.billing.modelVO.ComplainVO
import com.agb.customer.billing.modelVO.UserVO
import com.agb.customer.billing.networks.requests.ComplainListRequest
import com.agb.customer.billing.networks.responses.ComplainListResponse
import com.agb.customer.billing.utils.PreferenceUtils
import com.agb.customer.billing.viewmodels.ComplainListViewModel
import com.agb.customer.billing.views.ComplainListView

class ComplainHistoryActivity : BaseActivity(), ComplainDelegate, ComplainListView {

    lateinit var binding: ActivityComplainHistoryBinding
    private var complainList: MutableList<ComplainVO> = mutableListOf()
    private val complainAdapter by lazy { ComplainAdapter(this) }
    lateinit var mViewModel: ComplainListViewModel

    lateinit var mUser: UserVO

    companion object {
        fun newInstance(mContext: Context): Intent {
            return Intent(mContext, ComplainHistoryActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComplainHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViewModel()

    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(ComplainListViewModel::class.java)
        mViewModel.setView(this)
    }

    override fun onResume() {
        super.onResume()
        initLayout()
        viewLayoutVisible(false)
        invoiceListApiCall()

    }

    private fun initLayout() {
        mUser = PreferenceUtils.getUser()
        binding.rvComplain.layoutManager = LinearLayoutManager(this@ComplainHistoryActivity)
        binding.rvComplain.setHasFixedSize(true)
        binding.rvComplain.adapter = complainAdapter
        clickEvent()
    }

    private fun clickEvent() {

        binding.apply {
            swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorSecondary))
            swipeRefreshLayout.setOnRefreshListener {
                viewLayoutVisible(false)
                invoiceListApiCall()
            }
        }

        binding.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }
            btnSubmit.setOnClickListener {
                startActivity(ComplainActivity.newInstance(this@ComplainHistoryActivity))
                overridePendingTransition(R.anim.left_in, R.anim.left_out)
            }
        }

    }

    private fun invoiceListApiCall() {
        val request = ComplainListRequest()
        request.customerId = mUser.customerId.toString()
        request.customerUid = mUser.uid.toString()
        mViewModel.getComplainList(request)
    }

    override fun onTapComplain(data: ComplainVO) {
        startActivity(ComplainActivity.newInstance(this@ComplainHistoryActivity, data))
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
    }

    private fun viewLayoutVisible(b: Boolean) {
        binding.apply {
            if (b) {
                shimmerLoading.stopShimmer()
                shimmerLoading.visibility = View.GONE
            } else {
                shimmerLoading.startShimmer()
                shimmerLoading.visibility = View.VISIBLE
            }
        }
    }

    override fun setData(response: ComplainListResponse) {

        binding.swipeRefreshLayout.isRefreshing = false

        if (response.complainList != null && response.complainList!!.size > 0) {
            complainAdapter.setNewData(response.complainList!!)
            binding.lyError.layoutError.visibility = View.GONE
            binding.rvComplain.visibility = View.VISIBLE
        } else {
            hideLoadingError()
        }

        viewLayoutVisible(true)
    }

    private fun hideLoadingNetwork() {
        binding.apply {
            shimmerLoading.stopShimmer()
            shimmerLoading.visibility = View.GONE
            rvComplain.visibility = View.GONE
            lyError.layoutError.setBackgroundColor(Color.WHITE)
            lyError.layoutError.visibility = View.VISIBLE
        }
    }

    private fun hideLoadingError() {
        binding.apply {
            shimmerLoading.stopShimmer()
            shimmerLoading.visibility = View.GONE
            rvComplain.visibility = View.GONE
            lyError.layoutError.setBackgroundColor(Color.WHITE)
            lyError.ivError.setImageResource(R.drawable.ic_home_complain)
            lyError.tvError.text = resources.getString(R.string.error_complain)
            lyError.layoutError.visibility = View.VISIBLE
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


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.right_in, R.anim.right_out)
        finish()
    }

}