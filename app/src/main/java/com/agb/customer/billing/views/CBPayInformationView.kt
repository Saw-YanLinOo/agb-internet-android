package com.agb.customer.billing.views

import com.agb.customer.billing.networks.responses.CBPaymentQRDownloadQRResponse
import com.agb.customer.billing.networks.responses.ProfileResponse
import com.agb.customer.billing.networks.responses.SuccessResponse

interface CBPayInformationView : BaseView {
    fun setPaymentQRResponse(response: CBPaymentQRDownloadQRResponse)
}