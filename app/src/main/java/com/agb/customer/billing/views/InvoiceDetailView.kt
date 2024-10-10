package com.agb.customer.billing.views

import com.agb.customer.billing.networks.responses.AYAPaymentResponse
import com.agb.customer.billing.networks.responses.CBPaymentResponse
import com.agb.customer.billing.networks.responses.InvoiceDetailResponse
import com.agb.customer.billing.networks.responses.KBZPaymentResponse

interface InvoiceDetailView : BaseVersionView {
    fun setKBZPaymentData(response: KBZPaymentResponse)

    fun setInvoiceDetail(response : InvoiceDetailResponse)

    fun setAYAPaymentData(response: AYAPaymentResponse)
    fun setCBPaymentData(response: CBPaymentResponse)
}