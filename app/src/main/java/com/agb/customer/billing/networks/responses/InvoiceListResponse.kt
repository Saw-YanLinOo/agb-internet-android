package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.InvoiceVO
import com.google.gson.annotations.SerializedName

class InvoiceListResponse : BaseResponse() {
    @SerializedName("data")
    var data: MutableList<InvoiceVO> ?= mutableListOf()
}