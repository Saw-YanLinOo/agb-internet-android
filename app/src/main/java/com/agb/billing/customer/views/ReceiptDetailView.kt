package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.InvoiceDetailResponse

interface ReceiptDetailView : BaseView {
    fun setReceiptDetail(response : InvoiceDetailResponse)
}