package com.agb.customer.billing.utils

import android.content.Context
import android.content.SharedPreferences
import com.agb.customer.billing.modelVO.PaymentNotificationVO
import com.agb.customer.billing.modelVO.UserVO
import com.agb.customer.billing.networks.EndPoints
import com.google.gson.Gson

object PreferenceUtils {

    private const val PREFERENCE_USER = "user"
    private const val LANGUAGE = "language_key"
    private const val NOTIFICATION = "notification"
    private const val ACTIVE_BASE_URL = "active_base_url"

    private var mAppContext: Context? = null

    fun init(appContext: Context?) {
        mAppContext = appContext
    }

    private fun  sharedPreferences() : SharedPreferences {
        return mAppContext!!.getSharedPreferences("AGB_BILLING@aPP", Context.MODE_PRIVATE)
    }

    fun getUser(): UserVO {
        val loginData = sharedPreferences().getString(PREFERENCE_USER, "")
        return if (loginData == "") {
            UserVO()
        } else {
            Gson().fromJson(loginData, UserVO::class.java)
        }
    }

    fun setUser(userVO: UserVO) {
        val editor = sharedPreferences().edit()
        val loginData = Gson().toJson(userVO)
        editor.putString(PREFERENCE_USER, loginData).apply()
    }

    fun  getAppLanguage() :String {
        val language = sharedPreferences().getString(LANGUAGE, Constants.LANG_EN)
        return language ?: Constants.LANG_EN
    }

    fun setLanguage(language : String) {
        val editor = sharedPreferences().edit()
        editor.putString(LANGUAGE, language).apply()
    }

    fun setNotiData(dataVo : PaymentNotificationVO){
        val editor = sharedPreferences().edit()
        val notiData = Gson().toJson(dataVo)
        editor.putString(NOTIFICATION, notiData).apply()
    }

    fun getNotiData(): PaymentNotificationVO {
        val loginData = sharedPreferences().getString(NOTIFICATION, "")
        return if (loginData == "") {
            PaymentNotificationVO()
        } else {
            Gson().fromJson(loginData, PaymentNotificationVO::class.java)
        }
    }

    fun setBaseUrl(url: String){
        val editor = sharedPreferences().edit()
        editor.putString(ACTIVE_BASE_URL,url).apply()
    }

    fun getBaseUrl(): String? {
        val baseURL = sharedPreferences().getString(ACTIVE_BASE_URL, null)
        return baseURL

    }
}