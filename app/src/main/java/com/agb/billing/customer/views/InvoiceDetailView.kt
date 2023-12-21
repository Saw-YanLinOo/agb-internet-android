package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.AYAPaymentResponse
import com.agb.billing.customer.networks.responses.CBPaymentResponse
import com.agb.billing.customer.networks.responses.InvoiceDetailResponse
import com.agb.billing.customer.networks.responses.KBZPaymentResponse

interface InvoiceDetailView : BaseVersionView {
    fun setKBZPaymentData(response: KBZPaymentResponse)

    fun setInvoiceDetail(response : InvoiceDetailResponse)

    fun setAYAPaymentData(response: AYAPaymentResponse)
    fun setCBPaymentData(response: CBPaymentResponse)
}