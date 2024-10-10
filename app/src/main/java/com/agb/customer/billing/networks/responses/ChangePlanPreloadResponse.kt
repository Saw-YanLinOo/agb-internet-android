package com.agb.customer.billing.networks.responses

import com.google.gson.annotations.SerializedName

class ChangePlanPreloadResponse : BaseResponse() {
    @SerializedName("data")
    var data: ChangePlanPreloadBody ?= null
}