package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.AYAPaymentVO
import com.agb.customer.billing.modelVO.KBZPaymentVO
import com.google.gson.annotations.SerializedName

class AYAPaymentResponse : BaseResponse() {
    @SerializedName("data")
    var data: AYAPaymentVO ?= null
}
