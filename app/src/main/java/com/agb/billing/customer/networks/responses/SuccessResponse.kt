package com.agb.billing.customer.networks.responses

import com.google.gson.annotations.SerializedName

class SuccessResponse : BaseResponse() {
    @SerializedName("data")
    var data: String ?= ""
}