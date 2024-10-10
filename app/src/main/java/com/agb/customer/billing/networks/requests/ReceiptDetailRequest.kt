package com.agb.customer.billing.networks.requests

import com.google.gson.annotations.SerializedName

class ReceiptDetailRequest(
    @SerializedName("invnumber")
    var invnumber: String ?= "",
    @SerializedName("invType")
    var invType: String ?= ""

) {
}