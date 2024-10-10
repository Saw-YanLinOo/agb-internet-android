package com.agb.customer.billing.networks.responses

import com.google.gson.annotations.SerializedName

class HomeResponse : BaseResponse() {
    @SerializedName("data")
    var data: HomeBody ?= null
}