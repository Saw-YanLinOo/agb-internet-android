package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.InvoiceDetailVO
import com.google.gson.annotations.SerializedName

class InvoiceDetailResponse : BaseResponse() {
    @SerializedName("data")
    var data: InvoiceDetailVO ?= null
}