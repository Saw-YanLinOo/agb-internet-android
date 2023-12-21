package com.agb.billing.customer.networks.requests

import com.google.gson.annotations.SerializedName

class PlanByBandWidthRequest(
    @SerializedName("activePlanId")
    var activePlanId: Int ?= 0,
    @SerializedName("bandWidth")
    var bandWidth: String ?= ""
) {
}