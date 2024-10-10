package com.agb.customer.billing.views

import com.agb.customer.billing.networks.responses.InvoiceListResponse

interface InvoiceListView : BaseVersionView {
    fun setData(response: InvoiceListResponse)
}