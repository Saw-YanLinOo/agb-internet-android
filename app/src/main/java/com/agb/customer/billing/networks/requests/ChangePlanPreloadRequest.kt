package com.agb.customer.billing.networks.requests

import com.google.gson.annotations.SerializedName

class ChangePlanPreloadRequest(
    @SerializedName("activePlanId")
    var activePlanId: Int ?= 0

) {
}