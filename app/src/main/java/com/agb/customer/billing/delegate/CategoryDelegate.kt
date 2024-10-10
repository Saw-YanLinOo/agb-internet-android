package com.agb.customer.billing.delegate

import com.agb.customer.billing.modelVO.CategoryVO

interface CategoryDelegate {
    fun onTapCategory(data : CategoryVO)
}