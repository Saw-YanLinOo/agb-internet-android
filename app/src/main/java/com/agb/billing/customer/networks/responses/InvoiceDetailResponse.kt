package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.InvoiceDetailVO
import com.google.gson.annotations.SerializedName

class InvoiceDetailResponse : BaseResponse() {
    @SerializedName("data")
    var data: InvoiceDetailVO ?= null
}