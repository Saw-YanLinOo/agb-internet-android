package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.NotificationVO
import com.google.gson.annotations.SerializedName

class NotificationBody : BaseResponse() {

    @SerializedName("notificationList")
    var notificationList: MutableList<NotificationVO>? = mutableListOf()

}