package com.agb.billing.customer.networks.requests

import com.google.gson.annotations.SerializedName

class ChangePlanPreloadRequest(
    @SerializedName("activePlanId")
    var activePlanId: Int ?= 0

) {
}