package com.agb.customer.billing.delegate

import com.agb.customer.billing.networks.requests.ChangePlanRequest

interface ChangePlanDelegate {
    fun onTapPlanConfirm()

    fun onCreateRequest(request : ChangePlanRequest,desc : String)
}