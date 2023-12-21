package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.ChangePlanPreloadResponse
import com.agb.billing.customer.networks.responses.PlanByBandWidthResponse
import com.agb.billing.customer.networks.responses.SuccessResponse

interface ChangePlanPreloadView : BaseView {
    fun setChangePlanPreload(response: ChangePlanPreloadResponse)

    fun setPlanByBandWidth(response: PlanByBandWidthResponse)

    fun setChangePlan(response: SuccessResponse)


}