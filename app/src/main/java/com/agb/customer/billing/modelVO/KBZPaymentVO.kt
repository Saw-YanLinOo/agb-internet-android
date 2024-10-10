package com.agb.customer.billing.modelVO

import com.google.gson.annotations.SerializedName

class KBZPaymentVO {
    @SerializedName("prepay_id")
    var prepay_id : String ?= ""
    @SerializedName("nonce_str")
    var nonce_str : String ?= ""
    @SerializedName("sign")
    var sign : String ?= ""
    @SerializedName("appid")
    var appid : String ?= ""
    @SerializedName("callback_info")
    var callback_info : String ?= ""
    @SerializedName("merch_code")
    var merch_code : String ?= ""
    @SerializedName("method")
    var method : String ?= ""
    @SerializedName("notify_url")
    var notify_url : String ?= ""
    @SerializedName("timestamp")
    var timestamp : String ?= ""
    @SerializedName("signKey")
    var signKey : String ?= ""
    @SerializedName("sign_type")
    var sign_type : String ?= ""

}