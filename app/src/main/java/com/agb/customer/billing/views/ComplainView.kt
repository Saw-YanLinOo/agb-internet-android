package com.agb.customer.billing.views

import com.agb.customer.billing.networks.responses.ComplainPreloadResponse
import com.agb.customer.billing.networks.responses.TicketResponse

interface ComplainView : BaseVersionView {
    fun setData(response: ComplainPreloadResponse)
    fun setUploadResponseData(response: TicketResponse)
}