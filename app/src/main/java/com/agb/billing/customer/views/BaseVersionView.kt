package com.agb.billing.customer.views

interface BaseVersionView {

    fun showError(message:String,code:String)
    fun showInvalidSession(message: String, code: String)
    fun showNetworkFailed()
    fun showVersionUpdate(message: String, storeUrl: String)
}