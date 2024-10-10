package com.agb.customer.billing.delegate

import com.agb.customer.billing.modelVO.PaymentVO


interface PaymentMethodItemDelegates {
    fun onTapPaymentItem(data: PaymentVO)

}