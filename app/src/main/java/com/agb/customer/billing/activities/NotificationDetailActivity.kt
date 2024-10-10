package com.agb.customer.billing.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.agb.customer.billing.R
import com.agb.customer.billing.databinding.ActivityNotificationDetailBinding
import com.agb.customer.billing.modelVO.ComplainVO
import com.agb.customer.billing.modelVO.NotificationVO
import com.agb.customer.billing.networks.responses.NotificationResponse
import com.agb.customer.billing.views.NotificationDetailView

class NotificationDetailActivity : BaseActivity(), NotificationDetailView {

    private lateinit var binding: ActivityNotificationDetailBinding

    companion object {
        private var notificationVO: NotificationVO? = null
        fun newInstance(mContext: Context, notificationVO: NotificationVO? = null): Intent {
            this.notificationVO = notificationVO
            return Intent(mContext, NotificationDetailActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewLayoutVisible(false)
    }

    private fun viewLayoutVisible(b: Boolean) {
        binding.apply {
//            if (b) {
//                shimmerLoading.stopShimmer()
//                cvSubmit.visibility = View.VISIBLE
//                shimmerLoading.visibility = View.GONE
//            } else {
//                shimmerLoading.startShimmer()
//                cvSubmit.visibility = View.GONE
//                shimmerLoading.visibility = View.VISIBLE
//            }
        }
    }

    override fun setNotificationData(response: NotificationResponse) {
        TODO("Not yet implemented")
    }

    override fun showError(message: String, code: String) {
        TODO("Not yet implemented")
    }

    override fun showInvalidSession(message: String, code: String) {
        TODO("Not yet implemented")
    }

    override fun showNetworkFailed() {
        TODO("Not yet implemented")
    }

    override fun showVersionUpdate(message: String, storeUrl: String) {
        TODO("Not yet implemented")
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.right_in, R.anim.right_out)
        finish()
    }
}