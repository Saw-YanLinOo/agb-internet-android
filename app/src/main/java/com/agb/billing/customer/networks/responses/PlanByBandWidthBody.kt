package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.PlanListVO
import com.google.gson.annotations.SerializedName

class PlanByBandWidthBody : BaseResponse() {
    @SerializedName("planList")
    var planList: MutableList<PlanListVO> ?= mutableListOf()
}