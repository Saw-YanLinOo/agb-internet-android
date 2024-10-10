package com.agb.customer.billing.events

import com.agb.customer.billing.modelVO.PaymentNotificationVO

data class PaymentStatusEvent(val data : PaymentNotificationVO){}