package com.agb.customer.billing.views

import com.agb.customer.billing.networks.responses.ActivePlanListResponse
import com.agb.customer.billing.networks.responses.SuccessResponse

interface ActivePlanView : BaseVersionView {
    fun setActivePlanData(response: ActivePlanListResponse)

    fun setChangePlan(response : SuccessResponse)
}