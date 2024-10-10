package com.agb.customer.billing.views

import com.agb.customer.billing.networks.responses.ComplainListResponse

interface ComplainListView : BaseView {
    fun setData(response: ComplainListResponse)
}