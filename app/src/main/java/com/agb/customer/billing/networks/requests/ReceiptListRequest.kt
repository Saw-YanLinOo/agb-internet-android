package com.agb.customer.billing.networks.requests

import com.google.gson.annotations.SerializedName

class ReceiptListRequest(
    @SerializedName("pageNo")
    var pageNo: Int ?= 0

) {
}