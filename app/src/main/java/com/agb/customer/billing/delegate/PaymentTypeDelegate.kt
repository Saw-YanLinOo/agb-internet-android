package com.agb.customer.billing.delegate

interface PaymentTypeDelegate {
    fun onTapPaymentType(paymentMethod : String, paymentTypeId:Int, phoneNo : String)

}