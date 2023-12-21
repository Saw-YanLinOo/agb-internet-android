package com.agb.billing.customer.delegate

import com.agb.billing.customer.modelVO.ComplainVO

interface ComplainDelegate {
    fun onTapComplain(data: ComplainVO)
}