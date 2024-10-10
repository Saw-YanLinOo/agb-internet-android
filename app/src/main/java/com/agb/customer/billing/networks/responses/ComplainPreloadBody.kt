package com.agb.customer.billing.networks.responses

import com.agb.customer.billing.modelVO.ActivePlanVO
import com.agb.customer.billing.modelVO.BandWidthVO
import com.agb.customer.billing.modelVO.CategoryComplainVO
import com.agb.customer.billing.modelVO.PlanListVO
import com.google.gson.annotations.SerializedName

class ComplainPreloadBody : BaseResponse() {
    @SerializedName("activePlanList")
    var activePlanList: MutableList<ActivePlanVO> ?= mutableListOf()

    @SerializedName("complainCategoryList")
    var categoryComplainList: MutableList<CategoryComplainVO> ?= mutableListOf()


}