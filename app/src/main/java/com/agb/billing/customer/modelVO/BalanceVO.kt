package com.agb.billing.customer.modelVO

import com.google.gson.annotations.SerializedName

class BalanceVO {
    @SerializedName("colorcode")
    var colorcode : String ?= ""
    @SerializedName("value")
    var value : String ?= ""

}