package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.InvoiceListResponse

interface InvoiceListView : BaseVersionView {
    fun setData(response: InvoiceListResponse)
}