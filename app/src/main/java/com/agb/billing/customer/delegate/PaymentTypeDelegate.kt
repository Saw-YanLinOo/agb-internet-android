package com.agb.billing.customer.delegate

interface PaymentTypeDelegate {
    fun onTapPaymentType(paymentMethod : String, paymentTypeId:Int, phoneNo : String)

}