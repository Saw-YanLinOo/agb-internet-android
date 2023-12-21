package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.KBZPaymentVO
import com.google.gson.annotations.SerializedName

class KBZPaymentResponse : BaseResponse() {
    @SerializedName("data")
    var data: KBZPaymentVO ?= null
}