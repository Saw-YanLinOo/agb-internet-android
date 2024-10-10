package com.agb.customer.billing.views

import com.agb.customer.billing.networks.responses.ComplainPreloadResponse
import com.agb.customer.billing.networks.responses.NotificationResponse
import com.agb.customer.billing.networks.responses.TicketResponse

interface NotificationDetailView : BaseVersionView {
    fun setNotificationData(response: NotificationResponse)
}