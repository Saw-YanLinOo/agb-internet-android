package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.NotificationVO
import com.google.gson.annotations.SerializedName

class NotificationBody : BaseResponse() {

    @SerializedName("notificationList")
    var notificationList: MutableList<NotificationVO>? = mutableListOf()

}