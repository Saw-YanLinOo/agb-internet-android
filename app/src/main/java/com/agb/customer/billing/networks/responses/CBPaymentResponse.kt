package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.CBPaymentVO
import com.google.gson.annotations.SerializedName

class CBPaymentResponse : BaseResponse() {
    @SerializedName("data")
    var data: CBPaymentVO ?= null
}
