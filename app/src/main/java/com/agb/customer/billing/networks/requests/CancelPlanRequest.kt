package com.agb.customer.billing.networks.requests

import com.google.gson.annotations.SerializedName

class CancelPlanRequest(
    @SerializedName("id")
    var id: Int ?= 0,
    @SerializedName("pendingplanid")
    var pendingplanid: Int ?= 0

) {
}