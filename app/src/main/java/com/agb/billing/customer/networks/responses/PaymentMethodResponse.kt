package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.PaymentVO
import com.google.gson.annotations.SerializedName

class PaymentMethodResponse : BaseResponse() {
    @SerializedName("data")
    var paymentMethodList: MutableList<PaymentVO> ?= mutableListOf()
}