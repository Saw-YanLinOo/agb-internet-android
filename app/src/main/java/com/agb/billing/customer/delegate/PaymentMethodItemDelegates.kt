package com.agb.billing.customer.delegate

import com.agb.billing.customer.modelVO.PaymentVO


interface PaymentMethodItemDelegates {
    fun onTapPaymentItem(data: PaymentVO)

}