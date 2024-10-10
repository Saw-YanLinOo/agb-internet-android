package com.agb.customer.billing.views

import com.agb.customer.billing.networks.responses.NotificationResponse

interface NotificationView : BaseView {
    fun responseNotificationData(response: NotificationResponse)
}