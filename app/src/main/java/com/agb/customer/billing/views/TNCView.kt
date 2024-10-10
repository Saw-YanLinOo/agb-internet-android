package com.agb.customer.billing.views

import com.agb.customer.billing.networks.responses.TNCResponse

interface TNCView : BaseView {
    fun setTNC(response: TNCResponse)
}