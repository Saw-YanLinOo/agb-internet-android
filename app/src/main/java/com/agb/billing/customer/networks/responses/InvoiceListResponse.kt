package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.InvoiceVO
import com.google.gson.annotations.SerializedName

class InvoiceListResponse : BaseResponse() {
    @SerializedName("data")
    var data: MutableList<InvoiceVO> ?= mutableListOf()
}