package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.ReceiptVO
import com.google.gson.annotations.SerializedName

class ReceiptListResponse : BaseResponse() {
    @SerializedName("data")
    var data: MutableList<ReceiptVO> ?= mutableListOf()
}