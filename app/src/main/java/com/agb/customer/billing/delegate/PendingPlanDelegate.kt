package com.agb.customer.billing.delegate

import com.agb.customer.billing.modelVO.PendingPlanVO

interface PendingPlanDelegate {
    fun onTapUndoPlan(data : PendingPlanVO)
}