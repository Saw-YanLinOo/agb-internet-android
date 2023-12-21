package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.PendingPlanVO
import com.google.gson.annotations.SerializedName

class PendingPlanListResponse : BaseResponse() {
    @SerializedName("data")
    var data: MutableList<PendingPlanVO> ?= mutableListOf()
}