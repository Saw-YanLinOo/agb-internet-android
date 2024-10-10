package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.ReceiptVO
import com.google.gson.annotations.SerializedName

class ReceiptListResponse : BaseResponse() {
    @SerializedName("data")
    var data: MutableList<ReceiptVO> ?= mutableListOf()
}