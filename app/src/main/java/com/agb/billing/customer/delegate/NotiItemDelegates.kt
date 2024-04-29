package com.agb.billing.customer.delegate

import com.agb.billing.customer.modelVO.NotificationVO

interface NotiItemDelegates {
    fun onTapItem(data : NotificationVO)
}