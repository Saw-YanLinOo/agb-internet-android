package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.SupportResponse

interface SupportView : BaseView {
    fun setSupport(response: SupportResponse)
}