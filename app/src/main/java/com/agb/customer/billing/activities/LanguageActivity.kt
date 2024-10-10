package com.agb.customer.billing.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.agb.customer.billing.activities.BaseActivity
import com.agb.customer.billing.R
import com.agb.customer.billing.databinding.ActivityLanguageBinding
import com.agb.customer.billing.localizations.LocaleManager
import com.agb.customer.billing.utils.Constants
import com.agb.customer.billing.utils.PreferenceUtils

class LanguageActivity : BaseActivity() {

    lateinit var binding : ActivityLanguageBinding

    companion object{
        fun newInstance(mContext : Context) : Intent{
            return Intent(mContext,LanguageActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        initCheckLanguage()
        clickEvent()
    }

    private fun initCheckLanguage() {
        val lang = LocaleManager(this).getLanguage()
        binding.apply {
            if (lang == Constants.LANG_EN) {
                ivEnglishCheck.visibility = View.VISIBLE
                ivMyanmarCheck.visibility = View.GONE
            } else {
                ivMyanmarCheck.visibility = View.VISIBLE
                ivEnglishCheck.visibility = View.GONE
            }
        }
    }

    private fun initLayout() {

    }

    private fun clickEvent() {
        binding.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }

            lblMyanmar.setOnClickListener {
                if(!ivMyanmarCheck.isVisible) {
                    ivMyanmarCheck.visibility = View.VISIBLE
                    ivEnglishCheck.visibility = View.GONE
                    PreferenceUtils.setLanguage(Constants.LANG_UNI)
                    languageChange(Constants.LANG_UNI)
                }
            }

            lblEnglish.setOnClickListener {
                if(!ivEnglishCheck.isVisible) {
                    ivEnglishCheck.visibility = View.VISIBLE
                    ivMyanmarCheck.visibility = View.GONE
                    PreferenceUtils.setLanguage(Constants.LANG_EN)
                    languageChange(Constants.LANG_EN)
                }
            }
        }
    }

    fun languageChange(language: String) {
        LocaleManager(this).setNewLocale(this, language)
        reOpenApp()
    }
    private fun reOpenApp() {
        val refresh = Intent(this,MainActivity::class.java)
        refresh.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(refresh)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.right_in, R.anim.right_out)
        finish()
    }

}