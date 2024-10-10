package com.agb.customer.billing.localizations

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Build
import android.util.Log
import java.util.*

class LocaleManager(context: Context?) {

    val LANGUAGE_ENGLISH = "en"
    val LANGUAGE_DEFAULT_UNI = "my"
    private val LANGUAGE_KEY = "language_key"

    private var prefs: SharedPreferences? = null

    init {
        prefs = context!!.getSharedPreferences("AGB@BILLING", Context.MODE_PRIVATE)
    }

    fun onAttach(context: Context?): Context? {
        return setLocale(context!!)
    }


    fun setLocale(c: Context): Context? {
        persistLanguage(getLanguage())
        return updateResources(c, getLanguage())
    }

    fun setNewLocale(
        c: Context,
        language: String
    ): Context? {
        persistLanguage(language)
        return updateResources(c, language)
    }

    fun getLanguage(): String {
        return prefs!!.getString(LANGUAGE_KEY, LANGUAGE_ENGLISH)!!
    }

    @SuppressLint("ApplySharedPref")
    private fun persistLanguage(language: String) { // use commit() instead of apply(), because sometimes we kill the application process immediately
// which will prevent apply() to finish
        prefs!!.edit().putString(LANGUAGE_KEY, language).commit()
    }

    private fun updateResources(
        context: Context,
        language: String
    ): Context? {

        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        Log.e("LG", language)
        return context
    }

    fun getLocale(res: Resources): Locale {
        val config = res.configuration
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.locales[0]
        } else {
            config.locale
        }
    }

//    fun getLocaleValue(resources: Resources): String? {
//        return if (getLocale(resources).toString() == ConstantData.LANG_ZG) ConstantData.LANG_ZAW_GYI else getLocale(
//            resources
//        ).toString()
//    }
}