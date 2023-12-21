package com.agb.billing.customer.delegate

import com.agb.billing.customer.modelVO.ReceiptVO

interface ReceiptDelegate {
    fun onTapReceipt(data : ReceiptVO)
}