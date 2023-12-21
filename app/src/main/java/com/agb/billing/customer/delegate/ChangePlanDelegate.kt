package com.agb.billing.customer.delegate

import com.agb.billing.customer.networks.requests.ChangePlanRequest

interface ChangePlanDelegate {
    fun onTapPlanConfirm()

    fun onCreateRequest(request : ChangePlanRequest,desc : String)
}