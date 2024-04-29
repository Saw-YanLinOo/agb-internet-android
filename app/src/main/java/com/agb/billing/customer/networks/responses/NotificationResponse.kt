package com.agb.billing.customer.networks.responses

import com.google.gson.annotations.SerializedName

class NotificationResponse : BaseResponse() {
    @SerializedName("data")
    var data: NotificationBody ?= null

}