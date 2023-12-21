package com.agb.billing.customer.views

interface BaseView {

    fun showError(message:String,code:String)
    fun showInvalidSession(message: String, code: String)
    fun showNetworkFailed()
}