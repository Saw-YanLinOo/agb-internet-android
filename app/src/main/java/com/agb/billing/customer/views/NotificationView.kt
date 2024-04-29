package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.NotificationResponse

interface NotificationView : BaseView {
    fun responseNotificationData(response: NotificationResponse)
}