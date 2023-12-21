package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.AYAPaymentVO
import com.agb.billing.customer.modelVO.KBZPaymentVO
import com.google.gson.annotations.SerializedName

class AYAPaymentResponse : BaseResponse() {
    @SerializedName("data")
    var data: AYAPaymentVO ?= null
}
