package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.BandWidthVO
import com.agb.billing.customer.modelVO.PlanListVO
import com.google.gson.annotations.SerializedName

class ChangePlanPreloadBody : BaseResponse() {
    @SerializedName("bandWithList")
    var bandWithList: MutableList<BandWidthVO> ?= mutableListOf()
    @SerializedName("planList")
    var planList: MutableList<PlanListVO> ?= mutableListOf()
    @SerializedName("termsAndConditionsDesc")
    var termsAndConditionsDesc: String ?= ""

}