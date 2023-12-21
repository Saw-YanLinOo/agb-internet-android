package com.agb.billing.customer.delegate

import com.agb.billing.customer.modelVO.PendingPlanVO

interface PendingPlanDelegate {
    fun onTapUndoPlan(data : PendingPlanVO)
}