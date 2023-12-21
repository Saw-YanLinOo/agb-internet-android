package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.TicketVO
import com.agb.billing.customer.modelVO.UserVO
import com.google.gson.annotations.SerializedName

class TicketResponse : BaseResponse() {
    @SerializedName("data")
    var data: TicketVO ?= null
}