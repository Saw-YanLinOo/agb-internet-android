package com.agb.billing.customer.views

import com.agb.billing.customer.networks.responses.ComplainPreloadResponse
import com.agb.billing.customer.networks.responses.TicketResponse

interface ComplainView : BaseVersionView {
    fun setData(response: ComplainPreloadResponse)
    fun setUploadResponseData(response: TicketResponse)
}