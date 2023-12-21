package com.agb.billing.customer.activities

import android.os.Bundle
import android.os.Handler
import com.agb.billing.customer.activities.BaseActivity
import com.agb.billing.customer.R
import com.agb.billing.customer.databinding.ActivitySplashBinding
import com.agb.billing.customer.localizations.LocaleManager
import com.agb.billing.customer.modelVO.PaymentNotificationVO
import com.agb.billing.customer.utils.PreferenceUtils

class LaunchScreenActivity : BaseActivity() {

    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        LocaleManager(this).setLocale(this)
        PreferenceUtils.setNotiData(PaymentNotificationVO()) //noti
        hideStatusBar()
        setContentView(binding.root)

        Handler().postDelayed(object : Runnable{
            override fun run() {
                startActivity(LoginActivity.newInstance(this@LaunchScreenActivity))
                overridePendingTransition(R.anim.left_in, R.anim.left_out)
                finish()
            }

        },3000)
    }
}