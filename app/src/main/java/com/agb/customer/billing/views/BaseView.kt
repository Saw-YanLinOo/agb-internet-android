package com.agb.customer.billing.views

interface BaseView {

    fun showError(message:String,code:String)
    fun showInvalidSession(message: String, code: String)
    fun showNetworkFailed()
}