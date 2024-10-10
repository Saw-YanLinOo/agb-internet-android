package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.TicketVO
import com.agb.customer.billing.modelVO.UserVO
import com.google.gson.annotations.SerializedName

class TicketResponse : BaseResponse() {
    @SerializedName("data")
    var data: TicketVO ?= null
}