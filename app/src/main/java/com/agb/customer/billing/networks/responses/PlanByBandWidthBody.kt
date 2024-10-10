package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.PlanListVO
import com.google.gson.annotations.SerializedName

class PlanByBandWidthBody : BaseResponse() {
    @SerializedName("planList")
    var planList: MutableList<PlanListVO> ?= mutableListOf()
}