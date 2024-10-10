package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.KBZPaymentVO
import com.google.gson.annotations.SerializedName

class KBZPaymentResponse : BaseResponse() {
    @SerializedName("data")
    var data: KBZPaymentVO ?= null
}