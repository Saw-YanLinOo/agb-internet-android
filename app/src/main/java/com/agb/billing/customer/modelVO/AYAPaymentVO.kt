package com.agb.billing.customer.modelVO

import com.google.gson.annotations.SerializedName

class AYAPaymentVO {
    @SerializedName("result")
    var title : String ?= ""
    @SerializedName("msg")
    var desc : String ?= ""



}