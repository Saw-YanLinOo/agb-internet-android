package com.agb.customer.billing.views

import com.agb.customer.billing.networks.responses.ChangePlanPreloadResponse
import com.agb.customer.billing.networks.responses.PlanByBandWidthResponse
import com.agb.customer.billing.networks.responses.SuccessResponse

interface ChangePlanPreloadView : BaseView {
    fun setChangePlanPreload(response: ChangePlanPreloadResponse)

    fun setPlanByBandWidth(response: PlanByBandWidthResponse)

    fun setChangePlan(response: SuccessResponse)


}