package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.PendingPlanListResponse

interface PendingPlanView : BaseView {
    fun setPendingPlanData(response: PendingPlanListResponse)
}