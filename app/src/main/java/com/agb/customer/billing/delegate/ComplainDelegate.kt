package com.agb.customer.billing.delegate

import com.agb.customer.billing.modelVO.ComplainVO

interface ComplainDelegate {
    fun onTapComplain(data: ComplainVO)
}