package com.agb.billing.customer.networks.requests

import com.google.gson.annotations.SerializedName

class PaginationRequest(
    @SerializedName("pageNo")
    var pageNo: Int ?= 0

) {
}