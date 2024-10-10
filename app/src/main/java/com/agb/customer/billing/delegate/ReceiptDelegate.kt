package com.agb.customer.billing.delegate

import com.agb.customer.billing.modelVO.ReceiptVO

interface ReceiptDelegate {
    fun onTapReceipt(data : ReceiptVO)
}