package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.SuccessResponse

interface CancelPlanView : BaseView {
    fun setCancelPlan(response: SuccessResponse)
}