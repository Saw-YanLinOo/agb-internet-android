package com.agb.billing.customer.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.agb.billing.customer.activities.BaseActivity
import com.agb.billing.customer.R
import com.agb.billing.customer.databinding.ActivityTermConditionBinding
import com.agb.billing.customer.networks.responses.TNCResponse
import com.agb.billing.customer.viewmodels.TNCViewModel
import com.agb.billing.customer.views.TNCView

class TermConditionActivity : BaseActivity(), TNCView {

    lateinit var binding: ActivityTermConditionBinding
    lateinit var mViewModel: TNCViewModel

    companion object {
        fun newInstance(mContext: Context): Intent {
            return Intent(mContext, TermConditionActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermConditionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        initViewModel()
        clickEvent()
        viewLayoutVisible(false)
        apiCall()

    }

    private fun apiCall() {
        mViewModel.getTNC()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(TNCViewModel::class.java)
        mViewModel.setView(this)
    }

    private fun initLayout() {
        binding.apply {
            swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorSecondary))

            swipeRefreshLayout.setOnRefreshListener {
                viewLayoutVisible(false)
                apiCall()
            }
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
                tvTermConditionDesc.visibility = View.VISIBLE
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

    override fun setTNC(response: TNCResponse) {
        viewLayoutVisible(true)
        binding.swipeRefreshLayout.isRefreshing = false
        if (response.data?.description.toString().contains("<")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvTermConditionDesc.text = Html.fromHtml(
                    response.data?.description.toString(),
                    Html.FROM_HTML_MODE_COMPACT
                )
            } else {
                binding.tvTermConditionDesc.text =
                    Html.fromHtml(response.data?.description.toString());
            }
        } else {
            binding.tvTermConditionDesc.text = response.data?.description.toString()
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

    private fun hideLoadingNetwork() {
        binding.apply {
            swipeRefreshLayout.isRefreshing = false
            shimmerLoading.stopShimmer()
            shimmerLoading.visibility = View.GONE
            tvTermConditionDesc.visibility = View.INVISIBLE
            lyError.layoutError.visibility = View.VISIBLE
        }
    }

}