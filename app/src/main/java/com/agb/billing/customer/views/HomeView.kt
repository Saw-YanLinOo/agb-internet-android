package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.*

interface HomeView : BaseVersionView {
    fun setLogout(response: EmptyResponse)

    fun setData(response: InvoiceListResponse)

    fun setHome(response: HomeResponse)

    fun setKBZPaymentData(response: KBZPaymentResponse)

    fun setAYAPaymentData(response: AYAPaymentResponse)

    fun setCBPaymentData(response: CBPaymentResponse)
}