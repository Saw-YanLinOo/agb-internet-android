package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.PaymentMethodResponse

interface PaymentTypeView : BaseView {
    fun setPaymentTypeData(response: PaymentMethodResponse)

}