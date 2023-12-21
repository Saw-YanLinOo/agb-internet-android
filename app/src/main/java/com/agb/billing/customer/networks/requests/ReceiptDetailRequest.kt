package com.agb.billing.customer.networks.requests

import com.google.gson.annotations.SerializedName

class ReceiptDetailRequest(
    @SerializedName("invnumber")
    var invnumber: String ?= "",
    @SerializedName("invType")
    var invType: String ?= ""

) {
}