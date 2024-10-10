package com.agb.customer.billing.delegate

import com.agb.customer.billing.modelVO.InvoiceVO

interface InvoiceDelegate {
    fun onTapInvoice(data : InvoiceVO)
}