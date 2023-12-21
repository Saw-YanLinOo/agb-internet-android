package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.ReceiptListResponse

interface ReceiptListView : BaseView {
    fun setData(response: ReceiptListResponse)
}