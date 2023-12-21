package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.ComplainVO
import com.agb.billing.customer.modelVO.InvoiceVO
import com.google.gson.annotations.SerializedName

class ComplainListResponse : BaseResponse() {
    @SerializedName("data")
    var complainList: MutableList<ComplainVO> ?= mutableListOf()
}