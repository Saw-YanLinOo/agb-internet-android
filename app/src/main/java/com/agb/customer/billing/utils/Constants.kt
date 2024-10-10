package com.agb.customer.billing.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class Constants {
    companion object{
        const val LANG_EN = "en"
        const val LANG_UNI = "my"

        const val API_SUCCESS_CODE: String = "1"
        const val INVALID_SESSION_CODE = "1001"
        const val API_MULTI_ERROR_CODE = "1000"
        const val FACEBOOK_NEW_USER_CODE = "99999"
        const val API_VERSION_UPDATE_CODE = "2001"
        const val FIRST_TIME_LOGIN_CODE = "1007"
        const val FIELD_ERROR_CODE = "1029"

        const val API_FAILED_CODE = "-1"
        const val CONNECTION_FAIL: String = "Please Check You Internet Connection"

        const val KBZ_PAY = "kbz"
        const val AYA_PAY = "aya"

        const val KBZ_PAYMENT = "kbzpay"
        const val AYA_PAYMENT = "ayapay"
        const val DEVICE_TYPE = "1"

        const val NOTI_TYPE = "notiType"
        const val NOTI_DATA = "notiData"
        const val NOTI_TYPE_1 = "1"
        const val NOTI_TYPE_2 = "2"
        const val NOTI_TYPE_3 = "3"

        const val FROM_NOTI = "from_noti"
        const val NOTIFICATION = "notification"

        const val INVOICE = "1"
        const val RECEIPT = "2"
        var CURRENT_PLAN = 0
    }


}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager =
        getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    view?.let {
        activity?.hideKeyboard(it)
    }
}