package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.BandWidthVO
import com.agb.customer.billing.modelVO.PlanListVO
import com.google.gson.annotations.SerializedName

class ChangePlanPreloadBody : BaseResponse() {
    @SerializedName("bandWithList")
    var bandWithList: MutableList<BandWidthVO> ?= mutableListOf()
    @SerializedName("planList")
    var planList: MutableList<PlanListVO> ?= mutableListOf()
    @SerializedName("termsAndConditionsDesc")
    var termsAndConditionsDesc: String ?= ""

}