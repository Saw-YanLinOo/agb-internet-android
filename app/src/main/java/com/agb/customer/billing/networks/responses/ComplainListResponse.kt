package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.ComplainVO
import com.agb.customer.billing.modelVO.InvoiceVO
import com.google.gson.annotations.SerializedName

class ComplainListResponse : BaseResponse() {
    @SerializedName("data")
    var complainList: MutableList<ComplainVO> ?= mutableListOf()
}