package com.agb.customer.billing.views

import com.agb.customer.billing.networks.responses.ReceiptListResponse

interface ReceiptListView : BaseView {
    fun setData(response: ReceiptListResponse)
}