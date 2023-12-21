package com.agb.billing.customer.networks.requests

import com.google.gson.annotations.SerializedName

class CBPaymentQRDownloadRequest(
    @SerializedName("invNumber")
    var invnumber: String ?= "",
    @SerializedName("serviceID")
    var serviceID: String ?= "",


)