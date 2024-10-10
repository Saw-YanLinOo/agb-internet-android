package com.agb.customer.billing.networks.requests

import com.google.gson.annotations.SerializedName

class PaginationRequest(
    @SerializedName("pageNo")
    var pageNo: Int ?= 0

) {
}