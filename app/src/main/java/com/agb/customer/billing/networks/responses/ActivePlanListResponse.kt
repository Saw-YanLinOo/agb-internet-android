package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.ActivePlanVO
import com.google.gson.annotations.SerializedName

class ActivePlanListResponse : BaseResponse() {
    @SerializedName("data")
    var data: MutableList<ActivePlanVO> ?= mutableListOf()
}