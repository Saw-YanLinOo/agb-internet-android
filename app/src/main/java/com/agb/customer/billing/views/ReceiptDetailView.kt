package com.agb.customer.billing.views

import com.agb.customer.billing.networks.responses.InvoiceDetailResponse

interface ReceiptDetailView : com.agb.customer.billing.views.BaseView {
    fun setReceiptDetail(response : InvoiceDetailResponse)
}