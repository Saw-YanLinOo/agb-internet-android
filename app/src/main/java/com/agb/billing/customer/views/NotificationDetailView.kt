package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.ComplainPreloadResponse
import com.agb.billing.customer.networks.responses.NotificationResponse
import com.agb.billing.customer.networks.responses.TicketResponse

interface NotificationDetailView : BaseVersionView {
    fun setNotificationData(response: NotificationResponse)
}