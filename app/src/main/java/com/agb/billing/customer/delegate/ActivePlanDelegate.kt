package com.agb.billing.customer.delegate

import com.agb.billing.customer.modelVO.ActivePlanVO

interface ActivePlanDelegate {
    fun onTapChangePlan(data : ActivePlanVO)
}