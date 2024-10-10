package com.agb.customer.billing.networks.responses

import com.google.gson.annotations.SerializedName

class PlanByBandWidthResponse : BaseResponse() {
    @SerializedName("data")
    var data: PlanByBandWidthBody ?= null
}