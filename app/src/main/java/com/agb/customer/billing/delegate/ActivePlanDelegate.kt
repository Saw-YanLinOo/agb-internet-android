package com.agb.customer.billing.delegate

import com.agb.customer.billing.modelVO.ActivePlanVO

interface ActivePlanDelegate {
    fun onTapChangePlan(data : ActivePlanVO)
}