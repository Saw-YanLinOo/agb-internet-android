package com.agb.billing.customer.events

import com.agb.billing.customer.modelVO.PaymentNotificationVO

data class PaymentStatusEvent(val data : PaymentNotificationVO){}