package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.PendingPlanVO
import com.google.gson.annotations.SerializedName

class PendingPlanListResponse : BaseResponse() {
    @SerializedName("data")
    var data: MutableList<PendingPlanVO> ?= mutableListOf()
}