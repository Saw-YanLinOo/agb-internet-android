package com.agb.customer.billing.views

import com.agb.customer.billing.networks.responses.SuccessResponse

interface CancelPlanView : BaseView {
    fun setCancelPlan(response: SuccessResponse)
}