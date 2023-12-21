package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.ActivePlanListResponse
import com.agb.billing.customer.networks.responses.SuccessResponse

interface ActivePlanView : BaseVersionView {
    fun setActivePlanData(response: ActivePlanListResponse)

    fun setChangePlan(response : SuccessResponse)
}