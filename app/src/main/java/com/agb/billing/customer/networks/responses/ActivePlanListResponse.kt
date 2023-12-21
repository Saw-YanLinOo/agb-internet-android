package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.ActivePlanVO
import com.google.gson.annotations.SerializedName

class ActivePlanListResponse : BaseResponse() {
    @SerializedName("data")
    var data: MutableList<ActivePlanVO> ?= mutableListOf()
}