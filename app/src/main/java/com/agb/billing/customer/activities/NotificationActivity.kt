package com.agb.billing.customer.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.agb.billing.customer.R
import com.agb.billing.customer.adapter.NotificationAdapter
import com.agb.billing.customer.databinding.ActivityNotificationBinding
import com.agb.billing.customer.delegate.NotiItemDelegates
import com.agb.billing.customer.modelVO.NotificationVO
import com.agb.billing.customer.networks.requests.EmptyRequest
import com.agb.billing.customer.networks.requests.NotificationRequest
import com.agb.billing.customer.networks.responses.NotificationResponse
import com.agb.billing.customer.viewmodels.NotificationViewModel
import com.agb.billing.customer.views.NotificationView

class NotificationActivity : BaseActivity(), NotificationView, NotiItemDelegates {
    lateinit var binding: ActivityNotificationBinding
    lateinit var mViewModel: NotificationViewModel
    lateinit var mAdapter: NotificationAdapter
    var notificationList: MutableList<NotificationVO> = mutableListOf()
    private var notificationVO: NotificationVO? = null
    private var hasMore: Boolean = false
    private var mPageNo: Int = 1
    private var mPrePageNo: Int = 2

    companion object {
        fun newInstance(mContext: Context): Intent {
            return Intent(mContext, NotificationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initLayout()
        clickEvent()
        viewLayoutVisible(false)
        apiCall()
    }


    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(NotificationViewModel::class.java)
        mViewModel.setVIew(this)
    }

    private fun initLayout() {
        mAdapter = NotificationAdapter(this)
        binding.apply {
            swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorSecondary))

            swipeRefreshLayout.setOnRefreshListener {
                viewLayoutVisible(false)
                mPageNo = 1
                mPrePageNo = 2
                apiCall()
            }
            rvNotification.layoutManager = LinearLayoutManager(this@NotificationActivity)
            rvNotification.setHasFixedSize(true)
            rvNotification.adapter = mAdapter
        }
    }

    private fun clickEvent() {
        binding.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun viewLayoutVisible(b: Boolean) {
        binding.apply {
            lyError.layoutError.visibility = View.GONE
            if (b) {
                shimmerLoading.stopShimmer()
                rvNotification.visibility = View.VISIBLE
                shimmerLoading.visibility = View.GONE
            } else {
                shimmerLoading.startShimmer()
                shimmerLoading.visibility = View.VISIBLE
            }
        }
    }

    private fun apiCall() {
        val request = NotificationRequest()
        request.pageNo = mPageNo
        mViewModel.getNotificationList(request)
    }

    private fun hideLoadingNetwork() {
        binding.apply {
            shimmerLoading.stopShimmer()
            shimmerLoading.visibility = View.GONE
            lyError.ivError.setImageResource(R.drawable.ic_component_wifi)
            lyError.tvError.text = resources.getString(R.string.error_wifi)
            lyError.layoutError.visibility = View.VISIBLE
        }
    }

    private fun hideLoadingError() {
        binding.apply {
            shimmerLoading.stopShimmer()
            shimmerLoading.visibility = View.GONE
            lyError.ivError.setImageResource(R.drawable.ic_home_complain)
            lyError.tvError.text = resources.getString(R.string.empty_noti_list)
            lyError.layoutError.visibility = View.VISIBLE
        }
    }

    override fun onTapItem(data: NotificationVO) {
        startActivity(NotificationDetailActivity.newInstance(this@NotificationActivity, data))
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
    }

    override fun responseNotificationData(response: NotificationResponse) {
        viewLayoutVisible(true)
        binding.swipeRefreshLayout.isRefreshing = false

        if (response.data != null) {
            response.data!!.notificationList.let { list ->
                if (list!!.size == 0) {
                    setLoadMoreState(false)
                    mAdapter.notifyDataSetChanged()
                    if (notificationList.size == 0)
                        hideLoadingError()

                } else {
                    setLoadMoreState(true)
                    notificationList.addAll(list)
                    mAdapter.setNewData(notificationList)
                }
            }
            notificationList = response.data!!.notificationList!!
        } else {
            hideLoadingError()
        }
    }

    fun setLoadMoreState(loadMoreState: Boolean) {
        hasMore = loadMoreState
    }

    fun getLoadMoreState(): Boolean {
        return hasMore
    }

    override fun showError(message: String, code: String) {
        binding.swipeRefreshLayout.isRefreshing = false
        hideLoadingNetwork()
    }

    override fun showInvalidSession(message: String, code: String) {
        binding.swipeRefreshLayout.isRefreshing = false
        mInvalidSession(this, message)
    }

    override fun showNetworkFailed() {
        binding.swipeRefreshLayout.isRefreshing = false
        hideLoadingNetwork()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.right_in, R.anim.right_out)
        finish()
    }
}