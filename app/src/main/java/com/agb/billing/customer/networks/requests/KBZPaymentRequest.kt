package com.agb.billing.customer.networks.requests

import com.google.gson.annotations.SerializedName

class KBZPaymentRequest(
    @SerializedName("invnumber")
    var invnumber: String ?= "",
    @SerializedName("paymentMethod")
    var paymentMethod: String ?= ""//kbzpay
) {
}