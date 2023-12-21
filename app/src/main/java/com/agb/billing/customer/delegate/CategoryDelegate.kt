package com.agb.billing.customer.delegate

import com.agb.billing.customer.modelVO.CategoryVO

interface CategoryDelegate {
    fun onTapCategory(data : CategoryVO)
}