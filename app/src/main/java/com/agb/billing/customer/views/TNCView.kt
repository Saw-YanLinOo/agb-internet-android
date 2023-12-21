package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.TNCResponse

interface TNCView : BaseView {
    fun setTNC(response: TNCResponse)
}