package com.agb.customer.billing.views

import com.agb.customer.billing.networks.responses.PendingPlanListResponse

interface PendingPlanView : BaseView {
    fun setPendingPlanData(response: PendingPlanListResponse)
}