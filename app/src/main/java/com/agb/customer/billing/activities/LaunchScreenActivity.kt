package com.agb.customer.billing.activities

import android.os.Bundle
import android.os.Handler
import com.agb.customer.billing.activities.BaseActivity
import com.agb.customer.billing.R
import com.agb.customer.billing.databinding.ActivitySplashBinding
import com.agb.customer.billing.localizations.LocaleManager
import com.agb.customer.billing.modelVO.PaymentNotificationVO
import com.agb.customer.billing.networks.EndPoints
import com.agb.customer.billing.utils.Constants
import com.agb.customer.billing.utils.PreferenceUtils

class LaunchScreenActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        LocaleManager(this).setLocale(this)
        PreferenceUtils.setNotiData(PaymentNotificationVO()) //noti
        hideStatusBar()
        setContentView(binding.root)

        Handler().postDelayed({
            startActivity(LoginActivity.newInstance(this@LaunchScreenActivity))
            overridePendingTransition(R.anim.left_in, R.anim.left_out)
            finish()
        }, 3000)

    }
}