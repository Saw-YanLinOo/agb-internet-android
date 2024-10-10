package com.agb.customer.billing.networks.requests

import com.google.gson.annotations.SerializedName

class NotificationRequest(
    @SerializedName("pageNo")
    var pageNo: Int? = 0,
    )