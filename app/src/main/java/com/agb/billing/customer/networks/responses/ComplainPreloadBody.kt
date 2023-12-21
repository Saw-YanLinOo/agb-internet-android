package com.agb.billing.customer.networks.responses

import com.agb.billing.customer.modelVO.ActivePlanVO
import com.agb.billing.customer.modelVO.BandWidthVO
import com.agb.billing.customer.modelVO.CategoryComplainVO
import com.agb.billing.customer.modelVO.PlanListVO
import com.google.gson.annotations.SerializedName

class ComplainPreloadBody : BaseResponse() {
    @SerializedName("activePlanList")
    var activePlanList: MutableList<ActivePlanVO> ?= mutableListOf()

    @SerializedName("complainCategoryList")
    var categoryComplainList: MutableList<CategoryComplainVO> ?= mutableListOf()


}