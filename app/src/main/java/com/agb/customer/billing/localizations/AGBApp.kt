package com.agb.customer.billing.localizations

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.agb.customer.billing.utils.PreferenceUtils
import com.lzy.okgo.OkGo


class AGBApp : Application() {

    companion object {

        lateinit var localeManager: LocaleManager

        private var context: AGBApp? = null

        fun getContext(): AGBApp {
            if (context == null)
                context =
                    AGBApp()

            val i = context
            return i!!
        }

    }

    override fun onCreate() {
        super.onCreate()
        context = this
        PreferenceUtils.init(context)
        OkGo.getInstance().init(this)

    }

    override fun attachBaseContext(base: Context?) {
        localeManager = LocaleManager(base)
        super.attachBaseContext(localeManager.setLocale(base!!))
//        super.attachBaseContext(LocaleHelper.onAttach(base!!,"my"))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager.setLocale(this)
//        LocaleHelper.setLocale(this,"my")
    }
}