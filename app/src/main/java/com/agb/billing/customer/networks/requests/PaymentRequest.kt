package com.agb.billing.customer.networks.requests

import com.google.gson.annotations.SerializedName

class PaymentRequest(
    @SerializedName("invnumber")
    var invnumber: String ?= "",
    @SerializedName("paymentMethod")
    var paymentMethod: String ?= "",
    @SerializedName("phoneNo")
    var phoneNo: String ?= "",

)