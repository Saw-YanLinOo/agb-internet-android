package com.agb.billing.customer.delegate

import com.agb.billing.customer.modelVO.InvoiceVO

interface InvoiceDelegate {
    fun onTapInvoice(data : InvoiceVO)
}