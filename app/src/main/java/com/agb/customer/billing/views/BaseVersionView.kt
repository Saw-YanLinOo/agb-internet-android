package com.agb.customer.billing.views

interface BaseVersionView {

    fun showError(message:String,code:String)
    fun showInvalidSession(message: String, code: String)
    fun showNetworkFailed()
    fun showVersionUpdate(message: String, storeUrl: String)
}