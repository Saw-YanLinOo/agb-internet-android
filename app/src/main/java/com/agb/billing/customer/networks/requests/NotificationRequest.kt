package com.agb.billing.customer.networks.requests

import com.google.gson.annotations.SerializedName

class NotificationRequest(
    @SerializedName("pageNo")
    var pageNo: Int? = 0,
    )