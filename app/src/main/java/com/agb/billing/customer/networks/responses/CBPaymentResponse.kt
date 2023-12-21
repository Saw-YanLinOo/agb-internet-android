package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.CBPaymentVO
import com.google.gson.annotations.SerializedName

class CBPaymentResponse : BaseResponse() {
    @SerializedName("data")
    var data: CBPaymentVO ?= null
}
