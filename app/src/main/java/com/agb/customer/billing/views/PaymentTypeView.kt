package com.agb.customer.billing.views

import com.agb.customer.billing.networks.responses.PaymentMethodResponse

interface PaymentTypeView : BaseView {
    fun setPaymentTypeData(response: PaymentMethodResponse)

}