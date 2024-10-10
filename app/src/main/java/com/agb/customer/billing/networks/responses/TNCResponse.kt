package com.agb.customer.billing.networks.responses

import com.google.gson.annotations.SerializedName

class TNCResponse : BaseResponse() {
    @SerializedName("data")
    var data: TNCBody ?= null
}