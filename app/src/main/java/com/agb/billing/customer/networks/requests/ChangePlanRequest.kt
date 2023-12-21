package com.agb.billing.customer.networks.requests

import com.google.gson.annotations.SerializedName

class ChangePlanRequest(
    @SerializedName("id")
    var id: Int ?= 0,
    @SerializedName("newplanid")
    var newplanid: Int ?= 0

) {
}