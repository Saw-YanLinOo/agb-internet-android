package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.ComplainListResponse

interface ComplainListView : BaseView {
    fun setData(response: ComplainListResponse)
}