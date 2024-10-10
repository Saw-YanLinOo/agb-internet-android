package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.PaymentVO
import com.google.gson.annotations.SerializedName

class PaymentMethodResponse : BaseResponse() {
    @SerializedName("data")
    var paymentMethodList: MutableList<PaymentVO> ?= mutableListOf()
}