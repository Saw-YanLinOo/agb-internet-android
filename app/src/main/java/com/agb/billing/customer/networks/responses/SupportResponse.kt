package com.agb.billing.customer.networks.responses

import com.google.gson.annotations.SerializedName

class SupportResponse : BaseResponse() {
    @SerializedName("data")
    var data: SupportBody ?= null
}