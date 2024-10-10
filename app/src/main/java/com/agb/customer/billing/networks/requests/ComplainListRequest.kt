package com.agb.customer.billing.networks.requests

import com.google.gson.annotations.SerializedName

class ComplainListRequest(

    @SerializedName("customerId")
    var customerId: String? = "",

    @SerializedName("customerUid")
    var customerUid: String? = "",

    )