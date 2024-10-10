package com.agb.customer.billing.delegate

import com.agb.customer.billing.modelVO.NotificationVO

interface NotiItemDelegates {
    fun onTapItem(data : NotificationVO)
}