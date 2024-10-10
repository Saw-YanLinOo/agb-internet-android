package com.agb.customer.billing.views

import com.agb.customer.billing.networks.responses.SupportResponse

interface SupportView : BaseView {
    fun setSupport(response: SupportResponse)
}