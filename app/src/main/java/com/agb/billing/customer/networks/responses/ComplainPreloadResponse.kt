package com.agb.billing.customer.networks.responses

import com.google.gson.annotations.SerializedName

class ComplainPreloadResponse : BaseResponse() {
    @SerializedName("data")
    var data: ComplainPreloadBody? = null
}